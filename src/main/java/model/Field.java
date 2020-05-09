package model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fieldId;

    @NotNull
    private String name;

    @NotNull
    private int capacity;

    @ManyToOne
    @JoinColumn(name="FACULTY_FK")
    private Faculty faculty;

    @OneToMany(mappedBy = "field")
    private Set<MainSubject> mainSubjects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "field")
    private Set<Candidate> candidates = new LinkedHashSet<>();

    public Field(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public void addMainSubject(MainSubject mainSubject){
        mainSubject.setField(this);
        this.mainSubjects.add(mainSubject);
    }

    public void addCandidate(Candidate candidate){
        candidate.setField(this);
        this.candidates.add(candidate);
    }
}
