package tech.hariprasath.batchdemo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import tech.hariprasath.batchdemo.entity.Customer;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT a FROM Customer a " +
            "WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :name, '%'))" +
            "OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> getCustomerByPartialName(String name);

    @Query("SELECT a FROM Customer a " +
            "WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :name, '%'))" +
            "OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> getCustomerByPartialName(String name, Pageable pageable);
}