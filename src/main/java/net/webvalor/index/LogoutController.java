package net.webvalor.index;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
public class LogoutController {
	public String deslogar() throws ServletException, IOException {
		/*
		 * ExternalContext context =
		 * FacesContext.getCurrentInstance().getExternalContext();
		 * RequestDispatcher dispatcher = ((ServletRequest)
		 * context.getRequest())
		 * .getRequestDispatcher("/j_spring_security_logout");
		 * dispatcher.forward((ServletRequest) context.getRequest(),
		 * (ServletResponse) context.getResponse());
		 * FacesContext.getCurrentInstance().responseComplete();
		 */
		SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
		return "/public/login";
	}
}
