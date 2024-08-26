package com.excel.Excel.controller;


import com.excel.Excel.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/excel")
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/toDatabase")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) {
        try {
            userService.saveUsersFromFile(file);
            return ResponseEntity.ok("File data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/tocsv")
    public ResponseEntity<byte[]> downloadUsers() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            userService.writeUsersToCSV(os);

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)  // Show CSV data as plain text in response
                    .body(os.toByteArray());
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
