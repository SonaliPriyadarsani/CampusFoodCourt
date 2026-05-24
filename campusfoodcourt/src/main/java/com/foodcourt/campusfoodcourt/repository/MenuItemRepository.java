package com.foodcourt.campusfoodcourt.repository;
import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {


}

