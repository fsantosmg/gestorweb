package net.webvalor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import net.webvalor.dao.comercial.CategoriaDao;
import net.webvalor.model.comercial.CategoriaProduto;




@FacesConverter(forClass = CategoriaProduto.class)
public class CategoriaConverter implements Converter {

	@Inject
	private CategoriaDao categoriaDao;
	
	/*public CategoriaConverter() throws NamingException {
		
		categoriaDao = CDIServiceLocator.getBean(CategoriaDao.class);
	}*/
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CategoriaProduto retorno = null;
		
		if (value != null) {
			Long id = new Long(value);
			retorno = categoriaDao.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((CategoriaProduto) value).getId().toString();
		}
		
		return "";
	}

}
