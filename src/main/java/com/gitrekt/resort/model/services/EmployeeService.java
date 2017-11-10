package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class EmployeeService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
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
    
    public Employee getEmployeeById(Long employeeId){
     Employee employee = entityManager.getReference(Employee.class, employeeId);
        return employee;
    }
    
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
    
    public Boolean authenticate(String username, String password){
         String queryString = 
            "FROM Employee WHERE userName = :username AND "
                 + "hashedPassword = :passwrd"; 
        Query q = entityManager.createQuery(queryString); 
        q.setParameter("username", username);
        q.setParameter("password", password);
        List<Employee> results = q.getResultList();
        Boolean isAuthenticated = !results.isEmpty();
        return isAuthenticated;
    }
    
    public void resetEmployeePassword(Long managerId, String managerPassword,
            Long employeeId,String employeePassword){
        String queryString = 
            "FROM Employee WHERE employeeId = :managerID AND "
                 + "hashedPassword = :managerPassword"; 
        Query q = entityManager.createQuery(queryString); 
        q.setParameter("managerId", managerId);
        q.setParameter("managerPassword", managerPassword);
        List<Employee> results = q.getResultList();
        if(!results.isEmpty()){
            queryString = "FROM Employee WHERE employeeId = :employeeId";
            q = entityManager.createQuery(queryString);
            q.setParameter("employeeId", employeeId);
            results = q.getResultList();
            if(!results.isEmpty()){
                results.get(0).setHashedPassuord(employeePassword);
                this.updateEmployee(results.get(0));
            }
        }
    }
}
