package net.webvalor.financeiro.centrocusto;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.index.ContextoBean;
@Named
@ViewScoped
public class CentroCustoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	CentroCustoDao ccustoDao;
	
	@Inject
	ContextoBean contexto;
	
	List<CentroCusto> listaCustos;
	
	

	public List<CentroCusto> getListaCustos() {
		
		listaCustos =  ccustoDao.listaCentros(contexto.getEmpresaSelecionada());
		return listaCustos;
	}

	public void setListaCustos(List<CentroCusto> listaCustos) {
		this.listaCustos = listaCustos;
	}


	

	
}
