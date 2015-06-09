package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Curso;

public interface CursoRepository extends CrudRepository<Curso, Long> {
    List<Curso> findByName(String name);
}
