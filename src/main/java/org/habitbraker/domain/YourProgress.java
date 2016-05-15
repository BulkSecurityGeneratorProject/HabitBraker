package org.habitbraker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A YourProgress.
 */
@Entity
@Table(name = "your_progress")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YourProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "met_goal_today", nullable = false)
    private Boolean metGoalToday;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private ExerciseRegularly exerciseRegularly;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMetGoalToday() {
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

    public ExerciseRegularly getExerciseRegularly() {
        return exerciseRegularly;
    }

    public void setExerciseRegularly(ExerciseRegularly exerciseRegularly) {
        this.exerciseRegularly = exerciseRegularly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YourProgress yourProgress = (YourProgress) o;
        if(yourProgress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yourProgress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YourProgress{" +
            "id=" + id +
            ", metGoalToday='" + metGoalToday + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
