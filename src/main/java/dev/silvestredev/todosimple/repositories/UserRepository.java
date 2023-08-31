package dev.silvestredev.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.silvestredev.todosimple.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public User findByName(String name);  

}
