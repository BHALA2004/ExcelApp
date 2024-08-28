package com.excel.Excel.controller;


import com.excel.Excel.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public void exportCSV(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\\\"users.csv\\\"");
        try {
            userService.writeUsersToCSV(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
