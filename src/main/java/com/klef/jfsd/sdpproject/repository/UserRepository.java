package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.jfsd.sdpproject.models.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    List<User> findAllByRegstatus(String regstatus);
    List<User> findAllByRole(User.Role role);  
    
}
