package com.ranjay.directory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Business {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    @Email
    @NotEmpty
    @NotNull
    private String email;
    private String location;
    private String website;
    @NotEmpty
    @NotNull
    private String category;

    public Business(@NotEmpty String name, @Email @NotEmpty String email, String location, String website,
            @NotEmpty String category) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.website = website;
        this.category = category;
    }
    
}