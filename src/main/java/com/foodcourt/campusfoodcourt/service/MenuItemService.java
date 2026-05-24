package com.foodcourt.campusfoodcourt.service;

import com.foodcourt.campusfoodcourt.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
    MenuItem save(MenuItem item);
    List<MenuItem> getAllItems();
}

