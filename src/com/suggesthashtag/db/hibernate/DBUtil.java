/**
 * 
 */
package com.suggesthashtag.db.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.suggesthashtag.db.hibernate.domain.DBHibernateObject;

/**
 * @author sumitpoddar
 *
 */
public class DBUtil {

	public static boolean insertObject(DBHibernateObject hibernateObject) {
		return performCrudOperation(hibernateObject, DBCrudOperations.I);
	}

	public static boolean insertObject(String name,
			DBHibernateObject hibernateObject) {
		return performCrudOperation(name, hibernateObject, DBCrudOperations.I);
	}

	public static boolean updateObject(DBHibernateObject hibernateObject) {
		return performCrudOperation(hibernateObject, DBCrudOperations.U);
	}

	public static boolean updateObject(String name,
			DBHibernateObject hibernateObject) {
		return performCrudOperation(name, hibernateObject, DBCrudOperations.U);
	}

	public static boolean insertOrUpdateObject(DBHibernateObject hibernateObject) {
		return performCrudOperation(hibernateObject, DBCrudOperations.IOrU);
	}

	public static boolean insertOrUpdateObject(String name,
			DBHibernateObject hibernateObject) {
		return performCrudOperation(name, hibernateObject,
				DBCrudOperations.IOrU);
	}

	public static boolean deleteObject(DBHibernateObject hibernateObject) {
		return performCrudOperation(hibernateObject, DBCrudOperations.D);
	}

	public static boolean deleteObject(String name,
			DBHibernateObject hibernateObject) {
		return performCrudOperation(name, hibernateObject, DBCrudOperations.D);
	}

	private static boolean performCrudOperation(
			DBHibernateObject hibernateObject, DBCrudOperations operations) {
		boolean isSuccess = false;
		if (hibernateObject != null) {
			Session session = DBConnectionInit.getOpenSesssion();
			try {
				session.beginTransaction();
				operations.getOperationObject().process(session,
						hibernateObject);
				session.getTransaction().commit();
				isSuccess = true;
			} catch (HibernateException e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}
		return isSuccess;
	}

	private static boolean performCrudOperation(String objectName,
			DBHibernateObject hibernateObject, DBCrudOperations operations) {
		boolean isSuccess = false;
		if (hibernateObject != null) {
			Session session = DBConnectionInit.getOpenSesssion();
			try {
				session.beginTransaction();
				operations.getOperationObject().process(session, objectName,
						hibernateObject);
				session.getTransaction().commit();
				isSuccess = true;
			} catch (HibernateException e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}
		return isSuccess;
	}

	public static Session getPersistentDBSession() {
		return DBConnectionInit.getOpenSesssion();
	}
}
