package model;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Candidates")
public class Candidate {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int candidateId;

    @Expose
    @NotNull
    private boolean isAccepted;

    @Expose
    private double pointsNumber;

    @ManyToOne
    @JoinColumn(name="STUDENT_FK")
    private Student student;

    @ManyToOne
    @JoinColumn(name="FIELD_FK")
    private Field field;

    public Candidate(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
