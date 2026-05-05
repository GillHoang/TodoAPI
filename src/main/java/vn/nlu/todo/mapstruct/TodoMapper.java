package vn.nlu.todo.mapstruct;

import org.mapstruct.*;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.entities.TodoEntity;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoResponseDTO toResponse(TodoEntity todoEntity);

    TodoEntity toEntity(TodoCreateDTO todoCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TodoUpdateDTO todoUpdateDTO, @MappingTarget TodoEntity todoEntity);
}
