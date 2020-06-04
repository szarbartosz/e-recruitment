package dao;

import model.Candidate;
import model.Faculty;
import model.Field;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class UniversityDao {
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
        Faculty faculty = session.load(Faculty.class, facultyId);
        Field field = new Field(name, capacity);
        faculty.addField(field);
        Transaction transaction = session.beginTransaction();
        session.save(field);
        transaction.commit();
        session.close();
    }

    public void addMainSubjectToField(int fieldId, String subjectName) {
        Session session = SessionFactoryDecorator.openSession();
        Field field = session.get(Field.class, fieldId);
        field.addMainSubject(subjectName);
        Transaction transaction = session.beginTransaction();
        session.update(field);
        transaction.commit();
        session.close();
    }

    public void markAccepted(int fieldId) {
        Session session = SessionFactoryDecorator.openSession();
        Field field = session.get(Field.class, fieldId);
        field.setRecruitmentEnded(true);
        TypedQuery<Candidate> queryCandidates = session.createQuery("SELECT C FROM Candidate C WHERE C.field = :field", Candidate.class);
        queryCandidates.setParameter("field", field);
        List<Candidate> candidates = queryCandidates.getResultStream().sorted(Comparator.comparing(Candidate::getPointsNumber).reversed()).collect(Collectors.toList());

        int capacity = field.getCapacity();
        Transaction transaction = session.beginTransaction();
        for (int i = 0; i < capacity && i < candidates.size(); i++) {
            candidates.get(i).setAccepted(true);
            session.update(candidates.get(i));
        }
        transaction.commit();
        session.close();
    }

    public Collection<Faculty> getAllFaculties(){
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Faculty> query = session.createQuery("From Faculty F", Faculty.class);
        Collection<Faculty> collection = query.getResultList();
        session.close();
        return collection;
    }

    public Collection<Field> getAllFields(){
        Session session = SessionFactoryDecorator.openSession();
        TypedQuery<Field> query = session.createQuery("From Field F", Field.class);
        Collection<Field> collection = query.getResultList();
        session.close();
        return collection;
    }
}
