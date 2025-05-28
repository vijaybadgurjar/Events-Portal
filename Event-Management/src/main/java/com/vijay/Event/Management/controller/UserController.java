package com.vijay.Event.Management.controller;

import com.vijay.Event.Management.dto.UserDto.UserUpdateRequestDto;
import com.vijay.Event.Management.model.Ticket;
import com.vijay.Event.Management.model.User;
import com.vijay.Event.Management.service.TicketService;
import com.vijay.Event.Management.service.UserService;
import com.vijay.Event.Management.utils.securityUtils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller where each method returns a ResponseEntity or JSON response
@RequestMapping("/api/users") // Base path for all endpoints in this controller
@RequiredArgsConstructor // Lombok will automatically create a constructor for final fields (Dependency Injection)
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final SecurityUtils securityUtils;
    private final TicketService ticketService;
    private final UserService userService;



    @GetMapping("/")
    public ResponseEntity<?> getCurrentUser() throws Exception {
        UserDetails userDetails = securityUtils.getAuthenticatedUserDetails();
        String username = userDetails.getUsername();
        User user = userService.findByEmail(username);

        return ResponseEntity.ok(user);
    }


    @PutMapping("/")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequestDto userDto) throws Exception {

        UserDetails userDetails = securityUtils.getAuthenticatedUserDetails();
        String username = userDetails.getUsername();

        userService.updateUser(username, userDto);
        return ResponseEntity.ok("User updated successfully!");
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        User user = userService.findByID(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getTickets() throws Exception {
        List<Ticket> tickets = ticketService.getTickets();
        return ResponseEntity.ok(tickets);
    }

}