package net.webvalor.controller.comercial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import net.webvalor.Services.PedidosService;
import net.webvalor.dao.comercial.ClientesDao;
import net.webvalor.dao.comercial.ProdutoDao;
import net.webvalor.dao.comercial.UsuariosDao;
import net.webvalor.model.comercial.Cliente;
import net.webvalor.model.comercial.EnderecoEntrega;
import net.webvalor.model.comercial.FormaPagamento;
import net.webvalor.model.comercial.ItemPedido;
import net.webvalor.model.comercial.Pedido;
import net.webvalor.model.comercial.Produto;
import net.webvalor.model.comercial.StatusPedido;
import net.webvalor.model.Usuario;
import net.webvalor.util.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClientesDao clientesDao;

	@Inject
	private UsuariosDao usuariosDao;

	@Inject
	PedidosService service;

	@Inject
	ProdutoDao produtoDao;

	private Produto produtoLinhaEditavel;

	private Pedido pedido;
	private List<Usuario> vendedor;

	private String sku;

	private boolean editando;

	public CadastroPedidoBean() {

	}

	/*
	 * @PostConstruct public void init() {
	 * 
	 * if (this.pedido == null) { this.pedido = new Pedido(); }
	 * 
	 * if (FacesUtil.isNotPostback()) {
	 * 
	 * this.recalcularPedido(); this.vendedor = this.usuariosDao.vendedores();
	 * this.pedido.adicionarItemVazio();
	 * 
	 * }
	 * 
	 * }
	 */

	public void inicializar() {

		if (this.pedido == null) {
			this.pedido = new Pedido();
			this.pedido.setEnderecoEntrega(new EnderecoEntrega());
		}

		if (FacesUtil.isNotPostback()) {

			this.recalcularPedido();
			this.vendedor = this.usuariosDao.vendedores();
			this.pedido.adicionarItemVazio();

		}

	}

	private void limpar() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	public List<Cliente> listarCliente(String nome) {
		return clientesDao.porNome(nome);
	}

	public FormaPagamento[] getformaPagamentos() {
		return FormaPagamento.values();
	}

	public void salvar() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.service.salvar(this.pedido);
		} finally {
			this.pedido.adicionarItemVazio();
		}
		
		
		/*
		 * if (!isEditando()) { pedido.setDataCriacao(new Date());
		 * pedido.setStatus(StatusPedido.ORCAMENTO); pedido.setValorTotal(new
		 * BigDecimal(10)); service.salvar(pedido);
		 * 
		 * } else { service.salvar(pedido); }
		 */
		FacesUtil.addInfoMessage("Pedido gravado com sucesso!");

	}

	public boolean isEditando() {

		return this.pedido.getId() != null;
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			// this.pedido.getItens();
			this.pedido.recalcularValorTotal();
		}
	}

	public void carregarProdutoPorSKU() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtoDao.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtoDao.porNome(nome);
	}

	public void carregarProdutoLinhaEditavel() {

		ItemPedido item = this.pedido.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				this.pedido.recalcularValorTotal();
			}
		}
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}
		this.recalcularPedido();
	}
	
	public void atualizarTributos(ItemPedido item, int linha){
		
	}

	// ---getsetPadrao--------------------

	public Pedido getPedido() {
		return pedido;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedor() {
		return vendedor;
	}

	public void setVendedor(List<Usuario> vendedor) {
		this.vendedor = vendedor;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

}