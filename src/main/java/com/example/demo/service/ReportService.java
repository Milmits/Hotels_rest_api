package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Booking> getAllBookingsReport() {
        return bookingRepository.findAll();
    }

    public List<Hotel> getAllHotelsReport() {
        return hotelRepository.findAll();
    }

    public String generateCsvReport() throws IOException {
        List<Booking> bookings = bookingRepository.findAll();
        String filePath = "report.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Отель,Клиент,Дата заезда,Дата выезда,Стоимость,Отменено\n");

            for (Booking booking : bookings) {
                writer.append(booking.getId() + ",")
                        .append(booking.getHotel().getName() + ",")
                        .append(booking.getCustomerName() + ",")
                        .append(booking.getCheckInDate().toString() + ",")
                        .append(booking.getCheckOutDate().toString() + ",")
                        .append(booking.getTotalPrice() + ",")
                        .append(booking.isCanceled() ? "Да" : "Нет")
                        .append("\n");
            }
        }

        return filePath;
    }
}


