package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile entity = session.get(Smile.class, id);
            return entity;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get " + Smile.class + " by id " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        String query = "FROM Smile";
        try (Session session = factory.openSession()) {
            Query getAllQuery = session.createQuery(query, Smile.class);
            return getAllQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all smiles.", e);
        }
    }
}
