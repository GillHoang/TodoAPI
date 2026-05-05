package vn.nlu.todo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.reponse.ApiResponse;
import vn.nlu.todo.services.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoResponseDTO>>> getAll() {
        List<TodoResponseDTO> todos = service.getAll();

        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> getById(@PathVariable Long id) {
        TodoResponseDTO todo = service.getById(id);

        return ResponseEntity.status(201).body(ApiResponse.success("Tạo todo thành công", todo));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponseDTO>> create(@Valid @RequestBody TodoCreateDTO todoCreateDTO) {
        TodoResponseDTO todo = service.create(todoCreateDTO);

        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody TodoUpdateDTO todoUpdateDTO) {
        TodoResponseDTO todo = service.updateById(id, todoUpdateDTO);

        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
