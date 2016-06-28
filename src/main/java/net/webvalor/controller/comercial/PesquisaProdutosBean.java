package net.webvalor.controller.comercial;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.webvalor.Services.ProdutoServices;
import net.webvalor.dao.comercial.ProdutoDao;
import net.webvalor.filter.comenrcial.ProdutoFilter;
import net.webvalor.model.comercial.Produto;
import net.webvalor.util.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;

	@Inject
	private ProdutoServices produtoServices;

	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;

	private Produto produtoSelecionado;

	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}

	public void pesquisar() {
		produtosFiltrados = produtoDao.filtrados(filtro);
	}

	public void excluir() {
		produtoDao.excluir(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);
		FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluido com sucesso.");
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}