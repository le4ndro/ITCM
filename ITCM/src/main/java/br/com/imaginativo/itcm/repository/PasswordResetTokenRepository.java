package br.com.imaginativo.itcm.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.imaginativo.itcm.model.PasswordResetToken;
import br.com.imaginativo.itcm.model.User;

public interface PasswordResetTokenRepository extends
        CrudRepository<PasswordResetToken, Long> {

    public PasswordResetToken findByToken(String token);

    public PasswordResetToken findByUser(User user);

}
