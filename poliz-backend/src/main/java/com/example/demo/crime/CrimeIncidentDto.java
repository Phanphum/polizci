package com.example.demo.crime;

import java.time.format.DateTimeFormatter;

public record CrimeIncidentDto(
        Long id,
        String type,
        String placeName,
        String time,
        String description,
        Double latitude,
        Double longitude
) {
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    public static CrimeIncidentDto fromEntity(CrimeIncident c) {
        String timeStr = c.getIncidentTime() != null
                ? c.getIncidentTime().format(TIME_FMT)   // "18:53"
                : null;

        return new CrimeIncidentDto(
                c.getId(),
                c.getType(),
                c.getPlaceName(),
                timeStr,
                c.getDescription(),
                c.getLatitude(),
                c.getLongitude()
        );
    }
}