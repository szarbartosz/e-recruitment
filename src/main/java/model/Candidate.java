package model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int candidateId;

    @NotNull
    private boolean isAccepted;

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