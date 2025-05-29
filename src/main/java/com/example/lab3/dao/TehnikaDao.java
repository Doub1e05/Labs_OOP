package com.example.lab3.dao;

import com.example.lab3.entity.Tehnika;
import java.util.List;

public interface TehnikaDao {
    void save(Tehnika tehnika);
    List<Tehnika> findAll();
    void update(Tehnika t);
    void delete(long id);
    void linkOwner(long tehnikaId, long ownerId);
}