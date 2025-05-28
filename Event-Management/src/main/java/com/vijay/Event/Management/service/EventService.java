package com.vijay.Event.Management.service;

import com.vijay.Event.Management.dto.EventDto.EventCreateRequestDto;
import com.vijay.Event.Management.dto.EventDto.EventUpdateRequestDto;
import com.vijay.Event.Management.exception.EventNotFoundException;
import com.vijay.Event.Management.model.User;
import com.vijay.Event.Management.repository.EventRepository;
import com.vijay.Event.Management.repository.TicketRepository;
import com.vijay.Event.Management.repository.UserRepository;
import com.vijay.Event.Management.utils.securityUtils.SecurityUtils;
import jakarta.validation.Valid;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok will automatically create a constructor for 'final' fields(Dependency Injection)
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SecurityUtils securityUtils;

    public void createEvent(@Valid EventCreateRequestDto eventDto) {
        User user = getLoggedInUserHelper();
        Event event = CreateEventHelper(eventDto, user);

        eventRepository.save(event);
    }


    public void updateEvent(Long eventId, @Valid EventUpdateRequestDto eventDto) {
        User organizer = getLoggedInUserHelper();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!"));

        if(!event.getOrganizerId().equals(organizer.getId())){
            throw new EventNotFoundException("Event not found!");
        }
        UpdateEventHelper(eventDto, event);
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!"));

        // Also Delete All the tickets of this event
        ticketRepository.deleteByEventId(eventId);

        eventRepository.delete(event);
    }

    public List<Event> getAllEventsOfLoggedInOrganizer() {
        User organizer = getLoggedInUserHelper();
        Long organizerId = organizer.getId();
        return eventRepository.findByOrganizerId(organizerId);
    }


    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event findByID(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!"));
    }

    public List<String> getAllUsersOfEvent(Long eventId) {
        User organizer = getLoggedInUserHelper();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!"));
        if(!Objects.equals(event.getOrganizerId(), organizer.getId())){
            throw new EventNotFoundException("Event not found!");
        }
        return ticketRepository.findUsersByEventId(event.getId());
    }





    //////////////////////// Helper Methods //////////////////////////////////

    private User getLoggedInUserHelper() {
        UserDetails userDetails = securityUtils.getAuthenticatedUserDetails();
        String email = userDetails.getUsername();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }
        return userOptional.get();
    }

    private Event CreateEventHelper(EventCreateRequestDto eventDto, User user) {
        Event event = new Event();

        event.setOrganizerId(user.getId());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setLocation(eventDto.getLocation());
        event.setCategory(eventDto.getCategory());
        event.setQuantity(eventDto.getQuantity());
        event.setPrice(eventDto.getPrice());
        event.setContact(eventDto.getContact());
        event.setImageUrl(eventDto.getImageUrl());
        event.setStatus(eventDto.getStatus());
        return event;
    }

    private void UpdateEventHelper(EventUpdateRequestDto eventDto, Event event) {
        if(eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if(eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if(eventDto.getStartTime() != null) {
            event.setStartTime(eventDto.getStartTime());
        }
        if(eventDto.getEndTime() != null) {
            event.setEndTime(eventDto.getEndTime());
        }
        if(eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if(eventDto.getCategory() != null) {
            event.setCategory(eventDto.getCategory());
        }
        if(eventDto.getQuantity() != null){
            event.setQuantity(eventDto.getQuantity());
        }
        if(eventDto.getPrice() != null) {
            event.setPrice(eventDto.getPrice());
        }
        if(eventDto.getContact() != null) {
            event.setContact(eventDto.getContact());
        }
        if(eventDto.getImageUrl() != null) {
            event.setImageUrl(eventDto.getImageUrl());
        }
        if(eventDto.getStatus() != null) {
            event.setStatus(eventDto.getStatus());
        }
    }

}
