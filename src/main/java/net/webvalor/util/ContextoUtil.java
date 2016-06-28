package net.webvalor.util;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.webvalor.index.ContextoBean;

public class ContextoUtil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ContextoBean getContextoBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        HttpSession session = (HttpSession) external.getSession(true);

        ContextoBean contextoBean = (ContextoBean) session
                .getAttribute("contextoBean");

        return contextoBean;
    }
}
