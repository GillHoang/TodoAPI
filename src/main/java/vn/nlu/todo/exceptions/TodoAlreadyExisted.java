package vn.nlu.todo.exceptions;

public class TodoAlreadyExisted extends RuntimeException {
    public TodoAlreadyExisted(String name) {
        super("Đã tồn tại todo có tên: " + name);
    }
}
