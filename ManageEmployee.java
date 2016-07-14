package employee;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 
 

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {

	private static SessionFactory factory;
	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().
							configure().
							// addPackage("com.xyz") //add package if used.
							addAnnotatedClass(Employee.class).
							buildSessionFactory();
		} catch (Exception ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployee ME = new ManageEmployee();
		
		/* Add few employee records in database */
		Integer empID1 = ME.addEmployee("Ivan", "Ivanov", 3000);
		Integer empID2 = ME.addEmployee("Petr", "Petrov", 2000);
		Integer empID3 = ME.addEmployee("Sidr", "Sidorov", 1000);
		
		/* List down all the employees */
		ME.listEmployees();
		
		/* Update employee's records */
		ME.updateEmployees(empID3, 5000);
		
		/* Delete an employee from the database */
		ME.deleteEmployee(empID2);
				
		/* List down new list of the employees */
		ME.listEmployees();				
	}
/* Method to CREATE an employee in the database */
	
public Integer addEmployee(String fname, String lname, int salary) {
	Session session = factory.openSession();
	Transaction tx = null;
	Integer employeeID = null;
	try {
		tx = session.beginTransaction();
		Employee employee = new Employee();
		employee.setFirstName(fname);
		employee.setLastName(lname);
		employee.setSalary(salary);
		employeeID = (Integer) session.save(employee);
		tx.commit();
	} catch (HibernateException e) {  // whats HibernateException ? 
		if (tx!=null) tx.rollback();
		e.printStackTrace();
	} finally {
		session.close();
	}
	return employeeID;	
}

//Method to READ all the employees

public void listEmployees ( ) {
	Session session = factory.openSession();
	Transaction tx = null;
	try {
		tx = session.beginTransaction();
		List employees = session.createQuery("FROM File").list(); // Whats this ?
		for ( Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
			Employee employee = (Employee) iterator.next();
			System.out.println("First Name: " + employee.getFirstName());
			System.out.println(" Last Name: " + employee.getLastName());
			System.out.println(" Salary: " + employee.getSalary());			
		}
		tx.commit();
	} catch (HibernateException e) {
		if (tx!=null) tx.rollback(); // А что если tx=null
		e.printStackTrace();
	}finally {
		session.close();
	}	
}

//Method to UPDATE salary for an employee

public void updateEmployees (Integer EmployeeID, int Salary) {
	Session session = factory.openSession();
	Transaction tx = null;
	try {
		tx = session.beginTransaction();
		Employee employee = 
				(Employee)session.get(Employee.class, EmployeeID); // Выбор в классе определенного сотрудника
		employee.setSalary(Salary);
			session.update(employee);
		tx.commit();										
	} catch (HibernateException e) {
		if (tx!=null) tx.rollback(); 
		e.printStackTrace();
	} finally {
		session.close();
	}
}

//Method to DELETE an employee from the records

public void deleteEmployee (Integer EmployeeID) {
	Session session = factory.openSession();
	Transaction tx = null; // В рамках одной сессии могут быть несколько транзакций
	try {
		tx = session.beginTransaction();
		Employee employee = 
				(Employee) session.get(Employee.class, EmployeeID);
		session.delete(employee);
		tx.commit();		
	} catch (HibernateException e) {
		if (tx!=null) tx.rollback();
		e.printStackTrace();		
	} finally {
		session.close();
	}				
}
}
