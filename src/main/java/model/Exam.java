package model;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int examId;

    @NotNull
    private String subject;

    @NotNull
    private double result;

    @ManyToOne
    @JoinColumn(name="STUDENT_FK")
    private Student student;

    public Exam(String subject, double result) {
        this.subject = subject;
        this.result = result;
    }
}
