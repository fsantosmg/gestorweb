
package net.webvalor.financeiro.categoria;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import net.webvalor.financeiro.empresa.Empresa;

public class CategoriaDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;

	public Categoria salvar(Categoria categoria) {
		Session session = (Session) em.getDelegate();
		Categoria merged = (Categoria) session.merge(categoria);
		session.flush();
		session.clear();
		return merged;
	}

	public void excluir(Categoria categoria) {
		Session session = (Session) em.getDelegate();
		categoria = (Categoria) this.carregar(categoria.getCodigo());
		session.delete(categoria);
		session.flush();
		session.clear();
	}

	public Categoria carregar(Integer categoria) {
		Session session = (Session) em.getDelegate();
		return (Categoria) session.get(Categoria.class, categoria);
	}

	@SuppressWarnings("unchecked")

	public List<Categoria> listar(Empresa empresa) {
		Session session = (Session) em.getDelegate();
		String hql = "select c from Categoria c where c.pai is null and c.empresa = :empresa";
		Query query = session.createQuery(hql);
		query.setInteger("empresa", empresa.getId());

		List<Categoria> lista = query.list();

		return lista;
	}
}
