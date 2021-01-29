package org.scd.service;

import org.scd.model.Location;
import org.scd.model.dto.UserLocationDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAllLocations();

    Location getLocationById(final Long id);

    void deleteById(final Long id);

    Location update(Location userLocation, Long id);

    UserLocationDTO addLocation(final UserLocationDTO userLocationDTO);

    Optional<Location> findById(final Long id);

    //List<Location> getLocationsFromStartToEnd(Date startDate, Date endDate);
}
