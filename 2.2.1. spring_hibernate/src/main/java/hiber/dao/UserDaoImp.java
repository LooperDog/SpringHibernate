package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   @SuppressWarnings("unchecked")
   public void deleteAllUsers() {
      List<User> users = listUsers();
      for (User user : users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }
   @Transactional(readOnly = true)
   @Override
   public User findUserByCar(String model, int series) {
         String HQL = "from User where car.model = :model and car.series = :series";
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(HQL, User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.getSingleResult();
   }
}
