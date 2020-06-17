package com.knu.lab3.repository;

import com.knu.lab3.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserEmail(String email);

    Optional<Bill> findById(Long id);
}
