import dao.StudentDao;
import dao.UniversityDao;
import model.Exam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = StudentDao.getInstance();
        UniversityDao universityDao = UniversityDao.getInstance();
        try{
            studentDao.studentApply(15, 20);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
