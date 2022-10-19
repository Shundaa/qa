package com.qa.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long owner;
    private String description;
    private String ownerName;
    private String ownerLastName;
    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY
    )
    private List<ClassRegistration> registrations = new ArrayList<>();
}
