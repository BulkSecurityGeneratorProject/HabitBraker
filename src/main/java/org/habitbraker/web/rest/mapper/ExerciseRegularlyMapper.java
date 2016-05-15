package org.habitbraker.web.rest.mapper;

import org.habitbraker.domain.*;
import org.habitbraker.web.rest.dto.ExerciseRegularlyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ExerciseRegularly and its DTO ExerciseRegularlyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ExerciseRegularlyMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ExerciseRegularlyDTO exerciseRegularlyToExerciseRegularlyDTO(ExerciseRegularly exerciseRegularly);

    List<ExerciseRegularlyDTO> exerciseRegularliesToExerciseRegularlyDTOs(List<ExerciseRegularly> exerciseRegularlies);

    @Mapping(source = "userId", target = "user")
    ExerciseRegularly exerciseRegularlyDTOToExerciseRegularly(ExerciseRegularlyDTO exerciseRegularlyDTO);

    List<ExerciseRegularly> exerciseRegularlyDTOsToExerciseRegularlies(List<ExerciseRegularlyDTO> exerciseRegularlyDTOs);
}
