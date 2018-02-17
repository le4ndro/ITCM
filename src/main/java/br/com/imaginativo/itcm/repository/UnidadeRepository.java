package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Unidade;

public interface UnidadeRepository extends CrudRepository<Unidade, Long> {

    List<Unidade> findByEmpresa(Empresa empresa);

}
