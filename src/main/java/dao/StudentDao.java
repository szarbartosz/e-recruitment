package dao;

import model.Exam;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.TypedQuery;
import java.util.regex.Pattern;

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

    public void studentApply(int studentId, int fieldId){

    }
}
