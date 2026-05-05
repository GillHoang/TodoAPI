package vn.nlu.todo.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.response.ApiResponse;
import vn.nlu.todo.services.ITodoService;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final ITodoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoResponseDTO>>> getAll() {
        List<TodoResponseDTO> todos = service.getAll();

        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> getById(@Positive @PathVariable Long id) {
        TodoResponseDTO todo = service.getById(id);

        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponseDTO>> create(@Valid @RequestBody TodoCreateDTO todoCreateDTO) {
        TodoResponseDTO todo = service.create(todoCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "Tạo todo thành công", todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> update(@Positive @PathVariable Long id, @Valid @RequestBody TodoUpdateDTO todoUpdateDTO) {
        TodoResponseDTO todo = service.updateById(id, todoUpdateDTO);

        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@Positive @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa todo thành công", null));
    }
}
