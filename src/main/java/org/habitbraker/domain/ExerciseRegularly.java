package org.habitbraker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.habitbraker.domain.enumeration.Charities;

/**
 * A ExerciseRegularly.
 */
@Entity
@Table(name = "exercise_regularly")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExerciseRegularly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "committed_to", nullable = false)
    private String committedTo;

    @NotNull
    @Column(name = "pledged_ammount", nullable = false)
    private Double pledgedAmmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pledged_charity", nullable = false)
    private Charities pledgedCharity;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseRegularly exerciseRegularly = (ExerciseRegularly) o;
        if(exerciseRegularly.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseRegularly.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseRegularly{" +
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
