package vn.nlu.todo.mapstruct;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import vn.nlu.todo.dto.TodoCreateDTO;
import vn.nlu.todo.dto.TodoResponseDTO;
import vn.nlu.todo.dto.TodoUpdateDTO;
import vn.nlu.todo.entities.TodoEntity;

@Mapper(componentModel = "spring")
@Component
public interface TodoMapper {

    TodoResponseDTO toResponse(TodoEntity todoEntity);

    TodoEntity toEntity(TodoCreateDTO todoCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TodoUpdateDTO todoUpdateDTO, @MappingTarget TodoEntity todoEntity);
}
