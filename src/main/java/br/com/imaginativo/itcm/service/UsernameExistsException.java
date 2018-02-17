package br.com.imaginativo.itcm.service;

@SuppressWarnings("serial")
public class UsernameExistsException extends Throwable {

    public UsernameExistsException(String message) {
        super(message);
    }

}
