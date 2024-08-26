package com.excel.Excel.service;

import com.excel.Excel.model.UserDetails;
import com.excel.Excel.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl{


    @Autowired
    private UserRepository userRepository;

    public void saveUsersFromFile(MultipartFile file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            List<UserDetails> usersToSave = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                String userName = record.get("userName");
                String emailId = record.get("userEmail");

                // Check if the username already exists in the database
                boolean exists = userRepository.existsByUserName(userName);
                if (!exists) {
                    UserDetails user = new UserDetails();
                    user.setUserName(userName);
                    user.setUserEmail(emailId);
                    usersToSave.add(user);
                }

            }

            // Save only the new users to the database
            if (!usersToSave.isEmpty()) {
                userRepository.saveAll(usersToSave);
            }
        }
    }

    public void writeUsersToCSV(OutputStream os) throws IOException {
        List<UserDetails> users = userRepository.findAll();

        try (CSVPrinter printer = new CSVPrinter(new PrintWriter(os), CSVFormat.DEFAULT)) {
            for (UserDetails userDetails : users) {
                printer.printRecord(userDetails.getUserName(), userDetails.getUserEmail());
            }
        }
    }
}
