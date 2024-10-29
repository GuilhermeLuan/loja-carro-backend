package dev.guilhermeluan.lojacarro.repositories;

import dev.guilhermeluan.lojacarro.model.User;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
