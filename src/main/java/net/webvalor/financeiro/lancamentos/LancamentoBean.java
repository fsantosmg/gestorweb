
package net.webvalor.financeiro.lancamentos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.financeiro.categoria.Categoria;
import net.webvalor.financeiro.conta.Conta;
import net.webvalor.index.ContextoBean;

@Named
@ViewScoped
public class LancamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Lancamento> lista;
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	private List<Double> saldos = new ArrayList<Double>();
	private Float saldoGeral;
	private Lancamento editado = new Lancamento();
	@Inject
	private LancamentoRN lancamentoRN;
	// private Integer numeroCheque;

	@Inject
	ContextoBean contextoBean;

	public LancamentoBean() {
		this.novo();
	}

	private void novo() {
		this.editado = new Lancamento();
		this.editado.setData(new Date(System.currentTimeMillis()));
		// this.numeroCheque = null;
	}

	public void salvar() {
		// ContextoBean contextoBean = ContextoUtil.getContextoBean();
		// this.editado.setUsuario(contextoBean.getUsuarioLogado());
		this.editado.setConta(contextoBean.getContaAtiva());
		this.editado.setEmpresa(contextoBean.getEmpresaSelecionada());

		lancamentoRN.salvar(this.editado);

		this.novo();
		this.lista = null;
	}

	/*
	 * public void mudouCheque(ValueChangeEvent event) { Integer chequeAnterior
	 * = (Integer) event.getOldValue(); if (chequeAnterior != null) {
	 * ContextoBean contextoBean = ContextoUtil.getContextoBean(); ChequeRN
	 * chequeRN = new ChequeRN(); try {
	 * chequeRN.desvinculaLancamento(contextoBean.getContaAtiva(),
	 * chequeAnterior); } catch (RNException e) { FacesContext context =
	 * FacesContext.getCurrentInstance(); FacesMessage msg = new
	 * FacesMessage(e.getMessage()); context.addMessage(null, msg); return; } }
	 * }
	 */

	public void excluir() {

		this.editado = lancamentoRN.carregar(this.editado.getLancamento());
		lancamentoRN.excluir(this.editado);
		this.lista = null;
	}

	public List<Lancamento> getLista() {
		if (this.lista == null) {
			// ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);

			this.saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());

			this.lista = lancamentoRN.listar(conta, inicio.getTime(), null);

			Categoria categoria = null;
			double saldo = this.saldoGeral;
			this.saldos = new ArrayList<Double>();
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}

		}
		return lista;
	}

	public List<Lancamento> getListaAteHoje() {
		if (this.listaAteHoje == null) {
			// ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar hoje = new GregorianCalendar();

			this.listaAteHoje = lancamentoRN.listar(conta, null, hoje.getTime());
		}
		return this.listaAteHoje;
	}

	public void setListaAteHoje(List<Lancamento> listaAteHoje) {
		this.listaAteHoje = listaAteHoje;
	}

	public List<Lancamento> getListaFuturos() {
		if (this.listaFuturos == null) {
			// ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);

			this.listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);
		}
		return this.listaFuturos;
	}

	public void setListaFuturos(List<Lancamento> listaFuturos) {
		this.listaFuturos = listaFuturos;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public Float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(Float saldoGeral) {
		this.saldoGeral = saldoGeral;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public void setLista(List<Lancamento> lista) {
		this.lista = lista;
	}

}