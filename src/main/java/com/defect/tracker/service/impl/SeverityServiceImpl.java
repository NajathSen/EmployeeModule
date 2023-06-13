package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.defect.tracker.entities.Severity;
import com.defect.tracker.repositories.SeverityRepository;
import com.defect.tracker.response.dto.SeverityResponse;
import com.defect.tracker.resquest.dto.SeverityRequest;
import com.defect.tracker.service.SeverityService;

@Service
public class SeverityServiceImpl implements SeverityService {
  @Autowired
  private SeverityRepository severityRepository;

  @Transactional
  public void saveSeverity(SeverityRequest severityRequest) {
    Severity severity = new Severity();
    BeanUtils.copyProperties(severityRequest, severity);
    severityRepository.save(severity);
  }

  @Transactional
  public List<SeverityResponse> getAllSeverities() {
    List<SeverityResponse> severityResponses = new ArrayList<>();
    List<Severity> severitys = severityRepository.findAll();
    for (Severity severity : severitys) {
      SeverityResponse severityResponse = new SeverityResponse();
      BeanUtils.copyProperties(severity, severityResponse);
      severityResponses.add(severityResponse);
    }
    return severityResponses;
  }

  @Override
  public boolean isSeverityExistsByName(String name) {
    return severityRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public SeverityResponse getSeverityById(Long id) {
    Severity severity = severityRepository.findById(id).get();
    SeverityResponse severityResponse = new SeverityResponse();
    BeanUtils.copyProperties(severity, severityResponse);
    return severityResponse;
  }

  @Override
  public boolean existBySeverity(Long id) {
    return severityRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedSeverityNameExist(Long id, String name) {
    return severityRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteSeverity(Long id) {
    severityRepository.deleteById(id);
  }

  @Override
  public boolean isSeverityExists(String color) {
    return severityRepository.existsByColorIgnoreCase(color);
  }

  @Override
  public boolean isUpdatedSeverityColorExist(Long id, String color) {
    return false;
  }
}
