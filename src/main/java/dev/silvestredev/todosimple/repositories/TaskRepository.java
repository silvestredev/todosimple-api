package dev.silvestredev.todosimple.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.silvestredev.todosimple.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long id); //retorna lista de tasks pelo id do usuário | atenção para o nome do método, ele que define a busca
}
