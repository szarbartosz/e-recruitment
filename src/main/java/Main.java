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


        tran.commit();
        session.close();

    }
}
