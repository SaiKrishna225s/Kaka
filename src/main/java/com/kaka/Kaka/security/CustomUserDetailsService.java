package com.kaka.Kaka.security;

import com.kaka.Kaka.entity.Employee;
import com.kaka.Kaka.entity.UserRole;
import com.kaka.Kaka.repository.EmployeeRepository;
import com.kaka.Kaka.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository, UserRoleRepository userRoleRepository) {
        this.employeeRepository = employeeRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<UserRole> userRoles = userRoleRepository.findByEmployee(employee);
        
        List<GrantedAuthority> authorities = userRoles.stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(employee, authorities);
    }
}
