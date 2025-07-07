package com.foodcourt.campusfoodcourt.service;


import com.foodcourt.campusfoodcourt.entity.TableBooking;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.TableBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableBookingServiceImpl implements TableBookingService {

    @Autowired
    private TableBookingRepository bookingRepository;

    @Override
    public TableBooking bookTable(TableBooking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<TableBooking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

	@Override
	public void save(TableBooking booking) {
		// TODO Auto-generated method stub
		
	}
}
