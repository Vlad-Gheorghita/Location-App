package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.UserLocationAdminDTO;
import org.scd.model.dto.UserLocationDTO;
import org.scd.service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/locations")
public class LocationController {
    private final LocationServiceImpl locationServiceImpl;

    @Autowired
    public LocationController(LocationServiceImpl locationServiceImpl) {
        this.locationServiceImpl = locationServiceImpl;
    }

    @GetMapping(path = "/allLocations")
    public List<Location> getAllLocations() {
        return locationServiceImpl.getAllLocations();
    }

    @GetMapping(path = "/locations/{locationId}")
    public Location getLocation(@PathVariable("locationId") Long locationId) {
        return locationServiceImpl.getLocationById(locationId);
    }


    @DeleteMapping(path = "/delete/{locationId}")
    public void deleteLocation(@PathVariable("locationId") Long locationId) {
        locationServiceImpl.deleteById(locationId);
    }

    @PutMapping(path = "/locations")
    public Location update(@RequestBody Location userLocation) {
        locationServiceImpl.update(userLocation, userLocation.getId());
        return userLocation;
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/updateLocation/{id}")
    public Location updateLocation(@RequestBody Location location, @PathVariable long id) {
        return locationServiceImpl.update(location, id);
    }

    @PostMapping(path = "/createLocation")
    public ResponseEntity<UserLocationDTO> addLocation(@RequestBody final UserLocationDTO userLocationDTO) {
        return ResponseEntity.ok(locationServiceImpl.addLocation(userLocationDTO));
    }

    @GetMapping(path = "/getLocationById")
    public ResponseEntity<Optional<Location>> getLocationById(@RequestBody final Location userLocation) {
        return ResponseEntity.ok(locationServiceImpl.findById(userLocation.getId()));
    }

    @PostMapping(path = "/filterByStartAndEnd")
    public List<Location> getLocationByDates(@RequestBody UserLocationAdminDTO userLocationAdminDTO) throws BusinessException {
        return locationServiceImpl.getLocationsBetweenDates(userLocationAdminDTO);
    }
}
