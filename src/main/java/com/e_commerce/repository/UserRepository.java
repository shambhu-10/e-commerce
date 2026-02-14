package com.e_commerce.repository;

import com.e_commerce.entity.User;
import com.e_commerce.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // find user by email
    // used for login and duplicate email check
    Optional<User> findByEmail(String email);

    // check if email already registered
    // more efficient than findByEmail when you only need boolean
    boolean existsByEmail(String email);

    // find all user by role
    List<User> findByRole(UserRole role);

    // find active user only
    List<User> findByActiveTrue();

    // find user by email and active status
    // useful for login & don't allow inactive user for login
    Optional<User> findByEmailAndActiveTrue(String email);

    // for update and delete use @Modifying
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
    int updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);

    // find users with their profile loaded (avoid N+1)
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profile")
    List<User> findAllWithProfiles();

    // search user by name or email
    @Query("SELECT u FROM User u WHERE" +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<User> searchUser(@Param("keyword") String keyword);

}
