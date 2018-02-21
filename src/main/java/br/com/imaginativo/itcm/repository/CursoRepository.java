package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Curso;
import br.com.imaginativo.itcm.model.Empresa;

public interface CursoRepository extends CrudRepository<Curso, Long> {
    List<Curso> findByName(String name);
    List<Curso> findByEmpresa(Empresa empresa);
    List<Curso> findByEmpresaAndName(Empresa empresa, String name);
    List<Curso> findByConta(Conta conta);
    List<Curso> findByContaAndName(Conta conta, String name);
}
