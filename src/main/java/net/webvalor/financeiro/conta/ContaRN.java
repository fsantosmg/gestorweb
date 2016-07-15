package net.webvalor.financeiro.conta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.webvalor.financeiro.empresa.Empresa;


public class ContaRN implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ContaDAO contaDAO;

	

	public List<Conta> listar(Empresa empresa) {
		return contaDAO.listar(empresa);
	}

	public Conta carregar(Integer conta) {
		return contaDAO.carregar(conta);
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		contaDAO.salvar(conta);
	}

	public void excluir(Conta conta) {
		contaDAO.excluir(conta);
	}

	public void tornarFavorita(Conta contaFavorita) {
		Conta conta = this.buscarFavorita(contaFavorita.getEmpresa());
		if (conta != null) {
			conta.setFavorita(false);
			contaDAO.salvar(conta);
		}
		contaFavorita.setFavorita(true);
		contaDAO.salvar(contaFavorita);
	}

	private Conta buscarFavorita(Long conta) {
		
		return null;
	}

	public Conta buscarFavorita(Empresa empresa) {

		return contaDAO.buscarFavorita(empresa);
	}



}
