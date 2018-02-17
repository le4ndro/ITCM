package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {

}
