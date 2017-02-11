package com.limagiran.hearthstone.heroi.control;

/**
 *
 * @author Vinicius Silva
 */
public class JogouCardException extends Exception {

    private final String message;

    public JogouCardException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}