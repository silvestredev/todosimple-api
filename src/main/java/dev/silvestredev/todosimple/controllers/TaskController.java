package dev.silvestredev.todosimple.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.silvestredev.todosimple.models.Task;
import dev.silvestredev.todosimple.models.Task.CreateTask;
import dev.silvestredev.todosimple.services.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        var task = taskService.getTaskById(id);

        return ResponseEntity.ok().body(task);
    }

    @PostMapping("/create")
    @Validated(CreateTask.class)
    public ResponseEntity<String> createTask (@RequestBody @Valid Task task) {
        taskService.CreateTask(task);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
        .buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).body("Task criada!");
    }

    @PutMapping("/update/{id}")
    @Validated(CreateTask.class)
    public ResponseEntity<Task> updateTask(@PathVariable @Valid Long id, @RequestBody Task task) {
        taskService.updateTask(id, task);

        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask (@PathVariable Long id) {
        taskService.DeleteTask(id);

        return ResponseEntity.ok().body("Task deletada com sucesso!");
    }

}
