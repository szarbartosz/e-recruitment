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
@Table(name="Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    private String pesel;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    @OneToMany(mappedBy = "student")
    private Set<Exam> exams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Candidate> candidates = new LinkedHashSet<>();

    public Student(String firstName, String secondName, String pesel, String email, String address,
                   String city, String zipCode) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
        this.email = email;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
    }

    public void addExam(Exam exam){
        exam.setStudent(this);
        this.exams.add(exam);
    }

    public void addCandidate(Candidate candidate){
        candidate.setStudent(this);
        this.candidates.add(candidate);
    }
}
