package vn.nlu.todo.exceptions;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Không tìm thấy todo có id: " + id);
    }
}
