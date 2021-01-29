package org.scd.service;


import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.UserLocationAdminDTO;
import org.scd.model.dto.UserLocationDTO;
import org.scd.repository.LocationRepository;
import org.scd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Location> getAllLocations() {
        List<Location> userLocations = new ArrayList<>();
        locationRepository.findAll().forEach(userLocations::add);
        return userLocations;
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location update(Location location, Long id) {
        Location locationUpdated = locationRepository.getById(id);
        if (!(location.getLatitude() == null)) locationUpdated.setLatitude(location.getLatitude());
        if (!(location.getLongitude() == null)) locationUpdated.setLongitude(location.getLongitude());
        return locationRepository.save(locationUpdated);
    }

    @Override
    public UserLocationDTO addLocation(UserLocationDTO userLocationDTO) {

        locationRepository.save(new Location(userLocationDTO.getLatitude(), userLocationDTO.getLongitude(), userLocationDTO.getDate(), userRepository.findByEmail(userLocationDTO.getEmail())));
        return null;
    }


    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> getLocationsBetweenDates(UserLocationAdminDTO userLocationAdminDTO) throws BusinessException {
        if (userLocationAdminDTO.getStartDate().compareTo(userLocationAdminDTO.getEndDate()) > 0)
            throw new BusinessException(403,"ERROR");
        return locationRepository.customQuery(userLocationAdminDTO.getUserId(),userLocationAdminDTO.getStartDate(),userLocationAdminDTO.getEndDate());
    }

}
