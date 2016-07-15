
package net.webvalor.financeiro.lancamentos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.webvalor.financeiro.conta.Conta;

public class LancamentoRN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoDAO lancamentoDAO;

	public void salvar(Lancamento lancamento) {
		lancamentoDAO.salvar(lancamento);
	}

	public void excluir(Lancamento lancamento) {
		lancamentoDAO.excluir(lancamento);
	}

	public Lancamento carregar(Integer lancamento) {
		return lancamentoDAO.carregar(lancamento);
	}

	public float saldo(Conta conta, Date data) {

		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = lancamentoDAO.saldo(conta, data);

		return saldoInicial + saldoNaData;
	}

	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		return lancamentoDAO.listar(conta, dataInicio, dataFim);
	}
}
