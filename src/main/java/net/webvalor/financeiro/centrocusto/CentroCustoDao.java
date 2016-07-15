package net.webvalor.financeiro.centrocusto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import net.webvalor.financeiro.categoria.Categoria;
import net.webvalor.financeiro.empresa.Empresa;
import net.webvalor.model.comercial.CategoriaProduto;

public class CentroCustoDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;
	

	
    public List<CentroCusto> listaCentros(Empresa empresa) {
        return em.createQuery("from CentroCusto where empresa = :empresa",
                CentroCusto.class).setParameter("empresa", empresa).getResultList();
    }
    
	public CentroCusto carregar(Integer id) {
		Session session = (Session) em.getDelegate();
		return (CentroCusto) session.get(CentroCusto.class, id);
	}



}
