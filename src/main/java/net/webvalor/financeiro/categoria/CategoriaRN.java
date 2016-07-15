package net.webvalor.financeiro.categoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.financeiro.empresa.Empresa;

@Named
public class CategoriaRN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaDAO categoriaDAO;

	public List<Categoria> listar(Empresa empresa) {
		return categoriaDAO.listar(empresa);
	}

	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();

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

	private void replicarFator(Categoria categoria, int fator) {
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.categoriaDAO.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}

	public void excluir(Categoria categoria) {

		this.categoriaDAO.excluir(categoria);
	}

	public void excluir(Empresa empresa) {
		List<Categoria> lista = this.listar(empresa);
		for (Categoria categoria : lista) {
			this.categoriaDAO.excluir(categoria);
		}
	}

	public Categoria carregar(Integer integer) {
		return this.categoriaDAO.carregar(integer);
	}

	public List<Integer> carregarCodigos(Integer categoria) {
		List<Integer> codigos = new ArrayList<Integer>();

		Categoria c = this.carregar(categoria);
		this.extraiCodigos(codigos, c);

		return codigos;
	}

	private void extraiCodigos(List<Integer> codigos, Categoria categoria) {
		codigos.add(categoria.getCodigo());
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				this.extraiCodigos(codigos, filho);
			}
		}
	}

	public void salvarEstruturaPadrao(Empresa empresa) {

		Categoria despesas = new Categoria(null, empresa, "DESPESAS", -1);
		despesas = this.categoriaDAO.salvar(despesas);
		this.categoriaDAO.salvar(new Categoria(despesas, empresa, "Geral", -1));

		Categoria receitas = new Categoria(null, empresa, "RECEITAS", 1);
		receitas = this.categoriaDAO.salvar(receitas);
		this.categoriaDAO.salvar(new Categoria(receitas, empresa, "Geral", 1));

	}
}