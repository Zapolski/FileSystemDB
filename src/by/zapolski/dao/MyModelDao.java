package by.zapolski.dao;

import by.zapolski.exception.DaoSystemException;
import by.zapolski.exception.NoSuchEntityException;
import by.zapolski.model.MyFile;

import java.util.List;

// CRUD operations: create, read, update, delete
public interface MyModelDao<T>{
    public T readByName (String name) throws NoSuchEntityException,DaoSystemException;  //read
    public boolean createByName(T item) throws DaoSystemException;                      //create
    public List<T> selectAll() throws DaoSystemException;

    void closeMyFileDao();
}
