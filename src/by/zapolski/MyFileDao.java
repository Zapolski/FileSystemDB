package by.zapolski;

import by.zapolski.exception.NoSuchEntityException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyFileDao {
    public final String SELECT_BY_NAME_SQL = "SELECT id,parentdir,filename,lenght FROM files WHERE filename = ?";
    ConnectorDB conn = new ConnectorDB();

    public MyFile selectByName(String name) throws NoSuchEntityException{

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
            conn.commit();
            return result;
        }catch (SQLException e){
            conn.rollback();
            System.err.println(e);
        }finally {
            conn.closeStatement(stmt);
            conn.closeResultSet(rs);
        }
        return null;
    }

    public void closeMyFileDao(){
        conn.closeConnection();
    }

}
