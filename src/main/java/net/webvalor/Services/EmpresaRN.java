package net.webvalor.Services;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import net.webvalor.dao.EmpresaDao;
import net.webvalor.model.Empresa;

public class EmpresaRN implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmpresaDao dao;

    public Empresa buscaSelecionda() {
        //EmpresaDaoImp dao = new EmpresaDaoImp(); 
        Empresa empresa = dao.buscaSelecionda();

        return empresa;

    }

    public List<Empresa> listarEmpresa() {

        return this.dao.listar();

    }
}
