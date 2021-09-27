package com.electroService.services.defaultRestData;

import java.util.Collection;

public interface DefaultRestService<T extends DefaultEntity> {
    T create(T t) throws AlreadyExistException;

    boolean update(T t);

    T getById(Long id) throws NotFoundException;

    Collection<T> getAll();

    boolean deleteById(Long id) throws NotFoundException;
}
