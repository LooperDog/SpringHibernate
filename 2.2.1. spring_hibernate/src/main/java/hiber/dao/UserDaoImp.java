package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;
    @Autowired
    private LocalSessionFactoryBean getSessionFactory;

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

   public User findUserByCar(String model, int series) {
      User user=null;
      try {
         Session session = sessionFactory.getSessionFactory().openSession();
         String HQL = "from User where car.model = :model and car.series = :series";
         TypedQuery<User> query = session.createQuery(HQL, User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         user = query.getSingleResult();
      }catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }
}
