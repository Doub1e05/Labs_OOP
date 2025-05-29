package com.example.lab3.dao;

import com.example.lab3.entity.Tehnika;
import com.example.lab3.entity.Vladelys;
import com.example.lab3.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TehnikaDaoImpl implements TehnikaDao {



    @Override
    public void save(Tehnika tehnika) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(tehnika);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tehnika t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        }
    }


    @Override
    public List<Tehnika> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tehnika order by id", Tehnika.class).list();
        }
    }

    @Override
    public void delete(long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Tehnika tehnika = session.get(Tehnika.class, id);
            if (tehnika != null) {
                session.delete(tehnika);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void linkOwner(long tehnikaId, long ownerId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Tehnika t = session.get(Tehnika.class, tehnikaId);
            Vladelys v = session.get(Vladelys.class, ownerId);

            if (t != null && v != null) {
                t.getVladeltsy().add(v);
                session.merge(t);  // только одна сторона
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

}