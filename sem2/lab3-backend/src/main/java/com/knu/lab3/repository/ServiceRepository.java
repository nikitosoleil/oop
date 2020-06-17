package com.knu.lab3.repository;

import com.knu.lab3.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByIdNotIn(List<Long> serviceId);
}
