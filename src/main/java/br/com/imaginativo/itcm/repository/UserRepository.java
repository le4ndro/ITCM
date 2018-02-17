package br.com.imaginativo.itcm.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.imaginativo.itcm.model.User;

@Repository
@Qualifier(value = "userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);

    public User findByEmail(String email);
}
