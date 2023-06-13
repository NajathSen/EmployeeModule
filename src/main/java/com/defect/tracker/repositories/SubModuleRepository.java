package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.SubModule;

public interface SubModuleRepository extends JpaRepository<SubModule, Long> {
}
