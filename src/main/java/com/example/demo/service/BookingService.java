package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.RoomCategory;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BookingService {

    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        if (isRoomBooked(booking)) {
            throw new IllegalStateException("Этот номер уже забронирован на указанные даты.");
        }

        double totalPrice = calculateTotalPrice(booking);
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    private boolean isRoomBooked(Booking newBooking) {
        List<Booking> existingBookings = bookingRepository.findByHotelId(newBooking.getHotel().getId());
        return existingBookings.stream().anyMatch(existing ->
                existing.getRoomNumber() == newBooking.getRoomNumber() &&
                        !existing.isCanceled() &&
                        !(existing.getCheckOutDate().isBefore(newBooking.getCheckInDate()) ||
                                existing.getCheckInDate().isAfter(newBooking.getCheckOutDate()))
        );
    }

    private double calculateTotalPrice(Booking booking) {
        double pricePerDay = switch (booking.getCategory()) {
            case STANDARD -> 3000;
            case COMFORT -> 5000;
            case LUXURY -> 8000;
        };

        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        double totalPrice = pricePerDay * days;

        if (days >= 7) {
            totalPrice *= 0.9; // 10% скидка за длительное бронирование
        }

        if (booking.isExtraBed()) {
            totalPrice += 500; // Доплата за детскую кровать
        }

        return totalPrice;
    }

    public Booking cancelBooking(int id, String reason) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронирование с id " + id + " не найдено."));

        if (booking.isCanceled()) {
            throw new IllegalStateException("Бронирование уже отменено.");
        }

        booking.setCanceled(true);
        booking.setCancellationReason(reason);

        return bookingRepository.save(booking);
    }
}


