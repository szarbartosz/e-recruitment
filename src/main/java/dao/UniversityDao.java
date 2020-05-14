package dao;

import model.Faculty;
import model.Field;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UniversityDao {
    private static UniversityDao instance;

    public UniversityDao() {
        Configuration config = new Configuration();
    }

    public void addFaculty(String name) {
        Faculty faculty = new Faculty(name);
        Session session = sessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(faculty);
        transaction.commit();
        session.close();
    }

    public void addField(int facultyId, String name, int capacity) {
        Field field = new Field()
    }
}
