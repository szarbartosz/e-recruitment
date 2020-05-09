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
@Table(name="Faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int facultyId;

    @NotNull
    private String name;

    @OneToMany
    private Set<Field> fields = new LinkedHashSet<>();

    public Faculty(String name){
        this.name = name;
    }
}
