package es.in2.issuer.exception;

public class JwtParseException extends RuntimeException {

    public JwtParseException(String message) {
        super(message);
    }

}
