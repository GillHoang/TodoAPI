package vn.nlu.todo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.entities.TodoEntity;
import vn.nlu.todo.enums.EPriority;
import vn.nlu.todo.enums.EStatus;
import vn.nlu.todo.exceptions.TodoAlreadyExisted;
import vn.nlu.todo.exceptions.TodoNotFoundException;
import vn.nlu.todo.repositories.TodoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {
    private final TodoRepository repository;

    private TodoResponseDTO toDTO(TodoEntity entity) {
        return TodoResponseDTO.builder().id(entity.getId()).name(entity.getName()).description(entity.getDescription()).status(entity.getStatus()).priority(entity.getPriority()).dueDate(entity.getDueDate()).createAt(entity.getCreateAt()).updateAt(entity.getUpdateAt()).build();
    }

    private TodoEntity toEntity(TodoCreateDTO dto) {
        return TodoEntity.builder().name(dto.getName()).description(dto.getDescription()).priority(dto.getPriority() != null ? dto.getPriority() : EPriority.MEDIUM).dueDate(dto.getDueDate()).status(EStatus.TODO).build();
    }

    @Override
    public List<TodoResponseDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public TodoResponseDTO getById(Long id) {
        return repository.findById(id).map(this::toDTO).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    @Transactional
    public TodoResponseDTO create(TodoCreateDTO todoCreateDTO) {
        if (repository.existsByName(todoCreateDTO.getName())) {
            throw new TodoAlreadyExisted(todoCreateDTO.getName());
        }

        TodoEntity entity = toEntity(todoCreateDTO);
        TodoEntity saved = repository.save(entity);

        return toDTO(saved);
    }

    @Override
    @Transactional
    public TodoResponseDTO updateById(Long id, TodoUpdateDTO todoUpdateDTO) {
        return repository.findById(id).map(entity -> {
            entity.setName(todoUpdateDTO.getName());
            entity.setDescription(todoUpdateDTO.getDescription());
            entity.setPriority(todoUpdateDTO.getPriority());
            entity.setStatus(todoUpdateDTO.getStatus());
            entity.setDueDate(todoUpdateDTO.getDueDate());

            return toDTO(repository.save(entity));
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
