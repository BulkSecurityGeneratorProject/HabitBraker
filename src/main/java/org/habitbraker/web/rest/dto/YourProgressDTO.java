package org.habitbraker.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the YourProgress entity.
 */
public class YourProgressDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean metGoalToday;

    @NotNull
    private LocalDate date;


    private Long exerciseRegularlyId;
    

    private String exerciseRegularlyCommittedTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getMetGoalToday() {
        return metGoalToday;
    }

    public void setMetGoalToday(Boolean metGoalToday) {
        this.metGoalToday = metGoalToday;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getExerciseRegularlyId() {
        return exerciseRegularlyId;
    }

    public void setExerciseRegularlyId(Long exerciseRegularlyId) {
        this.exerciseRegularlyId = exerciseRegularlyId;
    }


    public String getExerciseRegularlyCommittedTo() {
        return exerciseRegularlyCommittedTo;
    }

    public void setExerciseRegularlyCommittedTo(String exerciseRegularlyCommittedTo) {
        this.exerciseRegularlyCommittedTo = exerciseRegularlyCommittedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        YourProgressDTO yourProgressDTO = (YourProgressDTO) o;

        if ( ! Objects.equals(id, yourProgressDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YourProgressDTO{" +
            "id=" + id +
            ", metGoalToday='" + metGoalToday + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
