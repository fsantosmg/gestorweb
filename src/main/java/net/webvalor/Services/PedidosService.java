package net.webvalor.Services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.webvalor.dao.comercial.ClientesDao;
import net.webvalor.dao.comercial.PedidosDao;
import net.webvalor.dao.comercial.UsuariosDao;
import net.webvalor.model.Usuario;
import net.webvalor.model.comercial.Cliente;
import net.webvalor.model.comercial.Pedido;
import net.webvalor.model.comercial.StatusPedido;

public class PedidosService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidosDao pedidosDao;

	@Inject
	private ClientesDao clientesDao;

	@Inject
	private UsuariosDao usuariosDao;


	
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		
		pedido.recalcularValorTotal();
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}
		
		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("Valor total do pedido não pode ser negativo.");
		}
		
		pedido = this.pedidosDao.salvar(pedido);
		return pedido;
	}

	public List<Cliente> listarClientes(String nome) {
		return this.clientesDao.porNome(nome);
	}

	public List<Usuario> listaVendedores() {
		return usuariosDao.vendedores();
	}



}
