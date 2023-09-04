package dev.silvestredev.todosimple.models;


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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Task.TASK_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {

    public interface CreateTask {} //validação

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

}
