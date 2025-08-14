package com.shophouse.service;

import com.shophouse.config.AppProperties;
import com.shophouse.error.exception.BadRequestException;
import com.shophouse.model.dto.auth.AdminCreationRequest;
import com.shophouse.model.dto.auth.JwtAuthenticationResponse;
import com.shophouse.model.dto.auth.LoginRequest;
import com.shophouse.model.dto.auth.UserRegistrationRequest;
import com.shophouse.model.entity.Role;
import com.shophouse.model.enums.RoleName;
import com.shophouse.repository.RoleRepository;
import com.shophouse.repository.UserRepository;
import com.shophouse.security.JwtTokenProvider;
import com.shophouse.model.entity.User;
import com.shophouse.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Transactional
    public JwtAuthenticationResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("already taken", "this username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("already in use", "this email address is already in use");
        }

        // Get USER role - convert enum to string
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BadRequestException("no role", "user role not set"));

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(true)
                .roles(Set.of(userRole))
                .build();

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getUsername());

        // Generate JWT token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return createAuthResponse(authentication);
    }

    @Transactional
    public JwtAuthenticationResponse createAdmin(AdminCreationRequest request) {
        // Verify admin creation key
        if (!appProperties.getAdminCreationKey().equals(request.getAdminKey())) {
            throw new BadRequestException("Invalid key!", "invalid admin creation key");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("already taken!", "this username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("already in use!", "this email address is already in use");
        }

        // Get ADMIN role - convert enum to string
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new BadRequestException("no role.", "admin role not set"));

        // Create new admin user
        User admin = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(true)
                .roles(Set.of(adminRole))
                .build();

        User savedAdmin = userRepository.save(admin);
        log.info("New admin created: {}", savedAdmin.getUsername());

        // Generate JWT token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return createAuthResponse(authentication);
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createAuthResponse(authentication);
    }

    private JwtAuthenticationResponse createAuthResponse(Authentication authentication) {
        String jwt = tokenProvider.generateToken(authentication);
        Date expiryDate = tokenProvider.getExpirationDateFromToken(jwt);
        long expiresIn = expiryDate.getTime() - System.currentTimeMillis();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Set<String> roles = userPrincipal.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .collect(java.util.stream.Collectors.toSet());

        return new JwtAuthenticationResponse(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                roles,
                expiresIn
        );
    }
}
