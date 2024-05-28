package exceptions;

public class MissingPacmanException extends RuntimeException {
    public MissingPacmanException(String message) {
        super(message);
    }
}
