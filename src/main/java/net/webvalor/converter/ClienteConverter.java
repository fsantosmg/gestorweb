package net.webvalor.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.naming.NamingException;

import net.webvalor.dao.comercial.ClientesDao;
import net.webvalor.model.comercial.Cliente;
import net.webvalor.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter{

	@Inject
	private ClientesDao dao;

	/*public ClienteConverter() throws NamingException {
		dao = CDIServiceLocator.getBean(ClientesDao.class);
	}
*/
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {
				Long id = new Long(value);
				Cliente cliente = dao.carregar(id);
				return cliente;
			}

			catch (NumberFormatException exception) {
				System.out.println("Erro " + exception.getMessage());
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Conversão", "Registro não encontrada"));
			}

		}

	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Cliente) value).getId());
		}
	}
	
	
}
