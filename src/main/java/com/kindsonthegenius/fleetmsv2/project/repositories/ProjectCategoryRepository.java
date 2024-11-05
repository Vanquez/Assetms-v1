package com.kindsonthegenius.fleetmsv2.project.repositories;

import com.kindsonthegenius.fleetmsv2.project.models.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Integer> {
}
