package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {
	List<Professor> findByConta(Conta conta);
}
