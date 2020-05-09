import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        SessionFactory factory = config.configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tran = session.beginTransaction();

        Student student = new Student("Bartosz", "Szar", "990920", "adsasd", "asdas", "assss", "asdasd");
        Exam exam = new Exam("Matematyka", 0.99);
        Candidate candidate = new Candidate(false);


        student.addExam(exam);
        student.addCandidate(candidate);


        Faculty faculty = new Faculty("Informatyki, Elektroniki i Telekomunikacji");
        Field field = new Field("Informatyka", 260);
        field.addMainSubject("Informatyka");
        field.addMainSubject("Matematyka");
        faculty.addField(field);

        field.addCandidate(candidate);

        session.save(student);
        session.save(exam);
        session.save(candidate);
        session.save(faculty);
        session.save(field);


        tran.commit();
        session.close();

    }
}
