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
@Table(name="Students")
public class Student {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;

    @Expose
    @NotNull
    private String firstName;

    @Expose
    @NotNull
    private String lastName;

    @Expose
    @NotNull
    private String pesel;

    @Expose
    @NotNull
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "student")
    private Set<Exam> exams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Candidate> candidates = new LinkedHashSet<>();

    public Student(String firstName, String lastName, String pesel, String email, String street,
                   String buildingNumber, String zipCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.email = email;
        this.address = new Address(street, buildingNumber, zipCode, city);
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
