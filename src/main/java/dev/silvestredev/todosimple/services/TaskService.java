package dev.silvestredev.todosimple.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.silvestredev.todosimple.models.Task;
import dev.silvestredev.todosimple.repositories.TaskRepository;
import dev.silvestredev.todosimple.repositories.UserRepository;
import dev.silvestredev.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    //
    public List<Task> getAllUserTasks(Long userId) {

        var user = userRepository.findById(userId).orElseThrow(
            () -> new ObjectNotFoundException("Usuário não encontrado!")
        );
        
        try {
            return user.getTasks();
        } 
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível realizar a busca das tarefas no momento");
        }
    }

    public Task getTaskById(Long taskId) {

        var task = taskRepository.findById(taskId).orElseThrow(
            () -> new ObjectNotFoundException("Essa Task <" + taskId + "> não existe!")
        );

        try {
            return task;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível realizar essa operação");
        }
    }

    @Transactional
    public void CreateTask (Task task) {

        try {
            taskRepository.save(task);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possivel realizar essa operação");
        }
    }


    @Transactional
    public void updateTask (Long taskId, Task newTask) {

        Task oldTask = taskRepository.findById(taskId).orElseThrow(
            () -> new ObjectNotFoundException("Essa task <" + taskId + "> não existe!")
        );

        try {
            oldTask.setTitle(newTask.getTitle());
            oldTask.setDescription(newTask.getDescription());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possivel realizar essa operação");
        }
    }

    @Transactional
    public void DeleteTask (Long taskId) {

        var task = taskRepository.findById(taskId).orElseThrow(
            () -> new ObjectNotFoundException("Essa task <" + taskId + "> não existe!")
        );

        try {
            taskRepository.delete(task);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possivel realizar essa operação");
        }
    }
}
