package com.electroService.services.defaultRestData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

public class DefaultRestServiceImpl<E extends DefaultEntity> implements DefaultRestService<E> {

    @Autowired
    protected JpaRepository<E, Long> dao;

    @Override
    public E create(E e) throws AlreadyExistException {
        if (e.getId() != null && dao.existsById(e.getId())) throw new AlreadyExistException();
        return dao.save(e);
    }

    @Override
    public boolean update(E e) {
        if (e.getId() != null && !dao.existsById(e.getId())) return false;
        dao.save(e);
        return true;
    }

    @Override
    public E getById(Long id) throws NotFoundException {
        if (!dao.existsById(id)) throw new NotFoundException();
        return dao.getOne(id);
    }

    @Override
    public Collection<E> getAll() {
        return dao.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (!dao.existsById(id)) return false;
        dao.deleteById(id);
        return true;
    }
}
