package vn.nlu.todo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.entities.TodoEntity;
import vn.nlu.todo.exceptions.TodoAlreadyExisted;
import vn.nlu.todo.exceptions.TodoNotFoundException;
import vn.nlu.todo.mapstruct.TodoMapper;
import vn.nlu.todo.repositories.TodoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {
    private final TodoRepository repository;
    private final TodoMapper todoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponseDTO> getAll() {
        return repository.findAll().stream().map(todoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponseDTO getById(Long id) {
        return repository.findById(id).map(todoMapper::toResponse).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    @Transactional
    public TodoResponseDTO create(TodoCreateDTO todoCreateDTO) {
        if (repository.existsByNameIgnoreCase(todoCreateDTO.getName())) {
            throw new TodoAlreadyExisted(todoCreateDTO.getName());
        }

        TodoEntity entity = todoMapper.toEntity(todoCreateDTO);
        TodoEntity saved = repository.save(entity);

        return todoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TodoResponseDTO updateById(Long id, TodoUpdateDTO todoUpdateDTO) {
        return repository.findById(id).map(entity -> {
            if (todoUpdateDTO.getName() != null && !todoUpdateDTO.getName().equals(entity.getName()) && repository.existsByNameIgnoreCase(todoUpdateDTO.getName())) {
                throw new TodoAlreadyExisted(todoUpdateDTO.getName());
            }

            todoMapper.updateEntity(todoUpdateDTO, entity);

            return todoMapper.toResponse(entity);
        }).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
