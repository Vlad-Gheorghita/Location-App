package org.scd.repository;

import org.scd.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface LocationRepository extends CrudRepository<Location, Long> {
    Location save(Location location);

    @Query(value = "SELECT * FROM locations WHERE user_id=?1 AND creation_date>=?2 AND creation_date<=?3", nativeQuery = true)
    List<Location> customQuery(final Long userId, final Date startDate, final Date endDate);

    Location getById(final Long id);
}
