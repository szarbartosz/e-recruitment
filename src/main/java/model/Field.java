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
@Table(name="Fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fielsId;

    @NotNull
    private String name;

    @NotNull
    private int capacity;

    @ManyToOne
    @JoinColumn(name="FACULTY_FK")
    private Faculty faculty;

    @OneToMany
    private Set<MainSubject> mainSubjects = new LinkedHashSet<>();

    public Field(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public void addMainSubject(MainSubject mainSubject){
        mainSubject.setField(this);
        this.mainSubjects.add(mainSubject);
    }
}
