package com.beratozturk.SystemOfExam.Repository;

import com.beratozturk.SystemOfExam.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}

