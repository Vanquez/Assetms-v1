package com.kindsonthegenius.fleetmsv2.hr.repositories;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query(value = "select e from Employee e where " +
			"e.firstname LIKE %?1% or e.lastname like %?1% or e.socialSecurityNumber like %?1%")
	List<Employee> findByKeyword(@Param("keyword") String keyword);

	@Query(value = "SELECT city, count(*) FROM Employee GROUP BY city",
			nativeQuery = true)
	List<Object> getCountByCountry();

	@Query(value = "SELECT COUNT(*) AS total_employees FROM Employee",
	 nativeQuery = true)
	List<Integer> getNumberEmployee();

	Employee findByUsername(String un);

	//Finding existing
	Employee findBySocialSecurityNumber(String socialSecurityNumber);
}
