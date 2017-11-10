package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Employee;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Handles all business logic related to employee accounts.
 */
public class EmployeeService {
    
    private final EntityManager entityManager;
    
    public enum AuthenticationResult {
        SUCCESS,
        FAILURE,
        PASSWORD_EXPIRED
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
     Employee employee = entityManager.getReference(Employee.class, employeeId);
        return employee;
    }
    
    /**
     * @return A list of all employee accounts in the system.
     */
    public List<Employee> getAllEmployee(){
    String queryString = 
            "FROM Employee WHERE employeeId is not NULL"; 
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
     * @param employee The employee to authenticate.
     * 
     * @param plaintextPassword The plaintext password of the employee.
     * 
     * @return The appropriate authenticationResult enum type.
     */
    public AuthenticationResult authenticate(Employee employee, 
            String plaintextPassword) {
        String hashed = employee.getHashedPassword();
        boolean passwordCorrect = BCrypt.checkpw(plaintextPassword, hashed);
        boolean passwordExpired = isPasswordExpired(employee);
        
        if(passwordCorrect) {
            if(passwordExpired) {
                return AuthenticationResult.PASSWORD_EXPIRED;
            } else {
                return AuthenticationResult.SUCCESS;
            }
        } else {
            return AuthenticationResult.FAILURE;
        }
    }
    
    /**
     * Determines whether the given employee's password is considered expired,
     * per the defined rules set in the requirements document.
     * 
     * Password change is supposed to be required every 90 days
     * 
     * @param employee The employee to check.
     * 
     * @return Whether the password is considered expired.
     */
    public boolean isPasswordExpired(Employee employee) {        
        Instant temp = employee.getLastPasswordChangeDate().toInstant();
        ZonedDateTime zdt = temp.atZone(ZoneId.systemDefault());
        LocalDateTime lastChanged = zdt.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        long daysSinceChange = lastChanged.until(now, ChronoUnit.DAYS);        
        return (daysSinceChange > 90);
    }
    
    public void resetEmployeePassword(Long managerId, String managerPassword,
            Long employeeId,String employeePassword){
        // TODO
    }
}
