package vn.nlu.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nlu.todo.entities.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    boolean existsByName(String name);
}
