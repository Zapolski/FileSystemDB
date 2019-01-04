package by.zapolski.model;

import by.zapolski.ConnectorDB;
import by.zapolski.dao.MyModelDao;
import by.zapolski.exception.DaoSystemException;
import by.zapolski.exception.NoSuchEntityException;
import by.zapolski.model.MyFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MyFileDao implements MyModelDao<MyFile> {

    public final String SELECT_BY_NAME_SQL = "SELECT id,parentdir,filename,lenght FROM files WHERE filename = ?";
    ConnectorDB conn = new ConnectorDB();



    public MyFile selectByName(String name) throws DaoSystemException,NoSuchEntityException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn.setAutoCommit(false);
            stmt = conn.getPreparedStatement(SELECT_BY_NAME_SQL);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if (!rs.next()){
                throw new NoSuchEntityException("No MyFile for name="+name);
            }
            MyFile result = new MyFile(rs.getInt("id"),rs.getString("parentdir"),
                    rs.getString("filename"),rs.getLong("lenght"));
            //и коммит и ролбэк освобождают ресурсы в БД
            conn.commit();//если мы только читаем, то можно вызывать rollback, т.к. она работает быстрее
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

    @Override
    public List<MyFile> selectAll() throws DaoSystemException {
        return null;
    }

    public void closeMyFileDao(){
        conn.closeConnection();
    }

}
