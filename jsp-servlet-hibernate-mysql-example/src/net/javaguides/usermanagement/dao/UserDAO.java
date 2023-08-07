package net.javaguides.usermanagement.dao;

import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;

import net.javaguides.usermanagement.User;
import net.javaguides.usermanagement.util.HibernateUtil;

public class UserDAO {
	
	//Save a user
	public void saveUser(User user) throws IllegalStateException, SystemException {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//start transaction
			transaction = (Transaction) session.beginTransaction();
			//save the student object
			session.save(user);
			//commit transaction
			transaction.commit();
		
		
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}
	
	//update a user
	public void updateUser(User user) throws IllegalStateException, SystemException {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//start transaction
			transaction = (Transaction) session.beginTransaction();
			//update the student object
			session.update(user);
			//commit transaction
			transaction.commit();
		
		
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	
	//delete a user
	public void deleteUser(int id) throws IllegalStateException, SystemException {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//start transaction
			transaction = (Transaction) session.beginTransaction();
			
			User user = session.get(User.class, id);
			//delete the student object
			
			if (user != null) {
				session.delete(user);
				System.out.println("User is deleted");
			}
			
			//commit transaction
			transaction.commit();
		
		
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}
	
	
	//get a user	
	public User getUser(int id) throws IllegalStateException, SystemException {
		Transaction transaction = null;
		User user = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//start a transaction
			transaction = (Transaction) session.beginTransaction();
			
			//get an user object
			user = session.get(User.class, id);
			
			//commit tansaction
			transaction.commit();
		}catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
		return user;
	}
	
	// get all users
	@SuppressWarnings("unchecked")
	public List < User > getAllUser() throws IllegalStateException, SystemException {
		Transaction transaction = null;
		List< User > ListOfUser = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//start a transaction
			transaction = (Transaction) session.beginTransaction();
			
			//get an user object
			ListOfUser = session.createQuery("from User").getResultList();
			
			//commit transaction
			transaction.commit();
		}catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
		return ListOfUser;
		
	}
	
}
