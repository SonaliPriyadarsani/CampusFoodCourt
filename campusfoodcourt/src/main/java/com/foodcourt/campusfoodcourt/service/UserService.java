package com.foodcourt.campusfoodcourt.service;


import com.foodcourt.campusfoodcourt.entity.User;

public interface UserService {
    User saveUser(User user);
    User getUserByEmail(String email);
    
}
