package net.webvalor.model.comercial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_produto")
public class CategoriaProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "categoria_pai_id")
	private CategoriaProduto categoriaPai;

	@OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL)
	private List<CategoriaProduto> subcategorias = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaProduto getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaProduto categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<CategoriaProduto> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<CategoriaProduto> subcategorias) {
		this.subcategorias = subcategorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaProduto other = (CategoriaProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}