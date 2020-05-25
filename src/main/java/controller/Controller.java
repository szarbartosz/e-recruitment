package controller;

import dao.StudentDao;
import dao.UniversityDao;

public abstract class Controller {
    static final StudentDao studentDao = new StudentDao();
    static final UniversityDao universityDao = new UniversityDao();
}
