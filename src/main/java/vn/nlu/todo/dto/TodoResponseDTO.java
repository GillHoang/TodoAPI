package vn.nlu.todo.dto;

import lombok.*;
import vn.nlu.todo.enums.EPriority;
import vn.nlu.todo.enums.EStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponseDTO {

    private Long id;

    private String name;

    private String description;

    private EStatus status;

    private EPriority priority;

    private LocalDate dueDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
