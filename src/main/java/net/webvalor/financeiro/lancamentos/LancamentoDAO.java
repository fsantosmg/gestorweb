
package net.webvalor.financeiro.lancamentos;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.webvalor.financeiro.conta.Conta;

public class LancamentoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager em;
	

	public void salvar(Lancamento lancamento) {
		Session session = (Session) em.getDelegate();
		session.saveOrUpdate(lancamento);
		session.flush();
		
	}

	public void excluir(Lancamento lancamento) {
		Session session = (Session) em.getDelegate();
		session.delete(lancamento);
	}

	public Lancamento carregar(Integer lancamento) {
		
		Session session = (Session) em.getDelegate();
		return (Lancamento) session.get(Lancamento.class, lancamento);
	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);

		if (dataInicio != null && dataFim != null) {
			criteria.add(Restrictions.between("data", dataInicio, dataFim));
		} else if (dataInicio != null) {
			criteria.add(Restrictions.ge("data", dataInicio));
		} else if (dataFim != null) {
			criteria.add(Restrictions.le("data", dataFim));
		}

		criteria.add(Restrictions.eq("conta", conta));
		criteria.addOrder(Order.asc("data"));
		return criteria.list();
	}

	public float saldo(Conta conta, Date data) {
		
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(l.valor * c.fator)");
		sql.append("  from lancamento l,");
		sql.append("	     categoria_lancamento c");
		sql.append(" where l.categoria = c.codigo");
		sql.append("   and l.conta = :conta");
		sql.append("   and l.data <= :data");
		
		
		Session session = (Session) em.getDelegate();
		if (data == null) {
			throw new IllegalArgumentException("[Financeiro] data cannot be null");
		}


		SQLQuery query = session.createSQLQuery(sql.toString());

		query.setParameter("conta", conta.getConta());
		query.setParameter("data", data);

		BigDecimal saldo = (BigDecimal) query.uniqueResult();

		if (saldo != null) {
			return saldo.floatValue();
		}
		return 0f;
	}
}
