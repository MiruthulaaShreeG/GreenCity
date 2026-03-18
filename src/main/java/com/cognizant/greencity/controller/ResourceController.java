package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.ResourceDTO;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.dao.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Resource getResourceById(@PathVariable UUID id) {
        return resourceRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Resource createResource(@RequestBody ResourceDTO dto) {
        Resource resource = new Resource();
        resource.setType(dto.getType());
        resource.setLocation(dto.getLocation());
        resource.setCapacity(dto.getCapacity());
        resource.setStatus(dto.getStatus());
        return resourceRepository.save(resource);
    }

    @PutMapping("/{id}")
    public Resource updateResource(@PathVariable UUID id, @RequestBody ResourceDTO dto) {
        return resourceRepository.findById(id).map(resource -> {
            resource.setType(dto.getType());
            resource.setLocation(dto.getLocation());
            resource.setCapacity(dto.getCapacity());
            resource.setStatus(dto.getStatus());
            return resourceRepository.save(resource);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable UUID id) {
        resourceRepository.deleteById(id);
    }
}
