package dev.aziz.repositories;

import dev.aziz.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM task JOIN user_tasks ON task.id = user_tasks.task_id WHERE user_tasks.user_id = :userId")
    Page<Task> findAllByPageRequest(@Param("userId") Long userId, PageRequest pageRequest);

}
