package com.foodcourt.campusfoodcourt.service;


import com.foodcourt.campusfoodcourt.entity.TableBooking;
import com.foodcourt.campusfoodcourt.entity.User;

import java.util.List;

public interface TableBookingService {
    TableBooking bookTable(TableBooking booking);
    List<TableBooking> getBookingsByUser(User user);
	void save(TableBooking booking);
}
