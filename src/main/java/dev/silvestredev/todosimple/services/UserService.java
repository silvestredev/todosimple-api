package dev.silvestredev.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.silvestredev.todosimple.exceptions.GlobalExceptionHandler;
import dev.silvestredev.todosimple.models.User;
import dev.silvestredev.todosimple.models.enums.ProfileEnum;
import dev.silvestredev.todosimple.repositories.TaskRepository;
import dev.silvestredev.todosimple.repositories.UserRepository;
import dev.silvestredev.todosimple.services.exceptions.ObjectAlreadyExistsException;
import dev.silvestredev.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //bean presente na classe SecurityConfig

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    //busca de usuário por id | lançamento de exceção caso não encontre
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);

        return result.orElseThrow(() -> new ObjectNotFoundException(
            "Usuário com o id: <" + id + "> não encontrado!"
        ));
    }

    //criação de usuário
    @Transactional //sempre que o método for uma transação única (ou dá certo ou é tudo revertido), usar essa annotation para otimização
    public User createUser(User user) {
        
        var userExist = userRepository.findByName(user.getName());
        
        if (userExist != null) {
            throw new ObjectAlreadyExistsException("Já há um usuário cadastrado com esse username!");
        }

        try {
            
            user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet())); //definindo perfil de usuário
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));// criptogrfando senha

            var userCreate = userRepository.save(user); //salvando o usuário
            taskRepository.saveAll(user.getTasks()); //salvando as tasks do usuário

            return userCreate;
        }
        catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException();
        }
    }

    //deletar usuário
    @Transactional
    public void userDelete(Long userId) {

        var user = userRepository.findById(userId).orElseThrow(
            () -> new ObjectNotFoundException("Este usuário (" + userId + ") não existe!")
        );

        try {
            taskRepository.deleteAll(user.getTasks());
            userRepository.delete(user);
        } 
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível deletar esse usuário.");
        }
    }
}
