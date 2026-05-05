package vn.nlu.todo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.nlu.todo.enums.EPriority;
import vn.nlu.todo.enums.EStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tiêu để không được để trống.")
    @Size(max = 100, message = "Tiêu đề có tối đa 100 kí tự.")
    @Column(nullable = false)
    private String name;

    @Size(max = 1000, message = "Mô tả có tối đa 1000 kí tự.")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EStatus status = EStatus.TODO;

    @Enumerated(EnumType.STRING)
    private EPriority priority = EPriority.MEDIUM;

    private LocalDate dueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
