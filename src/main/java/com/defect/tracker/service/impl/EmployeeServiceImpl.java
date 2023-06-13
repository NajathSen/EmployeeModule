package com.defect.tracker.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.Designation;
import com.defect.tracker.entities.Employee;
import com.defect.tracker.entities.QDesignation;
import com.defect.tracker.entities.QEmployee;
import com.defect.tracker.repositories.DesignationRepository;
import com.defect.tracker.repositories.EmployeeRepository;
import com.defect.tracker.response.dto.EmployeeResponse;
import com.defect.tracker.resquest.dto.EmployeeRequest;
import com.defect.tracker.search.response.EmployeeSearch;
import com.defect.tracker.service.EmployeeService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;



@Service
public class EmployeeServiceImpl implements EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  DesignationRepository designationRepository;

  @Transactional
  public void saveEmployee(EmployeeRequest employeeRequest) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeRequest, employee);
    Designation designation = new Designation();
    designation.setId(employeeRequest.getDesignationId());
    employee.setDesignation(designation);
    employeeRepository.save(employee);
  }

  @Override
  public boolean isEmployeeExistsByEmail(String email) {
    return employeeRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public boolean isEmployeeExistsByContactNumber(String contactNumber) {
    return employeeRepository.existsByContactNumberIgnoreCase(contactNumber);
  }

  @Override
  public boolean existByEmployee(Long id) {
    return employeeRepository.existsById(id);
  }

  @Transactional
  public List<EmployeeResponse> getAllEmployee() {
    List<EmployeeResponse> employeeResponses = new ArrayList<>();
    List<Employee> employees = employeeRepository.findAll();
    for (Employee employee : employees) {
      EmployeeResponse employeeResponse = new EmployeeResponse();
      BeanUtils.copyProperties(employee, employeeResponse);
      employeeResponse.setDesignationId(employee.getDesignation().getId());
      employeeResponse.setDesignationName(employee.getDesignation().getName());
      employeeResponses.add(employeeResponse);
    }
    return employeeResponses;
  }

  @Transactional
  public EmployeeResponse getEmployeeById(Long id) {

    Employee employee = employeeRepository.findById(id).get();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    BeanUtils.copyProperties(employee, employeeResponse);
    employeeResponse.setDesignationId(employee.getDesignation().getId());
    employeeResponse.setDesignationName(employee.getDesignation().getName());

    return employeeResponse;
  }

  @Override
  public List<Employee> getAllEmployeeByDesignationId(Long id) {
    return employeeRepository.findByDesignationId(id);
  }

  @Override
  public boolean isUpdatedEmployeeEmailExist(Long id, String email) {
    return employeeRepository.existsByEmailIgnoreCaseAndIdNot(email, id);
  }

  @Override
  public boolean isUpdatedEmployeeContactNumberExist(Long id, String contactNumber) {
    return employeeRepository.existsByContactNumberIgnoreCaseAndIdNot(contactNumber, id);
  }

  @Override
  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);

  }

  @Transactional
  public List<EmployeeResponse> multiSearchEmployee(Pageable pageable, Pagination pagination,
      EmployeeSearch employeeSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QEmployee qEmployee = QEmployee.employee;


    if (Utils.isNotNullAndEmpty(employeeSearch.getFirstName())) {
      booleanBuilder.and(qEmployee.firstName.containsIgnoreCase(employeeSearch.getFirstName()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getLastName())) {
      booleanBuilder.and(qEmployee.lastName.containsIgnoreCase(employeeSearch.getLastName()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getContactNumber())) {
      booleanBuilder
          .and(qEmployee.contactNumber.containsIgnoreCase(employeeSearch.getContactNumber()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getEmail())) {
      booleanBuilder.and(qEmployee.email.containsIgnoreCase(employeeSearch.getEmail()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getDesignation())) {
      QDesignation qDesignation = QDesignation.designation;
      booleanBuilder.and(qDesignation.name.containsIgnoreCase(employeeSearch.getDesignation()));
    }


    List<EmployeeResponse> employeeResponses = new ArrayList<>();
    Page<Employee> employees = employeeRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(employees.getTotalElements());
    pagination.setTotalPages(employees.getTotalPages());
    for (Employee employee : employees.toList()) {
      EmployeeResponse employeeResponse = new EmployeeResponse();
      BeanUtils.copyProperties(employee, employeeResponse);
      employeeResponse.setDesignationId(employee.getDesignation().getId());
      employeeResponse.setDesignationName(employee.getDesignation().getName());
      employeeResponses.add(employeeResponse);
    }
    return employeeResponses;
  }

  @Override
  public void exportEmployeesToCsv(String filePath) throws IOException {
    List<Employee> employees = employeeRepository.findAll();

    try (FileWriter writer = new FileWriter(filePath)) {
      writer
          .write("ID,First Name,Last Name,Email,Contact Number,Designation,Availability,Gender\n");

      for (Employee employee : employees) {
        writer.write(employee.getId() + "," + employee.getFirstName() + "," + employee.getLastName()
            + "," + employee.getEmail() + "," + employee.getContactNumber() + ","
            + employee.getDesignation().getName() + "," + employee.getAvailability() + ","
            + employee.getGender() + "\n");
      }
    } catch (IOException e) {
      throw e;
    }

  }

  @Override
  @Transactional
  public boolean importEmployeesFromCsv(MultipartFile file) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(data[1]);
        employeeRequest.setLastName(data[2]);
        employeeRequest.setEmail(data[3]);
        employeeRequest.setContactNumber(data[4]);
        employeeRequest.setAvailability(Double.parseDouble(data[6]));
        employeeRequest.setGender(data[7]);
        employeeRequest.setDesignationId(Long.parseLong(data[5]));

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        Designation designation = new Designation();
        designation.setId(employeeRequest.getDesignationId());
        employee.setDesignation(designation);

        employeeRepository.save(employee);
      }
      return true; // Import successful
    } catch (IOException e) {
      e.printStackTrace();
      return false; // Import failed
    }
  }
}


