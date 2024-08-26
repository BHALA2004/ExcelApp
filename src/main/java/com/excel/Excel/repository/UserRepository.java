package com.excel.Excel.repository;

import com.excel.Excel.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long> {

    boolean existsByUserName(String userName);

}
