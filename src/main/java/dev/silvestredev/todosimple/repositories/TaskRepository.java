package dev.silvestredev.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.silvestredev.todosimple.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
