package net.webvalor.model.comercial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;





@Embeddable
public class EnderecoEntrega implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "entrega_logradouro", nullable = true, length = 150)
	private String logradouro;
	
	@Column(name = "entrega_numero", nullable = true, length = 20)
	private String numero;
	
	@Size(max = 150)
	@Column(name = "entrega_complemento", length = 150)
	private String complemento;
	
	@Column(name = "entrega_cidade", nullable = true, length = 60)
	private String cidade;
	
	@NotNull
	@Column(name = "entrega_uf", nullable = true, length = 60)
	private String uf;
	
	@Column(name = "entrega_cep", nullable = true, length = 9)
	private String cep;

	
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}


	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
