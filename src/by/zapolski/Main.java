package by.zapolski;

import by.zapolski.exception.NoSuchEntityException;
import by.zapolski.model.MyFileDao;

public class Main {
    public static void main(String[] args) {
        //InitDB.fillDB("c:\\TestDir");

        MyFileDao myFileDao = new MyFileDao();

        try {
            System.out.println(myFileDao.selectByName("Самолет.pdf"));
        } catch (NoSuchEntityException e) {
            System.err.println(e);
        }

        myFileDao.closeMyFileDao();

    }
}
