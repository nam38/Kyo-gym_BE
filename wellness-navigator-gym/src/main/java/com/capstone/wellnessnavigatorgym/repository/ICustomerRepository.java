package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Account;
import com.capstone.wellnessnavigatorgym.entity.Customer;
import com.capstone.wellnessnavigatorgym.entity.CustomerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.sql.Date;
import java.util.Optional;

@Repository
@Transactional
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select c.customer_id, c.customer_address, c.customer_code, c.customer_email, c.customer_gender, " +
            "c.customer_img, c.customer_name, c. customer_phone, c.date_of_birth, c.id_card, c.is_enable, " +
            "ct.customer_type_id, a.account_id" +
            "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "left join account a on c.account_id = a.account_id " +
            "where c.is_enable = true " +
            "order by c.customer_code ",
            countQuery = "select count(c.customer_id) " +
                    "from customer c " +
                    "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
                    "left join account a on c.account_id = a.account_id where c.is_enable = true order by c.customer_code ",
            nativeQuery = true)
    Page<Customer> findAllCustomers(Pageable pageable);


    @Query(value = "select c.customer_id, c.customer_address, c.customer_code, c.customer_email, c.customer_gender, " +
            "c.customer_img, c.customer_name, c.customer_phone, c.date_of_birth, c.id_card, c.is_enable, " +
            "ct.customer_type_id, ct.customer_type_name, a.account_id " +
            "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "left join account a on c.account_id = a.account_id " +
            "where (ct.customer_type_name like concat('%', :type, '%') and c.customer_name like concat('%', :name, '%') " +
            "and c.customer_address like concat('%', :address, '%')  and c.customer_phone like concat('%', :phone, '%')) " +
            "and (c.is_enable = true) order by c.customer_code ",
            countQuery = "select count(c.customer_id) " +
                    "from customer c " +
                    "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
                    "left join account a on c.account_id = a.account_id " +
                    "where (ct.customer_type_name like concat('%', :type, '%') and c.customer_name like concat('%', :name, '%') " +
                    "and c.customer_address like concat('%', :address, '%')  and c.customer_phone like concat('%', :phone, '%')) " +
                    "and (c.is_enable = true) order by c.customer_code",
            nativeQuery = true)
    Page<Customer> searchCustomer(@Param("type") String type, @Param("name") String name,
                                  @Param("address") String address , @Param("phone") String phone , Pageable pageable);

    @Query(value = "select * from customer order by `customer_code` desc limit 1", nativeQuery = true)
    Customer limitCustomer();

    @Modifying
    @Query(value = "INSERT INTO customer (`customer_code`, `customer_name`, `customer_email`, `customer_phone`, " +
            "`customer_gender`, `date_of_birth`, `id_card`, `customer_address`, `customer_img`, `is_enable`, `customer_type_id`) " +
            "VALUES (:customer_code, :customer_name, :customer_email, :customer_phone, :customer_gender, " +
            ":date_of_birth, :id_card, :customer_address, :customer_img, :is_enable, :customer_type_id)", nativeQuery = true)
    void insertCustomer(@Param("customer_code") String customer_code,
                        @Param("customer_name") String customer_name,
                        @Param("customer_email") String customer_email,
                        @Param("customer_phone") String customer_phone,
                        @Param("customer_gender") Boolean customer_gender,
                        @Param("date_of_birth") Date date_of_birth,
                        @Param("id_card") String id_card,
                        @Param("customer_address") String customer_address,
                        @Param("customer_img") String customer_img,
                        @Param("is_enable") Boolean is_enable,
                        @Param("customer_type_id") CustomerType customer_type_id);

    @Modifying
    @Query(value = "UPDATE customer SET `customer_code`=:customer_code, `customer_name`=:customer_name, " +
            "`customer_email`=:customer_email, `customer_phone`=:customer_phone, `customer_gender`=:customer_gender, " +
            "`date_of_birth`=:date_of_birth, `id_card`=:id_card, `customer_address`=:customer_address, " +
            "`customer_img`=:customer_img, `is_enable`=:is_enable, `customer_type_id`=:customer_type_id, " +
            "`account_id`=:account_id WHERE `customer_id`=:customer_id", nativeQuery = true)
    void updateCustomer(@Param("customer_id") Integer customer_id,
                        @Param("customer_code") String customer_code,
                        @Param("customer_name") String customer_name,
                        @Param("customer_email") String customer_email,
                        @Param("customer_phone") String customer_phone,
                        @Param("customer_gender") Boolean customer_gender,
                        @Param("date_of_birth") Date date_of_birth,
                        @Param("id_card") String id_card,
                        @Param("customer_address") String customer_address,
                        @Param("customer_img") String customer_img,
                        @Param("is_enable") Boolean is_enable,
                        @Param("customer_type_id") CustomerType customer_type_id,
                        @Param("account_id") Account account_id);

    @Modifying
    @Query(value = "update customer set is_enable = false where customer_id = :id", nativeQuery = true)
    void deleteCustomerId(@Param("id") Integer id);

    @Query(value = "select c.customer_id, c.customer_code, c.customer_name, c.customer_phone, c.customer_gender, " +
            "c.date_of_birth, c.id_card, c.customer_address, c.customer_img, ct.customer_type_name, a.user_name, a.email " +
            "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "inner join account a on c.account_id = a.account_id " +
            "where (c.is_enable = true) and (a.is_enable = true) and (a.user_name = :username) ", nativeQuery = true)
    Optional<Tuple> findUserDetailByUsername(@Param("username") String username);
}
