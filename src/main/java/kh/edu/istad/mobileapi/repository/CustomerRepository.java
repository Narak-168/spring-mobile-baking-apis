package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Derived Query Method
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phoneNumber);
    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByPhoneAndIsDeletedFalse(String phone);

    //JPQL
    @Modifying
    @Query(value = """
    UPDATE Customer c SET c.isDeleted = TRUE 
    WHERE c.phone = :phone
""")
    void disableByPhone(String phone);


    @Query(value = """
    SELECT EXISTS (SELECT c.id 
            FROM Customer c 
                    WHERE c.phone = ?1)
""")
    boolean isExistsByPhone(String phone);
}
