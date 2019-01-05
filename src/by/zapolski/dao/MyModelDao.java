package by.zapolski.dao;

import by.zapolski.exception.DaoSystemException;
import by.zapolski.exception.NoSuchEntityException;

import java.util.List;

// CRUD operations: create, read, update, delete
public interface MyModelDao<T>{
    public T selectByName (String name) throws NoSuchEntityException,DaoSystemException;//read
    public List<T> selectAll() throws DaoSystemException;
}
