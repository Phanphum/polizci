package com.example.demo.controller;

import com.example.demo.service.CrimeIncidentService;
import com.example.demo.model.CrimeIncident;
import com.example.demo.model.CrimeIncidentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/crime-incidents")
public class CrimeIncidentController {

    private final CrimeIncidentService service;

    public CrimeIncidentController(CrimeIncidentService service) {
        this.service = service;
    }

    // GET /api/v1/crime-incidents
    // GET /api/v1/crime-incidents?type=Robbery
    @GetMapping
    public List<CrimeIncidentDto> getIncidents(
            @RequestParam(required = false) String type
    ) {
        List<CrimeIncident> list =
                (type == null || type.isBlank())
                        ? service.getAllIncidents()
                        : service.getIncidentsByType(type);

        return list.stream()
                .map(CrimeIncidentDto::fromEntity)
                .toList();
    }

    // POST /api/v1/crime-incidents
    @PostMapping
    public ResponseEntity<CrimeIncidentDto> createIncident(
            @RequestBody CrimeIncidentDto dto
    ) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        CrimeIncident c = new CrimeIncident();
        c.setType(dto.type());
        c.setPlaceName(dto.placeName());

        if (dto.time() != null && !dto.time().isBlank()) {
            c.setIncidentTime(LocalTime.parse(dto.time(), fmt)); // expects "HH:mm"
        } else {
            c.setIncidentTime(LocalTime.now());
        }

        c.setDescription(dto.description());
        c.setLatitude(dto.latitude());
        c.setLongitude(dto.longitude());

        CrimeIncident saved = service.save(c);
        CrimeIncidentDto body = CrimeIncidentDto.fromEntity(saved);

        return ResponseEntity
                .created(URI.create("/api/v1/crime-incidents/" + saved.getId()))
                .body(body);
    }
}