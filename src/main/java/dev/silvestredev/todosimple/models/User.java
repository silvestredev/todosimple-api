package dev.silvestredev.todosimple.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import dev.silvestredev.todosimple.models.enums.ProfileEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(nullable = false) //tamanho max = 30, não aceita nulo, é único
    @NotBlank(groups = {CreateUser.class, UpdateUser.class}) //não pode ser nulo e vazio
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6)
    private String password;

    @OneToMany(mappedBy = "user") //um usuário pode ter várias tasks || mapeado pelo nome da váriavel presente no model Task
    @JsonProperty(access = Access.WRITE_ONLY) // apenas escrita, não retornar as tasks ao buscar usuário
    private List<Task> tasks = new ArrayList<Task>();

    @ElementCollection(fetch = FetchType.EAGER) // sempre que buscar o usuário no banco, buscar os perfis juntos
    @JsonProperty(access = Access.WRITE_ONLY) // não retornar os perfis
    @CollectionTable(name = "user_profile") // definindo o nome da tabela no db
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>(); //setando o valor de profile

    //transformando os perfis em uma stream (para podermos percorrer), mapeando os valores e transformando e um Set e retornando
    public Set<ProfileEnum> getProfiles () {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    //adicionando pefil no Set profiles
    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }
}