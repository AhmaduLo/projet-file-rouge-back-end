package com.example.ShamblesProject.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShamblesProject.model.ERole;
import com.example.ShamblesProject.model.Role;
import com.example.ShamblesProject.model.User;
import com.example.ShamblesProject.repository.RoleRepository;
import com.example.ShamblesProject.repository.UserRepository;
import com.example.ShamblesProject.request.LoginRequest;
import com.example.ShamblesProject.request.SignupRequest;
import com.example.ShamblesProject.response.JwtResponse;
import com.example.ShamblesProject.response.MessageResponse;
import com.example.ShamblesProject.security.jwt.JwtUtils;
import com.example.ShamblesProject.service.UserDetailsImpl;
import com.example.ShamblesProject.service.UserService;







@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = (Authentication) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication) authentication);
        String jwt = jwtUtils.generateJwtToken((org.springframework.security.core.Authentication) authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) ((org.springframework.security.core.Authentication) authentication).getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (signUpRequest.getRoles() == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            signUpRequest.getRoles().forEach(role -> {
                        Role currentRole = roleRepository.findByName( ERole.valueOf(role))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(currentRole);
                    }
//                switch (role) {
//
//                    case ROLE_ADMIN:
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case ROLE_MODERATOR:
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                    case ROLE_USER:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                        break;
//                }
            );
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
    
    /*----------recuperer tout les utilisateurs----------------------------*/
	@GetMapping("/user")
	public Iterable<User> getUser(){
		return userService.getUser();
	}
	
	/*-------------recuperer by id-------------------------------*/
	@GetMapping("/user/{id}")
	public  User getUser(@PathVariable ("id") final Long id){
		Optional<User> User = userService.getUser(id);
		if(User.isPresent()) {
			return User.get();
		}else {
			return null;
		}
	}
	
//	-------------------delete user id---------------------------------------------------------------
    
	 @DeleteMapping("/user/{id}")
	    public void deleteUser(@PathVariable("id")  final Long id){
		 userService.deletUser(id);
	    }
    
    
    
    
    
    
    
    
    
}

