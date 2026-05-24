package com.foodcourt.campusfoodcourt.service;

import java.security.Principal;
import com.foodcourt.campusfoodcourt.entity.User;

public interface UserService {
    User saveUser(User user);
    User getUserByEmail(String email);
    boolean adminExists();
	User findByUsername(String username);
	public User getLoggedInUser(Principal principal);
}

