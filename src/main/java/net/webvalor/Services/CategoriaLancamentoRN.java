package net.webvalor.Services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import net.webvalor.dao.financeiro.CategoriaLancamentoDao;
import net.webvalor.model.Empresa;
import net.webvalor.model.financeiro.CategoriaLancamento;

@Named
public class CategoriaLancamentoRN implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaLancamentoDao categoriaDAO;

    public List<CategoriaLancamento> listar(Empresa empresa) {
        return categoriaDAO.listar(empresa);
    }

    public CategoriaLancamento salvar(CategoriaLancamento categoria) {
        CategoriaLancamento pai = categoria.getPai();

        if (pai == null) {
            String msg = "A Categoria " + categoria.getDescricao() + " deve ter um pai definido";
            throw new IllegalArgumentException(msg);
        }

        boolean mudouFator = pai.getFator() != categoria.getFator();

        categoria.setFator(pai.getFator());
        categoria = this.categoriaDAO.salvar(categoria);

        if (mudouFator) {
            categoria = this.carregar(categoria.getCodigo());
            this.replicarFator(categoria, categoria.getFator());
        }

        return categoria;
    }

    private void replicarFator(CategoriaLancamento categoria, int fator) {
        if (categoria.getFilhos() != null) {
            for (CategoriaLancamento filho : categoria.getFilhos()) {
                filho.setFator(fator);
                this.categoriaDAO.salvar(filho);
                this.replicarFator(filho, fator);
            }
        }
    }

    public void excluir(CategoriaLancamento categoria) {

        this.categoriaDAO.excluir(categoria);
    }

    public void excluir(Empresa empresa) {
        List<CategoriaLancamento> lista = this.listar(empresa);
        for (CategoriaLancamento categoria : lista) {
            this.categoriaDAO.excluir(categoria);
        }
    }

    public CategoriaLancamento carregar(Integer integer) {
        return this.categoriaDAO.carregar(integer);
    }

    public List<Integer> carregarCodigos(Integer categoria) {
        List<Integer> codigos = new ArrayList<Integer>();

        CategoriaLancamento c = this.carregar(categoria);
        this.extraiCodigos(codigos, c);

        return codigos;
    }

    private void extraiCodigos(List<Integer> codigos, CategoriaLancamento categoria) {
        codigos.add(categoria.getCodigo());
        if (categoria.getFilhos() != null) {
            for (CategoriaLancamento filho : categoria.getFilhos()) {
                this.extraiCodigos(codigos, filho);
            }
        }
    }

    public void salvarEstruturaPadrao(Empresa empresa) {

        CategoriaLancamento despesas = new CategoriaLancamento(null, empresa, "DESPESAS", -1);
        despesas = this.categoriaDAO.salvar(despesas);
        this.categoriaDAO.salvar(new CategoriaLancamento(despesas, empresa, "Geral", -1));

        CategoriaLancamento receitas = new CategoriaLancamento(null, empresa, "RECEITAS", 1);
        receitas = this.categoriaDAO.salvar(receitas);
        this.categoriaDAO.salvar(new CategoriaLancamento(receitas, empresa, "Geral", 1));

    }
}
