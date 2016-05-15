package org.habitbraker.repository;

import org.habitbraker.domain.YourProgress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YourProgress entity.
 */
@SuppressWarnings("unused")
public interface YourProgressRepository extends JpaRepository<YourProgress,Long> {

}
