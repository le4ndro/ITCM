package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.Perfil;

public interface PerfilRepository extends CrudRepository<Perfil, Long> {
	public Perfil findByUserUsername(String username);

}
