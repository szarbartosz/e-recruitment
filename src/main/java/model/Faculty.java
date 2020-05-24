package model;

import com.google.gson.annotations.Expose;
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

    @Expose
    @NotNull
    private String name;

    @OneToMany(mappedBy = "faculty")
    private Set<Field> fields = new LinkedHashSet<>();

    public Faculty(String name){
        this.name = name;
    }

    public void addField(Field field){
        field.setFaculty(this);
        this.fields.add(field);
    }
}
