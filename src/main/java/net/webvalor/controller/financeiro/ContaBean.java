package net.webvalor.controller.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.webvalor.Services.ContaRN;
import net.webvalor.index.ContextoBean;
import net.webvalor.model.financeiro.Conta;
import net.webvalor.util.ContextoUtil;

@Named
@RequestScoped
public class ContaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Conta selecionada;
    private List<Conta> lista = null;
    @Inject
    private ContaRN contaRN;
    
    @Inject
    ContextoBean contextoBean ;

    public void salvar() {

       // ContextoBean contextoBean = ContextoUtil.getContextoBean();
        //this.selecionada.setUsuario(contextoBean.getUsuarioLogado());
        this.selecionada.setEmpresa(contextoBean.getEmpresaSelecionada());

        contaRN.salvar(this.selecionada);
        //this.selecionada = new Conta();
        this.lista = null;
    }

    public void excluir() {
        //ContaRN contaRN = new ContaRN();
        contaRN.excluir(this.selecionada);
        this.selecionada = new Conta();
        this.lista = null;
    }

    public void tornarFavorita() {
        //ContaRN contaRN = new ContaRN();
        contaRN.tornarFavorita(this.selecionada);
        this.selecionada = new Conta();
    }

    public List<Conta> getLista() {
        if (this.lista == null) {
        //    ContextoBean contextoBean = ContextoUtil.getContextoBean();

            this.lista = contaRN.listar(contextoBean.getEmpresaSelecionada());
        }
        return this.lista;
    }

    public Conta getSelecionada() {
        return selecionada;
    }

    public void setSelecionada(Conta selecionada) {
        this.selecionada = selecionada;
    }
}
