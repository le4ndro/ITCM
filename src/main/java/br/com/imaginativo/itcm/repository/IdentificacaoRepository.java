package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Identificacao;

public interface IdentificacaoRepository extends
        CrudRepository<Identificacao, Long> {

}
