package dev.silvestredev.todosimple.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    //interfaces de validação
    public interface CreateUser {} 
    public interface UpdateUser {}

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //gera o id automaticamente
    private Long id;

    @Column(length = 100, nullable = false, unique = true) //tamanho max = 100, não aceita nulo, é único
    @NotBlank(groups = CreateUser.class) //não pode ser nulo e vazio
    @Size(groups = CreateUser.class, min = 5, max = 100)
    private String name;

    @JsonProperty(access = Access.WRITE_ONLY) //não retornar a senha
    @Column(length = 30, nullable = false) //tamanho max = 30, não aceita nulo, é único
    @NotBlank(groups = {CreateUser.class, UpdateUser.class}) //não pode ser nulo e vazio
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6, max = 30)
    private String password;

    @OneToMany(mappedBy = "user") //um usuário pode ter várias tasks || mapeado pelo nome da váriavel presente no model Task
    @JsonProperty(access = Access.WRITE_ONLY) // apenas escrita, não retornar as tasks ao buscar usuário
    private List<Task> tasks = new ArrayList<Task>();

}
