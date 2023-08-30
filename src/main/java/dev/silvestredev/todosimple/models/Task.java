package dev.silvestredev.todosimple.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Task.TASK_NAME)
public class Task {

    private interface CreateTask {}

    public static final String TASK_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    @NotBlank(groups = CreateTask.class)
    @Size(groups = CreateTask.class, min = 1, max = 100)
    private String title;

    @ManyToOne //várias tasks podem ser de um usuários
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // referencia a pk do user | não pode ser nulo | não pode mudar o usuário
    private User user;
    
    @Column(length = 500, nullable = false)
    @NotBlank(groups = CreateTask.class)
    @Size(groups = CreateTask.class, min = 1, max = 500)
    private String description;

    public Task () {

    }

    public Task (Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    //hash and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
