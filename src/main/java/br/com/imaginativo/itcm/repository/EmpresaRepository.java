package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

}
