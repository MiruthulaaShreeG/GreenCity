package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Resource")
@Getter
@Setter
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResourceID", updatable = false, nullable = false)
    private Integer resourceId;

    @Column(name = "Type", nullable = false, length = 50)
    private String type; // Energy / Water / Waste

    @Column(name = "Location", nullable = false, length = 255)
    private String location;

    @Column(name = "Capacity", nullable = false)
    private Double capacity;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    // One resource can have multiple usage records
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceUsage> usages;

    @ManyToOne(optional=true)
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;

    // Getters and Setters

}

