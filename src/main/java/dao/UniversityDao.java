package dao;

import model.Candidate;
import model.Faculty;
import model.Field;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UniversityDao {
    private static UniversityDao instance;

    public static UniversityDao getInstance(){
        if (instance == null){
            instance = new UniversityDao();
        }
        return instance;
    }

    public void addFaculty(String name) {
        Faculty faculty = new Faculty(name);
        Session session = SessionFactoryDecorator.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(faculty);
        transaction.commit();
        session.close();
    }

    public void addField(int facultyId, String name, int capacity) throws Exception {
        if(capacity <= 0) {
            throw new Exception("Incorrect capacity");
        }
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Faculty> query = session.createQuery("SELECT F FROM Faculty F WHERE F.facultyId = :facultyId", Faculty.class);
        query.setParameter("facultyId", facultyId);
        Faculty faculty = query.getSingleResult();
        Field field = new Field(name, capacity);
        faculty.addField(field);
        Transaction transaction = session.beginTransaction();
        session.save(field);
        transaction.commit();
        session.close();
    }

    public void addMainSubjectToField(int fieldId, String subjectName) {
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Field> query = session.createQuery("SELECT F FROM Field F WHERE F.fieldId = :fieldId", Field.class);
        query.setParameter("fieldId", fieldId);
        Field field = query.getSingleResult();
        field.addMainSubject(subjectName);
        Transaction transaction = session.beginTransaction();
        session.update(field);
        transaction.commit();
        session.close();
    }

    public void markAccepted(int fieldId) {
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Field> queryField = session.createQuery("SELECT F FROM Field F WHERE F.fieldId = :fieldId", Field.class);
        queryField.setParameter("fieldId", fieldId);
        Field field = queryField.getSingleResult();
        TypedQuery<Candidate> queryCandidates = session.createQuery("SELECT C FROM Candidate C WHERE C.field = :field", Candidate.class);
        queryCandidates.setParameter("field", field);
        ArrayList<Candidate> candidates = (ArrayList<Candidate>) queryCandidates.getResultList();
        candidates.sort(Collections.reverseOrder());
        int capacity = field.getCapacity();
        for (int i = 0; i < capacity; i++) {
            candidates.get(i).setAccepted(true);
        }
        Transaction transaction = session.beginTransaction();
        session.update(candidates);
        transaction.commit();
        session.close();
    }
}
