package eu.senla.alexbych.bulletinboard.backend.utils.exception;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(Long id) {
        super("Entity is not found, id=" + id);
    }
}
