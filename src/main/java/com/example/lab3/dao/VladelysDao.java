package com.example.lab3.dao;

import com.example.lab3.entity.Vladelys;
import java.util.List;

public interface VladelysDao {
    List<Vladelys> getAll();
    void save(Vladelys v);
    List<Vladelys> findAll();
    void delete(long id);
    Vladelys findID(long id);
}