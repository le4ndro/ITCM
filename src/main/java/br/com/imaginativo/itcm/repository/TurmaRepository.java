package br.com.imaginativo.itcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Turma;

public interface TurmaRepository extends CrudRepository<Turma, Long> {
	List<Turma> findByEmpresa(Empresa empresa);
	List<Turma> findByConta(Conta conta);
}
