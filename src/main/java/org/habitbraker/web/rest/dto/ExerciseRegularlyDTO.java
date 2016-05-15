package org.habitbraker.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.habitbraker.domain.enumeration.Charities;

/**
 * A DTO for the ExerciseRegularly entity.
 */
public class ExerciseRegularlyDTO implements Serializable {

    private Long id;

    @NotNull
    private String committedTo;

    @NotNull
    private Double pledgedAmmount;

    @NotNull
    private Charities pledgedCharity;

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private ZonedDateTime endDate;

    private String notes;


    private Long userId;
    

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCommittedTo() {
        return committedTo;
    }

    public void setCommittedTo(String committedTo) {
        this.committedTo = committedTo;
    }
    public Double getPledgedAmmount() {
        return pledgedAmmount;
    }

    public void setPledgedAmmount(Double pledgedAmmount) {
        this.pledgedAmmount = pledgedAmmount;
    }
    public Charities getPledgedCharity() {
        return pledgedCharity;
    }

    public void setPledgedCharity(Charities pledgedCharity) {
        this.pledgedCharity = pledgedCharity;
    }
    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseRegularlyDTO exerciseRegularlyDTO = (ExerciseRegularlyDTO) o;

        if ( ! Objects.equals(id, exerciseRegularlyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseRegularlyDTO{" +
            "id=" + id +
            ", committedTo='" + committedTo + "'" +
            ", pledgedAmmount='" + pledgedAmmount + "'" +
            ", pledgedCharity='" + pledgedCharity + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
