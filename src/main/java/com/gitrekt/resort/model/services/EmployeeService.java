package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Handles all business logic related to employee accounts.
 */
public class EmployeeService {
    
    private final EntityManager entityManager;
    
    /**
     * This is an enum to allow for future possible values such as handling
     * expired passwords, etc.
     */
    public enum AuthenticationResult {
        SUCCESS,
        FAILURE
    }
    
    /**
     * Creates an instance of the service class, along with it's associated 
     * Hibernate entityManager.
     */
    public EmployeeService(){
        this.entityManager = HibernateUtil.getEntityManager();
    }
    
    /**
     * Takes care of closing the Hibernate entityManager for the class.
     * 
     * @throws Throwable
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }
    
    /**
     * @param employeeId The id of the employee to retrieve.
     * 
     * @return The employee object, if found. If not found, accessing the object
     * will throw a EntityNotFoundException due to the lazy-loading behavior of
     * Hibernate.
     */
    public Employee getEmployeeById(Long employeeId){
        Employee employee = entityManager.getReference(
            Employee.class, employeeId
        );
        return employee;
    }
    
    /**
     * @return A list of all employee accounts in the system.
     */
    public List<Employee> getAllEmployees(){
        String queryString = 
            "FROM Employee"; 
        Query q = entityManager.createQuery(queryString);        
        List<Employee> results = q.getResultList();
        return results;
    }
    
    public void createEmployeeAccount(Employee employee){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }        
    }
    
    /**
     * Removes the provided employee from the database.
     * 
     * This operation cannot be undone, so be sure this is what you want to do.
     * 
     * @param employee The employee to delete.
     */
    public void deleteEmployee(Employee employee){
         try {
            entityManager.getTransaction().begin();
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }
    
    /**
     * Updates the record of the provided employee in the database.
     * 
     * @param employee The employee to update. 
     */
    public void updateEmployee(Employee employee){
         try {
            entityManager.getTransaction().begin();
            entityManager.merge(employee);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }
    
    /**
     * Performs authentication on the provided employee.
     * 
     * @param employeeId The id number of the employee to authenticate.
     * 
     * @param plaintextPassword The plaintext password of the employee.
     * 
     * @return The appropriate authenticationResult enum type.
     */
    public AuthenticationResult authenticate(Long employeeId, 
            String plaintextPassword) {
        Employee employee = getEmployeeById(employeeId);
        String hashed; // The encrypted (hashed) password of the employee.
        try {
            hashed = employee.getHashedPassword();
        } catch (EntityNotFoundException e) {
            return AuthenticationResult.FAILURE;
        }
        
        boolean passwordCorrect = BCrypt.checkpw(plaintextPassword, hashed);
        
        if(passwordCorrect) {
            return AuthenticationResult.SUCCESS;
        } else {
            return AuthenticationResult.FAILURE;
        }
    }
    
    public void resetEmployeePassword(Long managerId, String managerPassword,
            Long employeeId,String employeePassword){
        // TODO
    }
}
