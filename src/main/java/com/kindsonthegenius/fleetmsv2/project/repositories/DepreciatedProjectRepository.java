package com.kindsonthegenius.fleetmsv2.project.repositories;

import com.kindsonthegenius.fleetmsv2.project.models.DepreciatedProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepreciatedProjectRepository extends JpaRepository<DepreciatedProject,Long> {
}
