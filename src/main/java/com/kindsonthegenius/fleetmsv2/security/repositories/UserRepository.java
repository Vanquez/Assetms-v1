package com.kindsonthegenius.fleetmsv2.security.repositories;

import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select e from User e where " +
            "e.firstname LIKE %?1% or e.lastname like %?1% ")
    List<User> findByKeyword(@Param("keyword") String keyword);

    User findByUsername(String username);

}
