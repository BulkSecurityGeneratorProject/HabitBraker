package org.habitbraker.web.rest.mapper;

import org.habitbraker.domain.*;
import org.habitbraker.web.rest.dto.YourProgressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity YourProgress and its DTO YourProgressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface YourProgressMapper {

    @Mapping(source = "exerciseRegularly.id", target = "exerciseRegularlyId")
    @Mapping(source = "exerciseRegularly.committedTo", target = "exerciseRegularlyCommittedTo")
    YourProgressDTO yourProgressToYourProgressDTO(YourProgress yourProgress);

    List<YourProgressDTO> yourProgressesToYourProgressDTOs(List<YourProgress> yourProgresses);

    @Mapping(source = "exerciseRegularlyId", target = "exerciseRegularly")
    YourProgress yourProgressDTOToYourProgress(YourProgressDTO yourProgressDTO);

    List<YourProgress> yourProgressDTOsToYourProgresses(List<YourProgressDTO> yourProgressDTOs);

    default ExerciseRegularly exerciseRegularlyFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseRegularly exerciseRegularly = new ExerciseRegularly();
        exerciseRegularly.setId(id);
        return exerciseRegularly;
    }
}
