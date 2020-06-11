package org.javatter.javatter.repository;

import org.javatter.javatter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
