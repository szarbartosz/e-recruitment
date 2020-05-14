import dao.StudentDao;
import dao.UniversityDao;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = StudentDao.getInstance();
        UniversityDao universityDao = UniversityDao.getInstance();
        try{
            universityDao.addMainSubjectToField(11, "Matematyka");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
