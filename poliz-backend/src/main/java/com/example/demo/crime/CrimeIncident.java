package com.example.demo.crime;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "crime_incidents")
public class CrimeIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "Robbery", "Accident", "Violence", "Arson"
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String placeName;

    // Time of incident (HH:mm), no date
    @Column(name = "incident_time", nullable = false)
    private LocalTime incidentTime;

    @Column(length = 2000)
    private String description;

    // Coordinates for the map markers (OpenStreetMap / whatever you use)
    private Double latitude;
    private Double longitude;

    public CrimeIncident() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public LocalTime getIncidentTime() { return incidentTime; }
    public void setIncidentTime(LocalTime incidentTime) { this.incidentTime = incidentTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}