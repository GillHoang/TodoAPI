package vn.nlu.todo.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import vn.nlu.todo.enums.EPriority;
import vn.nlu.todo.enums.EStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoUpdateDTO {
    @Size(max = 100, message = "Tiêu đề có tối đa 100 kí tự.")
    private String name;

    @Size(max = 1000, message = "Mô tả có tối đa 1000 kí tự.")
    private String description;

    private EPriority priority;

    private EStatus status;

    private LocalDate dueDate;
}
