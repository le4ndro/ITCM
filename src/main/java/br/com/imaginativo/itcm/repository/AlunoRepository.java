package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Aluno;
import br.com.imaginativo.itcm.model.Conta;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
	List<Aluno> findByConta(Conta conta);
}
