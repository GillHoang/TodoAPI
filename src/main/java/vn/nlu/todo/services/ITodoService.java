package vn.nlu.todo.services;

import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;

import java.util.List;

public interface ITodoService {

    List<TodoResponseDTO> getAll();

    TodoResponseDTO getById(Long id);

    TodoResponseDTO create(TodoCreateDTO todoCreateDTO);

    TodoResponseDTO updateById(Long id, TodoUpdateDTO todoUpdateDTO);

    void deleteById(Long id);
}
