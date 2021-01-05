package com.mtotowamkwe.lostboyzpetservice.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Pet {
    
    private UUID id;
    private int age;
    private char sex;
    private String description;
    private String name;
    private String type;
    private String url;
    private String breed;
    private boolean is_found;
    private String last_seen_location;
}
