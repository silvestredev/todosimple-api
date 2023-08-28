package dev.silvestredev.todosimple.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = User.TABLE_NAME)
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
    @NotBlank(groups = CreateUser.class) //não pode ser nulo e vazio
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6, max = 30)
    private String password;

    // private List<Task> tasks = new ArrayList<Task>();

    // constructors
    public User() {

    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // getters and setters
    public Long getId() {
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    // hashequals
    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof User)){
            return false;
        }

        User other = (User) obj;
        
        if (other.id != null) {
            return false;
        }
        else if (!this.id.equals(other.id)){
            return false;
        }

        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.password, other.password);

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

        return result;
    }
}
