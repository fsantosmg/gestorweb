package net.webvalor.controller.comercial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import net.webvalor.dao.comercial.PedidosDao;
import net.webvalor.filter.comenrcial.PedidoFilter;
import net.webvalor.model.comercial.Pedido;
import net.webvalor.model.comercial.StatusPedido;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable  {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidosDao pedidosDao;
	
	private PedidoFilter filter;
	
	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidosBean() {
		filter = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}
	
	public void pesquisar(){
		pedidosFiltrados = pedidosDao.filtrados(filter);
	}
	
	public StatusPedido[] getStatusPedido(){
		return StatusPedido.values();
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFilter() {
		return filter;
	}

	public void setFilter(PedidoFilter filter) {
		this.filter = filter;
	}
	
	
	
}