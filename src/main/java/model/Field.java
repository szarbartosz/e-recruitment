package model;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Fields")
public class Field {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fieldId;

    @Expose
    @NotNull
    private String name;

    @Expose
    @NotNull
    private int capacity;

    @Expose
    @ManyToOne
    @JoinColumn(name="FACULTY_FK")
    private Faculty faculty;

    @ElementCollection
    private List<String> mainSubjects = new ArrayList<>();

    @Expose
    @NotNull
    private boolean recruitmentEnded;

    @OneToMany(mappedBy = "field")
    private Set<Candidate> candidates = new LinkedHashSet<>();

    public Field(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.recruitmentEnded = false;
    }

    public void addMainSubject(String mainSubject){
        this.mainSubjects.add(mainSubject);
    }

    public void addCandidate(Candidate candidate){
        candidate.setField(this);
        this.candidates.add(candidate);
    }
}
