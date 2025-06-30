package com.vijay.Event.Management.dto.EventDto;


import com.vijay.Event.Management.Enum.EventCategory;
import com.vijay.Event.Management.Enum.EventStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventUpdateRequestDto {

    private String title;
    private String description;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String contact;
    private Integer quantity;
    private Integer price;
    private String imageUrl;
    private EventCategory category;
    private EventStatus status;
}