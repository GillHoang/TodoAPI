package vn.nlu.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.nlu.todo.enums.EPriority;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCreateDTO {
    @NotBlank(message = "Tiêu để không được để trống.")
    @Size(max = 100, message = "Tiêu đề có tối đa 100 kí tự.")
    private String name;

    @Size(max = 1000, message = "Mô tả có tối đa 1000 kí tự.")
    private String description;

    private EPriority priority = EPriority.MEDIUM;

    private LocalDate dueDate;
}
