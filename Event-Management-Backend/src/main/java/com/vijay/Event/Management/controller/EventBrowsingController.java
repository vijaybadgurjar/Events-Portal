package com.vijay.Event.Management.controller;



import com.vijay.Event.Management.dto.TicketDto.TicketCreateRequestDto;
import com.vijay.Event.Management.model.Ticket;
import com.vijay.Event.Management.service.EventService;
import com.vijay.Event.Management.service.TicketService;
import jakarta.validation.Valid;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller where each method returns a ResponseEntity or JSON response
@RequestMapping("/api/events") // Base path for all endpoints in this controller
@RequiredArgsConstructor // Lombok will automatically create a constructor for final fields (Dependency Injection)
public class EventBrowsingController {

    private final EventService eventService;
    private final TicketService ticketService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents(){
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId){
        Event event = eventService.findByID(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping("/{eventId}/book")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> bookEvent(@PathVariable Long eventId, @Valid @RequestBody TicketCreateRequestDto ticketDto){
        Ticket ticket = ticketService.createTicket(eventId, ticketDto);
        return ResponseEntity.ok(ticket);
    }

}