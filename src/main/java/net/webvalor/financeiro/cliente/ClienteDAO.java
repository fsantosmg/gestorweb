package net.webvalor.financeiro.cliente;

import java.util.List;

import net.webvalor.model.comercial.Cliente;

public interface ClienteDAO {

	public void salvar(Cliente cliente);
	
	public List<Cliente> listarTodos();
	
	public Cliente buscarPorId(Long id);
	
	
}
