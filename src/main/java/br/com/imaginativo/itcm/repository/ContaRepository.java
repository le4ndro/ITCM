package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Conta;

public interface ContaRepository extends CrudRepository<Conta, Long> {

}
