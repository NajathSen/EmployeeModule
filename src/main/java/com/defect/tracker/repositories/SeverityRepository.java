package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.Severity;

public interface SeverityRepository extends JpaRepository<Severity, Long> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

  boolean existsByColorIgnoreCase(String color);

  boolean existsByColorIgnoreCaseAndIdNot(String color, Long id);
}
