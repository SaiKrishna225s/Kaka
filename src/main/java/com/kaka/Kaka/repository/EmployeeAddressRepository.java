package com.kaka.Kaka.repository;

import com.kaka.Kaka.entity.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, UUID> {
}
