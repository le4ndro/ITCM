package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Ambiente;
import br.com.imaginativo.itcm.model.Unidade;

public interface AmbienteRepository extends CrudRepository<Ambiente, Long> {

    List<Ambiente> findByUnidade(Unidade unidade);

}
