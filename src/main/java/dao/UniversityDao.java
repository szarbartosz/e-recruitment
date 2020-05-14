package dao;

import model.Faculty;
import model.Field;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.TypedQuery;

public class UniversityDao {
    private static UniversityDao instance;

    public UniversityDao() {
        Configuration config = new Configuration();
    }

    public void addFaculty(String name) {
        Faculty faculty = new Faculty(name);
        Session session = SessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(faculty);
        transaction.commit();
        session.close();
    }

    private Faculty getFacultyById(int facultyId) {
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Faculty> query = session.createQuery("SELECT F FROM Faculty F WHERE F.facultyId = :facultyId", Faculty.class);
        query.setParameter("facultyId", facultyId);
        return query.getSingleResult();
    }

    public void addField(int facultyId, String name, int capacity) throws Exception {
        if(capacity <= 0) {
            throw new Exception("Incorrect capacity");
        }
        Faculty faculty = getFacultyById(facultyId);

        Field field = new Field(name, capacity);
        faculty.addField(field);
        Session session = SessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(field);
        transaction.commit();
        session.close();
    }

    private Field getFieldById(int fieldId) {
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Field> query = session.createQuery("SELECT F FROM Field F WHERE F.fieldId = :fieldId", Field.class);
        query.setParameter("fieldId", fieldId);
        return query.getSingleResult();
    }

    public void addMainSubjectToField(int fieldId, String subjectName) {
        Field field = getFieldById(fieldId);

        field.addMainSubject(subjectName);
        Session session = SessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(field);
        transaction.commit();
        session.close();

    }
}
