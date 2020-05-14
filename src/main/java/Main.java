import dao.StudentDao;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = StudentDao.getInstance();
        try{
            studentDao.addExam(1, "język polski", 0.4);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
