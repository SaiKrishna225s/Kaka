package com.kaka.Kaka.config;

import com.kaka.Kaka.entity.Employee;
import com.kaka.Kaka.entity.Organization;
import com.kaka.Kaka.entity.Role;
import com.kaka.Kaka.entity.UserRole;
import com.kaka.Kaka.entity.enums.EmployeeStatus;
import com.kaka.Kaka.entity.enums.EmploymentType;
import com.kaka.Kaka.repository.EmployeeRepository;
import com.kaka.Kaka.repository.OrganizationRepository;
import com.kaka.Kaka.repository.RoleRepository;
import com.kaka.Kaka.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(RoleRepository roleRepository, 
                          OrganizationRepository organizationRepository,
                          EmployeeRepository employeeRepository,
                          UserRoleRepository userRoleRepository,
                          PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
        this.employeeRepository = employeeRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        seedPlatformAdmin();
    }

    private void seedRoles() {
        List<String[]> roles = List.of(
                new String[]{"PLATFORM_ADMIN", "Platform Provider - Can create Organizations and assign Organization Super Admins"},
                new String[]{"ORG_SUPER_ADMIN", "Organization Super Admin - Complete control over their organization, can create Admins and Employees"},
                new String[]{"ORG_ADMIN", "Organization Admin - Can manage daily operations and create Employees within the organization"},
                new String[]{"HR_MANAGER", "HR Manager with access to employee management, attendance, and leave approval"},
                new String[]{"MANAGER", "Department Manager with access to team attendance and leave approval"},
                new String[]{"EMPLOYEE", "Standard employee with access to own profile, leaves, and attendance"}
        );

        for (String[] roleData : roles) {
            String roleName = roleData[0];
            String description = roleData[1];

            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(description);
                roleRepository.save(role);
                logger.info("Seeded Role: {}", roleName);
            } else {
                logger.debug("Role {} already exists.", roleName);
            }
        }
    }

    private void seedPlatformAdmin() {
        // 1. Create Organization
        String orgName = "kaka";
        Organization organization = organizationRepository.findByName(orgName).orElseGet(() -> {
            Organization org = new Organization();
            org.setName(orgName);
            org.setDomain("kaka.com");
            organizationRepository.save(org);
            logger.info("Seeded Organization: {}", orgName);
            return org;
        });

        // 2. Create Platform Admin Employee
        String adminEmail = "charanreddy.gunda@kaka.com";
        Employee adminEmployee = employeeRepository.findByEmail(adminEmail).orElseGet(() -> {
            Employee emp = new Employee();
            emp.setOrganization(organization);
            emp.setFirstName("Charan Reddy");
            emp.setLastName("Gunda");
            emp.setEmail(adminEmail);
            emp.setPassword(passwordEncoder.encode("KakaAdmin@123")); // Securely encoded password
            emp.setStatus(EmployeeStatus.ACTIVE);
            emp.setEmploymentType(EmploymentType.FULL_TIME);
            employeeRepository.save(emp);
            logger.info("Seeded Platform Admin: {}", adminEmail);
            return emp;
        });

        // 3. Assign PLATFORM_ADMIN Role
        Optional<Role> platformAdminRoleOpt = roleRepository.findByName("PLATFORM_ADMIN");
        if (platformAdminRoleOpt.isPresent()) {
            Role platformAdminRole = platformAdminRoleOpt.get();
            
            // Check if user already has this role
            List<UserRole> existingRoles = userRoleRepository.findByEmployee(adminEmployee);
            boolean hasRole = existingRoles.stream()
                    .anyMatch(ur -> ur.getRole().getName().equals("PLATFORM_ADMIN"));
                    
            if (!hasRole) {
                UserRole userRole = new UserRole();
                userRole.setEmployee(adminEmployee);
                userRole.setRole(platformAdminRole);
                userRoleRepository.save(userRole);
                logger.info("Assigned PLATFORM_ADMIN role to {}", adminEmail);
            }
        }
    }
}
