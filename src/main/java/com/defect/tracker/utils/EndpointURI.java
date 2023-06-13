package com.defect.tracker.utils;

public class EndpointURI {
  private static final String BASE_API_PATH = "/api/v1/";
  private static final String ID = "/{id}";

  // URLs for Designation
  public static final String DESIGNATION = BASE_API_PATH + "designation";
  public static final String DESIGNATION_BY_ID = DESIGNATION + ID;
  public static final String DESIGNATION_SEARCH = DESIGNATION + "/search";

  // URLs for Severity
  public static final String SEVERITY = BASE_API_PATH + "severity";
  public static final String SEVERITY_BY_ID = SEVERITY + ID;

  // URLs for DEfectType
  public static final String DEFECTTYPE = BASE_API_PATH + "defectType";
  public static final String DEFECTTYPE_BY_ID = DEFECTTYPE + ID;

  // URLs for DefectStatus
  public static final String DEFECTSTATUS = BASE_API_PATH + "defectStatus";
  public static final String DEFECTSTATUS_BY_ID = DEFECTSTATUS + ID;

  // URLs for Module
  public static final String MODULE = BASE_API_PATH + "module";
  public static final String MODULE_BY_ID = MODULE + ID;

  // URLs for Release
  public static final String RELEASE = BASE_API_PATH + "release";
  public static final String RELEASE_BY_ID = RELEASE + ID;
  public static final String RELEASE_SEARCH = RELEASE + "/search";

  // URLs for Role
  public static final String ROLE = BASE_API_PATH + "role";
  public static final String ROLE_BY_ID = ROLE + ID;
  public static final String ROLE_SEARCH = ROLE + "/search";

  // URLs for Project_type
  public static final String PROJECT_TYPE = BASE_API_PATH + "projectType";
  public static final String PROJECT_TYPE_BY_ID = PROJECT_TYPE + ID;
  public static final String PROJECT_TYPE_SEARCH = PROJECT_TYPE + "/search";

  // URLs for Priority
  public static final String PRIORITY = BASE_API_PATH + "priority";
  public static final String PRIORITY_BY_ID = PRIORITY + ID;
  public static final String PRIORITY_SEARCH = PRIORITY + "/search";

  // URLs for Employee
  public static final String EMPLOYEE = BASE_API_PATH + "employee";
  public static final String EMPLOYEE_BY_ID = EMPLOYEE + ID;
  public static final String EMPLOYEE_DESIGNATION_BY_ID = EMPLOYEE + "/designation" + ID;
  public static final String EMPLOYEE_SEARCH = EMPLOYEE + "/search";
  public static final String EMPLOYEE_EXPORT_CSV = EMPLOYEE + "/exportcsv";
  public static final String EMPLOYEE_IMPORT_CSV = EMPLOYEE +"/importcsv";
}
