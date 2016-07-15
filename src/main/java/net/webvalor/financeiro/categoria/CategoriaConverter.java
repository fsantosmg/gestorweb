
package net.webvalor.financeiro.categoria;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	CategoriaDAO dao;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {

				Integer id = new Integer(value);
				Categoria categoria = dao.carregar(id);

				return categoria;
			}

			catch (NumberFormatException exception) {
				System.out.println("Erro " + exception.getMessage());
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Convers�o", "Registro n�o encontrada"));
			}

		}

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Categoria) value).getCodigo());
		}
	}

}
