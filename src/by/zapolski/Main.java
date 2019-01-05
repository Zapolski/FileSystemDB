package by.zapolski;

import by.zapolski.dao.MyModelDao;
import by.zapolski.exception.DaoException;
import by.zapolski.exception.DaoSystemException;
import by.zapolski.exception.NoSuchEntityException;
import by.zapolski.dao.MyFileDaoImpl;
import by.zapolski.model.MyFile;

public class Main {
    public static void main(String[] args) {

        //InitDB.fillDB("c:\\TestDir");

        MyFileDaoImpl myFileDaoImpl = new MyFileDaoImpl();

        try {
            MyFile result = myFileDaoImpl.readByName("Самолет.pdf");
            System.out.println(result);
            //myFileDaoImpl.createByName(new MyFile("dir1","fly.txt",12345));
            myFileDaoImpl.updateByName(result.getName(),new MyFile(result.getParentDir(),result.getName(),1));
            result = myFileDaoImpl.readByName("Самолет.pdf");
            System.out.println(result);
            myFileDaoImpl.deleteByName("Самолет.pdf");

            result = myFileDaoImpl.readByName("Самолет.pdf");
            System.out.println(result);

        }catch (DaoException e){
            System.err.println(e);
            //e.printStackTrace();
        }

        myFileDaoImpl.closeMyFileDao();

    }
}
