package controller;

import dao.StudentDao;
import dao.UniversityDao;

public abstract class Controller {
    static final StudentDao studentDao = StudentDao.getInstance();
    static final UniversityDao universityDao = UniversityDao.getInstance();
}
