package by.zapolski;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InitDB {

    public static void fillDB(String initDir){
        List<MyDirectory> myDirectoryList = new ArrayList<>();
        List<MyFile> myFileList = new ArrayList<>();
        Queue<File> queue = new LinkedList<>();
        //File rootFolder = new File("c:\\TestDir");
        File rootFolder = new File(initDir);
        queue.offer(rootFolder);
        while (!queue.isEmpty()){
            for (File file :queue.poll().listFiles()){
                String parentDir = file.getParent();
                int index = parentDir.lastIndexOf(File.separator)+1;
                parentDir = parentDir.substring(index);

                if (file.isDirectory()){
                    queue.offer(file);
                    myDirectoryList.add(new MyDirectory(parentDir,file.getName()));
                } else {
                    long lenght = file.length();
                    myFileList.add(new MyFile(parentDir,file.getName(),lenght));
                }
                //System.out.println(file.getAbsolutePath()+" ["+file.getParent()+"]");
            }
        }
        //myDirectoryList.forEach(System.out::println);
        //myFileList.forEach(System.out::println);

        ConnectorDB connection = new ConnectorDB();
        Statement st = null;
        PreparedStatement ps = null;

        //заполняем таблицу каталогов
        String clearTableDirectories = "TRUNCATE TABLE directories;";//предваритель очищаем таблицу
        String INSERT_TO_DIRECTORIES = "INSERT INTO directories (parentdir,namedir) VALUES (?,?)";

        try {
            st = connection.getStatement();
            st.execute(clearTableDirectories);
            ps = connection.getPreparedStatement(INSERT_TO_DIRECTORIES);
            for (MyDirectory dir: myDirectoryList){
                ps.setString(1,dir.getParentDir());
                ps.setString(2,dir.getName());
                ps.addBatch();
            }
            int[] updateCounts = ps.executeBatch();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            connection.closeStatement(st);
            connection.closeStatement(ps);
        }

        //заполняем таблицу файлов
        String clearTableFiles = "TRUNCATE TABLE files;";
        String INSERT_TO_FILES = "INSERT INTO files (parentdir,filename,lenght) VALUES (?,?,?)";
        try {
            st = connection.getStatement();
            st.execute(clearTableFiles);
            ps = connection.getPreparedStatement(INSERT_TO_FILES);
            for (MyFile fl: myFileList){
                ps.setString(1,fl.getParentDir());
                ps.setString(2,fl.getName());
                ps.setLong(3,fl.getLenght());
                ps.addBatch();
            }
            int[] updateCounts = ps.executeBatch();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            connection.closeStatement(st);
            connection.closeStatement(ps);
        }

    }
}
