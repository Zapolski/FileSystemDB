package by.zapolski.dao;

import by.zapolski.ConnectorDB;
import by.zapolski.dao.MyModelDao;
import by.zapolski.exception.DaoSystemException;
import by.zapolski.exception.NoSuchEntityException;
import by.zapolski.model.MyFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MyFileDaoImpl implements MyModelDao<MyFile> {

    private final String SELECT_BY_NAME_SQL = "SELECT id,parentdir,filename,lenght FROM files WHERE filename = ?";
    private final String INSERT_BY_NAME_SQL = "INSERT INTO files (parentdir,filename,lenght) VALUES (?,?,?)";
    private final String UPDATE_BY_NAME_SQL = "UPDATE files SET parentdir=?,filename=?,lenght=? where filename = ?;";
    private final String DELETE_BY_NAME_SQL = "DELETE FROM files WHERE filename = ?;";

    ConnectorDB conn = new ConnectorDB();



    public MyFile readByName(String name) throws DaoSystemException,NoSuchEntityException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            //conn.setAutoCommit(false);
            stmt = conn.getPreparedStatement(SELECT_BY_NAME_SQL);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if (!rs.next()){
                throw new NoSuchEntityException("No MyFile for name="+name);
            }
            MyFile result = new MyFile(rs.getInt("id"),rs.getString("parentdir"),
                    rs.getString("filename"),rs.getLong("lenght"));
            //и коммит и ролбэк освобождают ресурсы в БД
            //conn.commit();//если мы только читаем, то можно вызывать rollback, т.к. она работает быстрее
            return result;
        }catch (SQLException e){
            conn.rollbackQuietly();
            throw new DaoSystemException("Some exception",e);
        }finally {
            //надо все закрывать, т.к. многие спеку нарушают
            conn.closeQuietly(stmt,rs);
            //conn.closeStatement(stmt);
            //conn.closeResultSet(rs);
        }
    }

    public boolean createByName(MyFile newMyFile) throws DaoSystemException{
        boolean flag = false;
        PreparedStatement stmt = null;
        try{
            stmt = conn.getPreparedStatement(INSERT_BY_NAME_SQL);

            stmt.setString(1,newMyFile.getParentDir());
            stmt.setString(2,newMyFile.getName());
            stmt.setLong(3,newMyFile.getLenght());

            stmt.executeUpdate();
            flag=true;
        }catch (SQLException e){
            conn.rollbackQuietly();
            throw new DaoSystemException("Some exception",e);
        }finally {
            conn.closeQuietly(stmt);
        }
        return flag;
    }

    public boolean updateByName(String name, MyFile myFile) throws DaoSystemException{
        boolean flag = false;
        PreparedStatement stmt = null;
        try{
            stmt = conn.getPreparedStatement(UPDATE_BY_NAME_SQL);

            stmt.setString(1,myFile.getParentDir());
            stmt.setString(2,myFile.getName());
            stmt.setLong(3,myFile.getLenght());
            stmt.setString(4,name);

            stmt.executeUpdate();
            flag=true;
        }catch (SQLException e){
            conn.rollbackQuietly();
            throw new DaoSystemException("Some exception",e);
        }finally {
            conn.closeQuietly(stmt);
        }
        return flag;
    }


    public boolean deleteByName(String name) throws DaoSystemException{
        boolean flag = false;
        PreparedStatement stmt = null;
        try{
            stmt = conn.getPreparedStatement(DELETE_BY_NAME_SQL);
            stmt.setString(1,name);
            stmt.executeUpdate();
            flag=true;
        }catch (SQLException e){
            conn.rollbackQuietly();
            throw new DaoSystemException("Some exception",e);
        }finally {
            conn.closeQuietly(stmt);
        }
        return flag;
    }



    @Override
    public List<MyFile> selectAll() throws DaoSystemException {
        return null;
    }

    public void closeMyFileDao(){
        conn.closeConnection();
    }

}
