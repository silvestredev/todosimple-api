package dev.silvestredev.todosimple.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.silvestredev.todosimple.models.User;
import dev.silvestredev.todosimple.models.User.CreateUser;
import dev.silvestredev.todosimple.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated //significa que validaremos algo, no caso, os métodos das services
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        
        var user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/register")
    @Validated(CreateUser.class)
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user) {
        
        userService.createUser(user);
        
        //construção da URI do usuário com base no seu id 
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        
        userService.userDelete(id);

        return ResponseEntity.noContent().build();
    } 
}
