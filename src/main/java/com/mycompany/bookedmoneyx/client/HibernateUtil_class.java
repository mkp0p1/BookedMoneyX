package com.mycompany.bookedmoneyx.client;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Date;
import com.mycompany.bookedmoneyx.client.Operation_class;
import com.mycompany.bookedmoneyx.client.OperationsList_class;
import com.mycompany.bookedmoneyx.client.AccountList_class;
import com.mycompany.bookedmoneyx.client.Category_class;
import com.mycompany.bookedmoneyx.client.Func;

public class HibernateUtil_class {
	enum eTypeObject{
		Account,
		Category,
		Operation
	}
	
	private SessionFactory sessionFactory;

	HibernateUtil_class(){}
		
	private SessionFactory configureSessionFactory(){
		try{
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("ERROR initial SessionFactory creation failed:\t\t" + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
	}
	
	private SessionFactory configureSessionFactory(String host, int port, String db, String user, String password,
			String extensionConnString) throws HibernateException {
		String connDriverClass = "com.mysql.jdbc.Driver";
		String connURL = "jdbc:mysql://" + host + ":" + port + "/" + db + extensionConnString;
				
        Configuration configuration = new Configuration()
			.setProperty( "hibernate.connection.driver_class", connDriverClass )
			.setProperty( "hibernate.connection.url", connURL )
			.setProperty( "hibernate.connection.username", user )
			.setProperty( "hibernate.connection.password", password )
			.setProperty( "hibernate.connection.pool_size", "1" )
			.setProperty( "hibernate.connection.autocommit", "false" )
			.setProperty( "hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider" )
			.setProperty( "hibernate.cache.use_second_level_cache", "false" )
			.setProperty( "hibernate.cache.use_query_cache", "false" )
			//.setProperty( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect" )
			.setProperty( "hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect" )
			.setProperty( "hibernate.show_sql","false" )
			.setProperty( "hibernate.current_session_context_class", "thread" )
			.setProperty( "hibernate.hbm2ddl.auto", "update" )
			.addResource("Account_class.hbm.xml")
			.addResource("Category_class.hbm.xml")			
			.addResource("Operation_class.hbm.xml")
			.addResource("Data_class.hbm.xml")
			;
		try{
			sessionFactory = configuration.buildSessionFactory();
		}
		catch (Throwable ex) {
			System.err.println("ERROR HibernateUtil: initial SessionFactory creation failed:\t" + ex);
		}
        return sessionFactory;
	}
	
	private SessionFactory configureSessionFactory(String host, int port, String db, String user, String password){
		return configureSessionFactory(host, port, db, user, password, "");
	}
	
	public boolean Init(String host, int port, String db, String user, String password, String extensionConnString){		
		if(configureSessionFactory(host, port, db, user, password, extensionConnString) == null){
			System.err.println("ERROR HibernateUtil: initial is failed!");
			return false;
		}
		return true;		
	}
	
	public void Close(){
		if(sessionFactory != null){
			try{
				sessionFactory.close();
				sessionFactory = null;
			}
			catch (Throwable ex) {
				System.err.println("ERROR HibernateUtil: close SessionFactory failed:\t" + ex);
			}			
		}
	}
	
    public SessionFactory getSessionFactory() {
      return sessionFactory;
    }
	
	private void addItem(Object item, eTypeObject type){
		if(item == null)
			return;
		Session	session = this.sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		try{
			switch(type){
				case Account:
					session.save((Account_class)item);
					break;
				case Category:
					session.save((Category_class)item);
					break;
				case Operation:
					session.save((Operation_class)item);
			}
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - add(" + type + "):\t\t" + e);
		}
        finally{
			session.close();
		}
	}
	
	public void add(Account_class item){
		addItem(item, eTypeObject.Account);
	}
	
	public void add(Category_class item){
		addItem(item, eTypeObject.Category);
	}
	
	public void add(Operation_class item){
		addItem(item, eTypeObject.Operation);
	}
	
	private Object getItem(long id, eTypeObject type){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		Object item = null;
		String queryStr = "";
		switch(type){
			case Account:
				queryStr = "from Account_class where id = :id";
				break;
			case Category:
				queryStr = "from Category_class where id = :id";
				break;
			case Operation:
				queryStr = "from Operation_class where id = :id";
				break;
		}		
		try{
			Query query = session.createQuery(queryStr);
			query.setParameter("id", id);
			item = query.getSingleResult();
			transaction.commit();
		}
		catch(javax.persistence.NoResultException e){
			//item = null;
		}
		catch(Exception e){
			System.out.println("Exception - getItem(" + type + "):\t\t" + e);
		}
        finally{
			session.close();
		}
		return item;
	}
	
	public Account_class getAccount(long id){
		return (Account_class) getItem(id, eTypeObject.Account);
	}
	
	public Category_class getCategory(long id){
		return (Category_class) getItem(id, eTypeObject.Category);
	}
	
	public Operation_class getOperation(long id){
		return (Operation_class) getItem(id, eTypeObject.Operation);
	}
			
	private List getListAll(eTypeObject type) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		List list = null;
		try{
			switch(type){
				case Account:		
					list = session.createQuery("FROM Account_class").list();
					break;
				case Category:
					list = session.createQuery("FROM Category_class").list();
					break;
				case Operation:
					list = session.createQuery("FROM Operation_class").list();
					break;
			}
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - getItemsAll(" + type + "):\t\t" + e);
		}
        finally{
			session.close();
		}
        return list;
    }
	
	public List getAccountsAll() {
		return getListAll(eTypeObject.Account);
    }
	
	public List getCategoriesAll() {
		return getListAll(eTypeObject.Category);
    }
	
	public List getOperationsAll() {
        return getListAll(eTypeObject.Operation);
    }
	
	public List getOperationsByInterval(Date begin, Date end){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		List list = null;
		String queryStr = "from Operation_class where datetime BETWEEN :begin and :end";
		try{
			Query query = session.createQuery(queryStr);
			query.setParameter("begin", begin);
			query.setParameter("end", end);
			list = query.getResultList();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - getListOfOperationsByInterval():\t\t" + e);
		}
        finally{
			session.close();
		}
		return list;
	}
	
	private void updateItem(long id, Object value, eTypeObject type) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		try{
			Object item;
			switch(type){
				case Account:
					item = (Account_class) session.get(Account_class.class, id);
					if(item != null){
						((Account_class)item).set((Account_class)value);
						session.update(item);
					}
					break;
				case Category:
					item = (Category_class) session.get(Category_class.class, id);
					if(item != null){
						((Category_class)item).set((Category_class) value);
						session.update(item);
					}
					break;
				case Operation:
					item = (Operation_class) session.get(Operation_class.class, id);
					if(item != null){
						((Operation_class)item).set((Operation_class) value);
						session.update(item);
					}
					break;
			}
			transaction.commit();
		}
		catch(org.hibernate.exception.SQLGrammarException e){
			System.out.println("Exception - updateItem(" + type + "):\t\t" + e);
		}
        finally{
			session.close();
		}
    }
	
	public void updateAccount(long id, Account_class value) {
		updateItem(id, value, eTypeObject.Account);
	}
	
	public void updateCategory(long id, Category_class value) {
		updateItem(id, value, eTypeObject.Category);
	}
	
	public void updateOperation(long id, Operation_class value) {
		updateItem(id, value, eTypeObject.Operation);
	}
		
	public void updateOperation_OLD(long id, Operation_class value) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		try{
			Operation_class item = (Operation_class) session.get(Operation_class.class, id);
			if(item != null){
				item.set(value);
				session.update(item);
			}
			transaction.commit();
		}
		catch(org.hibernate.exception.SQLGrammarException e){
			System.out.println("Exception - updateOperation():\t\t" + e);
		}
        finally{
			session.close();
		}
    }
	
	private void addOrSet(Object value, eTypeObject type){
		long id = 0;
		switch(type){
			case Account:
				id = ((Account_class)value).getID();
				break;
			case Category:
				id = ((Category_class)value).getID();
				break;
			case Operation:
				id = ((Operation_class)value).getID();
				break;
		}
		if(getItem(id, type) == null)
			addItem(value, type);
		else
			updateItem(id, value, type);
	}
	
	public void addOrSet(Account_class item){
		addOrSet(item, eTypeObject.Account);
	}
	
	public void addOrSet(Category_class item){
		addOrSet(item, eTypeObject.Category);
	}
	
	public void addOrSet(Operation_class item){
		addOrSet(item, eTypeObject.Operation);
	}
	
	private void removeItem(long id, eTypeObject type) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		Object item;
		try{
			switch(type){
				case Account:
					item = (Account_class) session.get(Account_class.class, id);
					session.delete((Account_class)item);
					break;
				case Category:
					item = (Category_class) session.get(Category_class.class, id);
					session.delete((Category_class) item);
					break;
				case Operation:
					item = (Operation_class) session.get(Operation_class.class, id);
					session.delete((Operation_class) item);
					break;
			}			
			transaction.commit();
		}
		catch(java.lang.IllegalArgumentException e){
			//System.out.println("Exception2 - removeItem(" + type + ")\t\tnot find item with ID=" + id);
		}
		catch(Exception e){
			System.out.println("Exception1 - removeItem(" + type + "):\t\t" + e);
		}
		finally{
			session.close();
		}
    }
	
	public void removeAccount(long id){
		removeItem(id, eTypeObject.Account);
	}
	
	public void removeCategory(long id){
		removeItem(id, eTypeObject.Category);
	}
	
	public void removeOperation(long id){
		removeItem(id, eTypeObject.Operation);
	}
	
	private void removeItemsAll(eTypeObject type){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
		String queryStr = "";
		switch(type){
			case Account:
				queryStr = "DELETE Account_class";
				break;
			case Category:
				queryStr = "DELETE Category_class";
				break;
			case Operation:
				queryStr = "DELETE Operation_class";
				break;
		}		
		try{
			Query query = session.createQuery(queryStr);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception1 - removeItemsAll(" + type + "):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
	
	public void removeAccountsAll(){
		removeItemsAll(HibernateUtil_class.eTypeObject.Account);
	}
	
	public void removeCategoryAll(){
		removeItemsAll(HibernateUtil_class.eTypeObject.Category);
	}
	
	public void removeOperationsAll(){
		removeItemsAll(HibernateUtil_class.eTypeObject.Operation);
	}
	
	public void createTableAccounts(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "CREATE TABLE IF NOT EXISTS accounts (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(255) NULL DEFAULT NULL, "
					+ "balance DOUBLE NULL DEFAULT NULL"
					+ ")";
			Query query = session.createSQLQuery(queryStr).addEntity(Account_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - createTableAccounts):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
	
	public void createTableCategories(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "CREATE TABLE IF NOT EXISTS categories (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(255) NULL DEFAULT NULL, "
					+ "name_parent VARCHAR(255) NULL DEFAULT NULL, "
					+ "is_incoming BIT(1) NULL DEFAULT NULL"
					+ ")";
			Query query = session.createSQLQuery(queryStr).addEntity(Account_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - createTableCategories):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
	
	public void createTableOperations(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "CREATE TABLE IF NOT EXISTS operations (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "type INT(11) NULL DEFAULT NULL, "
					+ "account_incoming_id BIGINT(20) NULL DEFAULT NULL,"
					+ "account_outgoing_id BIGINT(20) NULL DEFAULT NULL,"
					+ "is_incoming BIT(1) NULL DEFAULT NULL,"
					+ "category_id BIGINT(20) NULL DEFAULT NULL,"
					+ "datetime DATETIME NULL DEFAULT NULL,"
					+ "description VARCHAR(255) NULL DEFAULT NULL,"
					+ "amount DOUBLE NULL DEFAULT NULL"
					+ ")";			
			Query query = session.createSQLQuery(queryStr).addEntity(Account_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - createTableOperations):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
			
	public void dropTableAccounts(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "DROP TABLE IF EXISTS accounts";
			Query query = session.createSQLQuery(queryStr).addEntity(Account_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - dropTableAccounts):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
	
	public void dropTableCategories(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "DROP TABLE IF EXISTS categories";
			Query query = session.createSQLQuery(queryStr).addEntity(Category_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - dropTableCategories):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
	
	public void dropTableOpearations(){
		Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String queryStr = "DROP TABLE IF EXISTS operations";
			Query query = session.createSQLQuery(queryStr).addEntity(Account_class.class);
			query.executeUpdate();
			transaction.commit();
		}
		catch(Exception e){
			System.out.println("Exception - dropTableOpearations):\t\t" + e);
		}
		finally{
			session.close();
		}
	}
}
