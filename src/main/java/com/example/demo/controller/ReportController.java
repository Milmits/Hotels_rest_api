package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/bookings")
    public List<Booking> getBookingsReport() {
        return reportService.getAllBookingsReport();
    }

    @GetMapping("/hotels")
    public List<Hotel> getHotelsReport() {
        return reportService.getAllHotelsReport();
    }

    @GetMapping("/bookings/csv")
    public ResponseEntity<byte[]> downloadCsvReport() {
        try {
            String filePath = reportService.generateCsvReport();
            byte[] fileContent = Files.readAllBytes(Path.of(filePath));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv");

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



