package net.webvalor.dao.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.webvalor.model.Empresa;
import net.webvalor.model.financeiro.Conta;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



public class ContaDao implements Serializable{

    private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public void salvar(Conta conta) {
		Session session = (Session) em.getDelegate();
		session.saveOrUpdate(conta);
	}

	public void excluir(Conta conta) {
		Session session = (Session) em.getDelegate();
		session.delete(conta);

	}

	public Conta carregar(Integer conta) {
		Session session = (Session) em.getDelegate();
		return (Conta) session.get(Conta.class, conta);
	}

	@SuppressWarnings("unchecked")

	public List<Conta> listar(Empresa emp) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("empresa", emp));
		return criteria.list();
	}

	public Conta buscarFavorita(Empresa emp) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("empresa", emp));
		criteria.add(Restrictions.eq("favorita", true));

		return (Conta) criteria.uniqueResult();
	}

}
