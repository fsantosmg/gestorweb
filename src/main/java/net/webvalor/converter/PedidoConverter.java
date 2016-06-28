package net.webvalor.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import net.webvalor.dao.comercial.PedidosDao;
import net.webvalor.model.comercial.Pedido;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	 @Inject
	private PedidosDao dao;

	
	/*public PedidoConverter() throws NamingException {
		//instancia produtodao no contexto do cdi
		dao = CDIServiceLocator.getBean(PedidosDao.class);
	}*/

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {
				Long id = new Long(value);
				Pedido pedido = dao.carregar(id);
				return pedido;
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
			return String.valueOf(((Pedido) value).getId());
		}
	}
}
