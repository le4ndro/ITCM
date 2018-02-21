package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Interesse;

public interface InteresseRepository extends CrudRepository<Interesse, Long> {
	List<Interesse> findByConta(Conta conta);
}
