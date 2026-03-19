package com.devsenai2a.Login.SignUp.Repository;

import com.devsenai2a.Login.SignUp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
