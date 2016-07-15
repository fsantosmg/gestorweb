package net.webvalor.financeiro.cliente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import net.webvalor.model.comercial.Cliente;

public class ClienteDaoImp implements ClienteDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	
	

	@Override
	public void salvar(Cliente cliente) {
			
		Session session =(Session) manager.getDelegate();

		session.saveOrUpdate(cliente);

	}

	@Override
	public List<Cliente> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
