package net.webvalor.financeiro.empresa;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class EmpresaRN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EmpresaDAO dao;
	@Inject
	Empresa empresa;

	public Empresa buscaSelecionda() {
		// EmpresaDaoImp dao = new EmpresaDaoImp();
		empresa = dao.buscaSelecionda();

		return empresa;

	}

	public List<Empresa> listarEmpresa() {

		return this.dao.listar();

	}
}
