package com.foodcourt.campusfoodcourt.service;


import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItem save(MenuItem item) {
        return menuItemRepository.save(item);
    }

    @Override
    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }
}

