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
@Table(name="MainSubjects")
public class MainSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mainSubjectId;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="FIELD_FK")
    private Field field;

    public MainSubject(String name) {
        this.name = name;
    }
}
