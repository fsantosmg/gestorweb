package net.webvalor.financeiro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EmpresaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresa empresa;
	
	private List<Empresa> empresas = new ArrayList<Empresa>();


	@Inject
	private EmpresaDAO dao;

	public void loadObject() {
		this.empresas = dao.listar();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void listaEmpresa() {

	}

}
