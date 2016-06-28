package net.webvalor.Services;

import java.io.Serializable;

import javax.inject.Inject;

import net.webvalor.dao.comercial.ProdutoDao;
import net.webvalor.model.comercial.Produto;

public class ProdutoServices implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;
	
	public Produto salvar(Produto produto) {
		
		return produtoDao.salvar(produto);
	}
	
	
	public void excluir(Produto produto){
		produtoDao.excluir(produto);
	}
	
	
}