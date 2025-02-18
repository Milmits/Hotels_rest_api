package com.example.demo.service;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(int id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отель с id " + id + " не найден."));
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(int id, Hotel updatedHotel) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отель с id " + id + " не найден."));

        existingHotel.setName(updatedHotel.getName());
        existingHotel.setAddress(updatedHotel.getAddress());
        existingHotel.setContacts(updatedHotel.getContacts());
        existingHotel.setArrivalTime(updatedHotel.getArrivalTime());

        return hotelRepository.save(existingHotel);
    }

    public void deleteHotel(int id) {
        if (!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException("Отель с id " + id + " не найден.");
        }
        hotelRepository.deleteById(id);
    }

    public List<Hotel> searchHotels(String name, String brand, String city, String county, String amenity) {
        // Заглушка, реализация метода зависит от логики поиска
        return hotelRepository.findAll();
    }

    public Hotel addAmenities(int id, Set<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отель с id " + id + " не найден."));

        hotel.getAmenities().addAll(amenities);
        return hotelRepository.save(hotel);
    }
}








