package com.example.lab3.dao;

import com.example.lab3.entity.Vladelys;

import com.example.lab3.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VladelysDaoImpl implements VladelysDao {

  @Override
  public void save(Vladelys vladel) {
    Transaction tx = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      session.save(vladel);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }


  @Override
  public List<Vladelys> findAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from Vladelys order by id", Vladelys.class).list();
    }
  }

  @Override
  public List<Vladelys> getAll() {

    Session session =  HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = session.beginTransaction();

    List<Vladelys> result = session.createQuery("SELECT DISTINCT v FROM Vladelys v LEFT JOIN FETCH v.tehniki", Vladelys.class)
            .getResultList();

    transaction.commit();
    session.close();
    return result;
  }


  @Override
  public Vladelys findID(long id) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery(
                      "SELECT DISTINCT v FROM Vladelys v " +
                              "LEFT JOIN FETCH v.tehniki t " +
                              "LEFT JOIN FETCH t.vladeltsy " +
                              "WHERE v.id = :id", Vladelys.class)
              .setParameter("id", id)
              .uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void delete(long id) {
    Transaction tx = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Vladelys vladel = session.get(Vladelys.class, id);
      if (vladel != null) {
        session.delete(vladel);
      }
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }
}