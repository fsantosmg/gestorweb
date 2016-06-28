package net.webvalor.dao.comercial;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.webvalor.model.comercial.CategoriaProduto;

public class CategoriaDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager manager;

    public CategoriaProduto porId(Long id) {
        return manager.find(CategoriaProduto.class, id);
    }

    public List<CategoriaProduto> raizes() {

        return manager.createQuery("from CategoriaProduto where categoriaPai is null",
                CategoriaProduto.class).getResultList();
    }

    public List<CategoriaProduto> subcategoriasDe(CategoriaProduto categoriaPai) {
        return manager.createQuery("from CategoriaProduto where categoriaPai = :raiz",
                CategoriaProduto.class).setParameter("raiz", categoriaPai).getResultList();
    }

}
