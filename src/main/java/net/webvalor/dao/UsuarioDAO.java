package net.webvalor.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.webvalor.model.Usuario;

import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager manager;

    public void salvar(Usuario usuario) {
        Session session = (Session) manager.getDelegate();
        session.save(usuario);

    }

//    public void atualizar(Usuario usuario) {
//
//        Session session = (Session) manager.getDelegate();
//        if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
//            Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
//            usuario.setPermissao(usuarioPermissao.getPermissao());
//            session.evict(usuarioPermissao);
//        }
//
//        session.update(usuario);
//    }

    public void excluir(Usuario usuario) {
        Session session = (Session) manager.getDelegate();
        session.delete(usuario);

    }

    public Usuario carregar(Integer codigo) {
        Session session = (Session) manager.getDelegate();
        return (Usuario) session.get(Usuario.class, codigo);

    }

    public Usuario buscarPorLogin(String login) {
        Session session = (Session) manager.getDelegate();
        String hql = "select u from Usuario u where u.login = :login";
        Query consulta = session.createQuery(hql);
        consulta.setString("login", login);
        return (Usuario) consulta.uniqueResult();
    }

    @SuppressWarnings("unchecked")

    public List<Usuario> listar() {
        Session session = (Session) manager.getDelegate();
        return session.createCriteria(Usuario.class).list();
    }

}
