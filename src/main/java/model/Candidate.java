package model;


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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int candidateId;

    @NotNull
    private boolean isAccepted;

    public Candidate(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @ManyToOne
    @JoinColumn(name="STUDENT_FK")
    private Student student;


}
