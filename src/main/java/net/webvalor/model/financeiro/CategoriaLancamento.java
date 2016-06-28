/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.webvalor.model.financeiro;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import net.webvalor.model.Empresa;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Felipe Santos
 */
@Entity
@Table(name = "categoria_lancamento")
public class CategoriaLancamento implements Serializable {

    private static final long serialVersionUID = 1L;

   public CategoriaLancamento() {
		
	}

	public CategoriaLancamento(CategoriaLancamento pai, Empresa empresa, String descricao, int fator) {
		this.pai = pai;
		this.empresa = empresa;
		this.descricao = descricao;
		this.fator = fator;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "categoria_pai", nullable = true, foreignKey = @ForeignKey(name = "fk_categoria_categoria") )
	private CategoriaLancamento pai;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "empresa", foreignKey = @ForeignKey(name = "fk_categoria_empresa") )
	private Empresa empresa;

	private String descricao;

	private int fator;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "categoria_pai", updatable = false)
	@org.hibernate.annotations.OrderBy(clause = "descricao asc")
	private List<CategoriaLancamento> filhos;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public CategoriaLancamento getPai() {
		return pai;
	}

	public void setPai(CategoriaLancamento pai) {
		this.pai = pai;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<CategoriaLancamento> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<CategoriaLancamento> filhos) {
		this.filhos = filhos;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoriaLancamento other = (CategoriaLancamento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
        
        

    
}
