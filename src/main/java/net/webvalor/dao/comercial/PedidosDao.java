package net.webvalor.dao.comercial;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.webvalor.filter.comenrcial.PedidoFilter;

import net.webvalor.model.comercial.ItemPedido;
import net.webvalor.model.comercial.Pedido;

public class PedidosDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager manager;

	@Inject
	ItemPedido itemPedido;

	public Pedido salvar(Pedido pedido) {
		Session session = (Session) manager.getDelegate();

		session.saveOrUpdate(pedido);
		session.flush();
		return pedido;
	}

	public Pedido carregar(Long id) {
		
		TypedQuery<Pedido> query = manager.createQuery("FROM Pedido p JOIN FETCH p.itens WHERE p.id = :id",Pedido.class);
		
		Pedido pedido = query.setParameter("id", id).getSingleResult();

			
		
		/*
		return manager.createQuery("from CategoriaProduto where categoriaPai = :raiz", 
				CategoriaProduto.class).setParameter("raiz", categoriaPai).getResultList();*/
				
				
				return pedido;
				
				
		
		/*
		 * 
		 * return pedido;
		 */

	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = (Session) manager.getDelegate();

		Criteria criteria = session.createCriteria(Pedido.class)
				// fazemos uma associação (join) com cliente e nomeamos como "c"
				.createAlias("cliente", "c")
				// fazemos uma associação (join) com vendedor e nomeamos como
				// "v"
				.createAlias("vendedor", "v");

		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a
			// filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a
			// filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c",
			// criado anteriormente
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v",
			// criado anteriormente
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes
			// da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

}