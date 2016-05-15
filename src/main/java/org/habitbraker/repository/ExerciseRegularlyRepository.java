package org.habitbraker.repository;

import org.habitbraker.domain.ExerciseRegularly;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseRegularly entity.
 */
@SuppressWarnings("unused")
public interface ExerciseRegularlyRepository extends JpaRepository<ExerciseRegularly,Long> {

    @Query("select exerciseRegularly from ExerciseRegularly exerciseRegularly where exerciseRegularly.user.login = ?#{principal.username}")
    List<ExerciseRegularly> findByUserIsCurrentUser();

}
