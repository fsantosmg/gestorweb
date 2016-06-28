package net.webvalor.index;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import net.webvalor.Services.ContaRN;
import net.webvalor.Services.EmpresaRN;
import net.webvalor.dao.EmpresaDao;
import net.webvalor.model.Empresa;
import net.webvalor.model.Usuario;
import net.webvalor.model.financeiro.Conta;

@Named
@SessionScoped
public class ContextoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuarioLogado = null;
    private Conta contaAtiva = null;
    private Empresa empresaSelecionada = null;
    private String pagina;

    @Inject
    EmpresaDao empresaDAO;

    @Inject
    EmpresaRN empresaRN;

    @Inject
    ContaRN contaRN;

//	public Usuario getUsuarioLogado() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext external = context.getExternalContext();
//		String login = external.getRemoteUser();
//
//		if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {
//
//			if (login != null) {
//				UsuarioRN usuarioRN = new UsuarioRN();
//				this.usuarioLogado = usuarioRN.buscarPorLogin(login);
//			}
//		}
//		return usuarioLogado;
//	}
    public Empresa getEmpresaSelecionada() {

        if (this.empresaSelecionada == null) {
            //EmpresaRN empresaRN = new EmpresaRN();
            this.empresaSelecionada = empresaRN.buscaSelecionda();
            //this.empresaSelecionada = empresaDAO.buscaSelecionda();
        }

        return empresaSelecionada;
    }

    public Conta getContaAtiva() {

        if (this.contaAtiva == null) {

            Empresa empresa = getEmpresaSelecionada();

            //ContaRN contaRN = new ContaRN();
            this.contaAtiva = contaRN.buscarFavorita(empresa);

            if (this.contaAtiva == null) {
                List<Conta> contas = contaRN.listar(empresa);
                if (contas != null) {
                    for (Conta conta : contas) {
                        this.contaAtiva = conta;
                        break;
                    }
                }
            }
        }
        return this.contaAtiva;
    }

    public void setContaAtiva(ValueChangeEvent event) {
        Integer conta = (Integer) event.getNewValue();
        this.contaAtiva = contaRN.carregar(conta);
    }

    public String getPagina() {
        if (pagina.isEmpty()) {
            pagina = "/faces/restrito/principal";
        }
        return pagina;
    }

}
