package com.qa.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "chapter",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Lesson> lessons;


    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

}
