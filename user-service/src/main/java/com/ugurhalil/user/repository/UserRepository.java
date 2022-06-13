package com.ugurhalil.user.repository;

import com.ugurhalil.common.constant.UserRole;
import com.ugurhalil.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findUsersByRole(UserRole userRole);
}
