package by.zapolski;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectorDB {
    private static final String FILE_PROP_PATH = System.getProperty("user.dir")+ File.separator+
                                                    "src"+File.separator+"database.properties";
    private static final String URL = "url";
    private Connection connection;

    public ConnectorDB() {
        Properties prop = new Properties();
        String url;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            FileInputStream fis = new FileInputStream(FILE_PROP_PATH);
            prop.load(fis);
            url = prop.getProperty(URL);
            prop.remove(URL);
            connection = DriverManager.getConnection(url, prop);
        } catch (IOException e) {
            System.err.println(e);
        } catch (SQLException e){
            System.err.println("not obtained connection "+e);
        }

    }

    public Statement getStatement() throws SQLException{
        if (connection !=null){
            Statement statement = connection.createStatement();
            if (statement != null){
                return statement;
            }
        }
        throw new SQLException("connection or statement is null");
    }

    public void closeStatement (Statement statement){
        if (statement!=null){
            try {
                statement.close();
            }catch (SQLException e){
                System.err.println("statement is null "+e);
            }
        }
    }

    public void closeConnection(){
        if (connection !=null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("wrong connection "+e);
            }
        }
    }

    public PreparedStatement getPreparedStatement(String sqlString) throws SQLException{
        if (connection !=null){
            PreparedStatement statement = connection.prepareStatement(sqlString);
            if (statement != null){
                return statement;
            }
        }
        throw new SQLException("connection or statement is null");
    }

    public void setAutoCommit(boolean b) throws SQLException{
        if (connection!=null){
            connection.setAutoCommit(b);
            return;
        }
        throw new SQLException("connection in ConnectorsDB is null");
    }

    public void commit() throws SQLException{
        if (connection!=null) {
            connection.commit();
            return;
        }
        throw new SQLException("connection in ConnectorDB is null");
    }

    public void rollbackQuietly(){
        if (connection!=null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("ResultSet is null "+e);
            }
        }
    }

    public void closeQuietly(AutoCloseable resource) {
        if (resource!=null){
            try{
                resource.close();
            }catch (Exception e){
                /*NOP*/
            }
        }
    }

    public void closeQuietly(AutoCloseable... resources){
        for (AutoCloseable resource: resources)
            closeQuietly(resource);
    }


    /*public static Connection getConnection() throws SQLException{

        Properties prop = new Properties();
        String url = "";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        try {
            FileInputStream fis = new FileInputStream(FILE_PROP_PATH);
            prop.load(fis);
            url = prop.getProperty(URL);
            prop.remove(URL);
        } catch (IOException e) {
            System.err.println(e);
        }
        return DriverManager.getConnection(url, prop);
    }*/
}
