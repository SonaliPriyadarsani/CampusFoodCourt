package com.foodcourt.campusfoodcourt.repository;


import com.foodcourt.campusfoodcourt.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

	Object findByUsername(String username);
    
}
