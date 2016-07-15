package net.webvalor.index;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.financeiro.conta.Conta;
import net.webvalor.financeiro.conta.ContaRN;
import net.webvalor.financeiro.empresa.Empresa;
import net.webvalor.financeiro.empresa.EmpresaDAO;

@Named
@SessionScoped
public class ContextoBean implements Serializable{
	//private Usuario usuarioLogado = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private Conta contaAtiva = null;
	

	
	private Empresa empresaSelecionada = null;
	
	private String pagina;
	
	@Inject
	EmpresaDAO empresaDAO;
	
	
	@Inject 
	ContaRN condaDAO;

	/*
	 * public Usuario getUsuarioLogado() { FacesContext context =
	 * FacesContext.getCurrentInstance(); ExternalContext external =
	 * context.getExternalContext(); String login = external.getRemoteUser();
	 * 
	 * if (this.usuarioLogado == null ||
	 * !login.equals(this.usuarioLogado.getLogin())) {
	 * 
	 * if (login != null) { UsuarioRN usuarioRN = new UsuarioRN();
	 * this.usuarioLogado = usuarioRN.buscarPorLogin(login); } } return
	 * usuarioLogado; }
	 */

	public Empresa getEmpresaSelecionada() {

		if (this.empresaSelecionada == null) {
			//EmpresaRN empresaRN = new EmpresaRN();
			this.empresaSelecionada = empresaDAO.buscaSelecionda();
			//this.empresaSelecionada = empresaDAO.buscaSelecionda();
		}

		return empresaSelecionada;
	}

	public Conta getContaAtiva() {
		
		if (this.contaAtiva == null) {
			
			empresaSelecionada = getEmpresaSelecionada();

			//ContaRN contaRN = new ContaRN();
			this.contaAtiva = condaDAO.buscarFavorita(empresaSelecionada);

			//se nao ouver conta marcada como ativa envia a primeira
			if (this.contaAtiva == null) {
				List<Conta> contas = condaDAO.listar(empresaSelecionada);
				if (contas != null) {
					for (Conta conta : contas) {
						this.contaAtiva = conta;
						break;
					}
				}
			}
		}
		return this.contaAtiva;
	}
	
	

	public void setContaAtiva(ValueChangeEvent event) {
		
		Integer conta =  (Integer) event.getNewValue();

		ContaRN contaRN = new ContaRN();
		this.contaAtiva = contaRN.carregar(conta);
	}

	public String getPagina() {
		if (pagina.isEmpty()) {
			pagina = "/faces/restrito/principal";
		}
		return pagina;
	}

}