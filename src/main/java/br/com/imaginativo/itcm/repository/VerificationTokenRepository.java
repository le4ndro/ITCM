package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.User;
import br.com.imaginativo.itcm.model.VerificationToken;

public interface VerificationTokenRepository extends
        CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
