import model.Exam;
import model.Student;
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
        student.addExam(exam);

        session.save(student);
        session.save(exam);

        tran.commit();
        session.close();

    }
}
