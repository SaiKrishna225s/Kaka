package com.kaka.Kaka.repository;

import com.kaka.Kaka.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaka.Kaka.entity.Employee;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByEmployee(Employee employee);
}
