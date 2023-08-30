package dev.silvestredev.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.silvestredev.todosimple.models.User;
import dev.silvestredev.todosimple.repositories.TaskRepository;
import dev.silvestredev.todosimple.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    //busca de usuário por id | lançamento de exceção caso não encontre
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);

        return result.orElseThrow(() -> new RuntimeException(
            "Usuário com o id: <" + id + "> não encontrado!"
        ));
    }

    //criação de usuário
    @Transactional //sempre que o método for uma transação única (ou dá certo ou é tudo revertido), usar essa annotation para otimização
    public User createUser(User user) {
        
        try {
            var userCreate = userRepository.save(user); //salvando o usuário
            taskRepository.saveAll(user.getTasks()); //salvando as tasks do usuário

            return userCreate;
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível criar esse usuário, tente novamente mais tarde.");
        }
    }

    //deletar usuário
    @Transactional
    public void userDelete(Long userId) {

        var user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("Este usuário (" + userId + ") não existe!")
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

    //atualização de senha
    @Transactional
    public void updatePassword (Long userId, String oldPassword, String newPassword) {
        
        var user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("Este usuário (" + userId + ") não existe!")
        );

        var oldPassValidation = user.getPassword();
        if (!oldPassword.equals(oldPassValidation)){
            throw new RuntimeException("Senha atual incorreta!");
        }

        try {
            user.setPassword(newPassword);
            userRepository.save(user);
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível atualizar a senha, tente novamente mais tarde.");
        }
    }
}
