/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.webvalor.dao.financeiro;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.webvalor.model.Empresa;
import net.webvalor.model.financeiro.CategoriaLancamento;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Felipe Santos
 */
public class CategoriaLancamentoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public CategoriaLancamento salvar(CategoriaLancamento categoria) {
        Session session = (Session) em.getDelegate();
        CategoriaLancamento merged = (CategoriaLancamento) session.merge(categoria);
        session.flush();
        session.clear();
        return merged;
    }

    public void excluir(CategoriaLancamento categoria) {
        Session session = (Session) em.getDelegate();
        categoria = (CategoriaLancamento) this.carregar(categoria.getCodigo());
        session.delete(categoria);
        session.flush();
        session.clear();
    }

    public CategoriaLancamento carregar(Integer categoria) {
        Session session = (Session) em.getDelegate();
        return (CategoriaLancamento) session.get(CategoriaLancamento.class, categoria);
    }

    public List<CategoriaLancamento> listar(Empresa empresa) {
        Session session = (Session) em.getDelegate();
        String hql = "select c from Categoria c where c.pai is null and c.empresa = :empresa";
        Query query = session.createQuery(hql);
        query.setInteger("empresa", empresa.getId());

        List<CategoriaLancamento> lista = query.list();

        return lista;
    }
}
