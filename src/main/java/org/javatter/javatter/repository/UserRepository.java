package org.javatter.javatter.repository;

import org.javatter.javatter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    public User findByEmail(@Param("email") String name);
    // Optional<User> findByEmail(String email);
}
