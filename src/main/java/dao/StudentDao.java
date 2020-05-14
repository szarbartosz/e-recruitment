package dao;

import model.Candidate;
import model.Exam;
import model.Field;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentDao {
    private static StudentDao instance;
    private final Pattern emailPattern;

    public static StudentDao getInstance(){
        if (instance == null){
            instance = new StudentDao();
        }
        return instance;
    }

    private StudentDao(){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        this.emailPattern = Pattern.compile(emailRegex);
        Configuration config = new Configuration();
    }

    public void addStudent(String firstName, String secondName, String pesel, String email,
                           String address, String city, String zipCode) throws Exception {

        if (!this.emailPattern.matcher(email).matches()){
            throw new Exception("Incorrect email address");
        }

        if (pesel.length() != 11){
            throw new Exception("Incorrect pesel");
        }

        Student student = new Student(firstName, secondName, pesel, email, address, city, zipCode);
        Session session = SessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }

    public void addExam(int studentId, String subject, double result) throws Exception {
        if (result < 0.0 || result > 1.0){
            throw new Exception("Incorrect exam result");
        }

        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Student> query = session.createQuery("SELECT S From Student S WHERE S.studentId = :studentId", Student.class );
        query.setParameter("studentId", studentId);
        Student student = query.getSingleResult();
        Exam exam = new Exam(subject, result);
        student.addExam(exam);
        Transaction transaction = session.beginTransaction();
        session.save(exam);
        session.update(student);
        transaction.commit();
        session.close();
    }

    public void studentApply(int studentId, int fieldId) throws Exception{
        Session session = SessionFactoryDecorator.openSession();

        TypedQuery<Student> query1 = session.createQuery("SELECT S From Student S WHERE S.studentId = :studentId", Student.class);
        query1.setParameter("studentId", studentId);
        Student student = query1.getSingleResult();

        TypedQuery<Field> query2 = session.createQuery("SELECT F From Field F WHERE F.fieldId = :fieldId",Field.class);
        query2.setParameter("fieldId", fieldId);
        Field field = query2.getSingleResult();

        TypedQuery<Exam> query3 = session.createQuery("SELECT E From Exam E WHERE E.student = :student", Exam.class);
        query3.setParameter("student", student);
        List<Exam> allExamsList = query3.getResultList();
        List<Exam> mainExams = allExamsList.stream()
                .filter(exam -> field.getMainSubjects().contains(exam.getSubject()))
                .sorted(Comparator.comparing(Exam::getResult).reversed())
                .collect(Collectors.toList());

        List<Exam> mathExam =  allExamsList.stream()
                .filter(exam -> exam.getSubject().equals("Matematyka podstawowa"))
                .collect(Collectors.toList());

        if (mainExams.size() == 0 || mathExam.size() != 1){
            throw new Exception("Not enough exams");
        }

        Candidate candidate = new Candidate(false);
        candidate.setPointsNumber(mainExams.get(0).getResult() * 800 + mathExam.get(0).getResult() * 200);

        student.addCandidate(candidate);
        field.addCandidate(candidate);

        Transaction transaction = session.beginTransaction();
        session.save(candidate);
        session.update(student);
        session.update(field);
        transaction.commit();
        session.close();
    }
}
