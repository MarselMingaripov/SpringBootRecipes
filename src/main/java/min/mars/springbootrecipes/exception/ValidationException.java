package min.mars.springbootrecipes.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super("Ошибка валидации " + message);
    }
}
