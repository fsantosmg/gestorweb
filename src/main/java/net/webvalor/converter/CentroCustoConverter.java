package net.webvalor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import net.webvalor.financeiro.centrocusto.CentroCusto;
import net.webvalor.financeiro.centrocusto.CentroCustoDao;

@FacesConverter(forClass = CentroCusto.class)
public class CentroCustoConverter implements Converter{
	
	@Inject
	private CentroCustoDao dao;
	
	/*public CategoriaConverter() throws NamingException {
		
		categoriaDao = CDIServiceLocator.getBean(CategoriaDao.class);
	}*/
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CentroCusto retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = dao.carregar(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((CentroCusto) value).getID().toString();
		}
		
		return "";
	}


}
