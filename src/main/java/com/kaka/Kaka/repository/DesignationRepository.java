package com.kaka.Kaka.repository;

import com.kaka.Kaka.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, UUID> {
}
