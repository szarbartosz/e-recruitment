import dao.StudentDao;
import dao.UniversityDao;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = StudentDao.getInstance();
        UniversityDao universityDao = UniversityDao.getInstance();
        try{
            studentDao.addExam(1, "angielski", 0.69);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
