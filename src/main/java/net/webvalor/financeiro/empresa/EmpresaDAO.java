package net.webvalor.financeiro.empresa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EmpresaDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;

	public void salvar(Empresa empresa) {

		manager.persist(empresa);
	}

	public void excluir(Empresa empresa) {
		// TODO Auto-generated method stub

	}

	public List<Empresa> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Empresa buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Empresa buscarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	public Empresa buscaSelecionda() {

		Session session = (Session) manager.getDelegate();

		Criteria criteria = session.createCriteria(Empresa.class);
		criteria.add(Restrictions.eq("selecionada", true));
		
		

		return (Empresa) criteria.uniqueResult();
	}

}
