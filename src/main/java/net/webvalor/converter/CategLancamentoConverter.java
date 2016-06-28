package net.webvalor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import net.webvalor.dao.financeiro.CategoriaLancamentoDao;
import net.webvalor.model.financeiro.CategoriaLancamento;

@FacesConverter(forClass = CategoriaLancamento.class)
public class CategLancamentoConverter implements Converter {

    @Inject
    private CategoriaLancamentoDao dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CategoriaLancamento retorno = null;

        if (value != null) {
            Integer id = new Integer(value);
            retorno = dao.carregar(id);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((CategoriaLancamento) value).getCodigo().toString();
        }

        return "";
    }

}
