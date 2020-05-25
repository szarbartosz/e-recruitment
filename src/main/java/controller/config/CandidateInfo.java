package controller.config;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Candidate;
import model.Faculty;
import model.Field;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CandidateInfo {
    @Id
    @Expose
    private int candidateId;
    @Expose
    private boolean isAccepted;
    @Expose
    private double pointsNumber;
    @Expose
    private String facultyName;
    @Expose
    private String fieldName;
    @Expose
    private boolean recruitmentEnded;

    public CandidateInfo(int candidateId, boolean isAccepted, double pointsNumber, String facultyName, String fieldName, boolean recruitmentEnded) {
        this.candidateId = candidateId;
        this.isAccepted = isAccepted;
        this.pointsNumber = pointsNumber;
        this.facultyName = facultyName;
        this.fieldName = fieldName;
        this.recruitmentEnded = recruitmentEnded;
    }

    public CandidateInfo(Candidate candidate, Faculty faculty, Field field) {
        this.candidateId = candidate.getCandidateId();
        this.isAccepted = candidate.isAccepted();
        this.pointsNumber = candidate.getPointsNumber();
        this.facultyName = faculty.getName();
        this.fieldName = field.getName();
        this.recruitmentEnded = field.isRecruitmentEnded();
    }
}