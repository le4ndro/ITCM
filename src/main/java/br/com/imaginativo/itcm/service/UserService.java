package br.com.imaginativo.itcm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.imaginativo.itcm.model.PasswordResetToken;
import br.com.imaginativo.itcm.model.RoleEnum;
import br.com.imaginativo.itcm.model.User;
import br.com.imaginativo.itcm.model.UserRole;
import br.com.imaginativo.itcm.model.VerificationToken;
import br.com.imaginativo.itcm.repository.PasswordResetTokenRepository;
import br.com.imaginativo.itcm.repository.UserRepository;
import br.com.imaginativo.itcm.repository.VerificationTokenRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) throws EmailExistsException,
            UsernameExistsException, ConstraintViolationException {

        if (emailExists(user.getEmail())) {
            throw new EmailExistsException(
                    "Existe uma conta com este endereco de e-mail "
                            + user.getEmail());
        }

        if (usernameExists(user.getUsername())) {
            throw new UsernameExistsException("Usuário " + user.getUsername()
                    + " já existe.");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("hash " + hashedPassword);
        /*
         * String encpass =
         * PasswordCrypto.getInstance().encrypt(user.getPassword());
         * System.out.println("encpass = " + encpass);
         */
        user.setPassword(hashedPassword);
        Set<UserRole> roles = new HashSet<UserRole>();
        roles.add(new UserRole(RoleEnum.USER.toString(), user));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }

        return false;
    }

    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(
            final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository
                .findByToken(existingVerificationToken);

        vToken.setToken(UUID.randomUUID().toString());
        vToken.setExpiryDate(vToken.calculateExpireDate(60 * 24));// 1 dia
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(final User user,
            final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
