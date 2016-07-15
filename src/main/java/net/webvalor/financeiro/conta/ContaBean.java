package net.webvalor.financeiro.conta;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.index.ContextoBean;

@Named
@ViewScoped
public class ContaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Conta selecionada ;
	
	private List<Conta> lista = null;
	@Inject
	private ContaRN contaRN;

	@Inject
	ContextoBean contextoBean;
	
	@Inject
	ContaDAO dao;

	public void salvar() {

		// ContextoBean contextoBean = ContextoUtil.getContextoBean();
		// this.selecionada.setUsuario(contextoBean.getUsuarioLogado());
		this.selecionada.setEmpresa(contextoBean.getEmpresaSelecionada());

		contaRN.salvar(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void excluir() {
		// ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void tornarFavorita() {
		// ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
	}

	public List<Conta> getLista() {
		if (this.lista == null) {
			// ContextoBean contextoBean = ContextoUtil.getContextoBean();

			this.lista = dao.listar(contextoBean.getEmpresaSelecionada());
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
