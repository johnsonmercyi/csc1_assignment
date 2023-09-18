package com.csci.assignment.beans;

public class Course {
  private String term;
  private String subjectCode;
  private int courseNumber;
  private String courseTitle;
  private int creditHours;
  private String studentGrade;

  public Course(String term, String subjectCode, int courseNumber, String courseTitle, int creditHours, String studentGrade) {
    this.term = term;
    this.subjectCode = subjectCode;
    this.courseNumber = courseNumber;
    this.courseTitle = courseTitle;
    this.creditHours = creditHours;
    this.studentGrade = studentGrade;
  }

  /**
   * @return the term
   */
  public String getTerm() {
    return term;
  }

  /**
   * @return the subjectCode
   */
  public String getSubjectCode() {
    return subjectCode;
  }

  /**
   * @return the courseNumber
   */
  public int getCourseNumber() {
    return courseNumber;
  }

  /**
   * @return the courseTitle
   */
  public String getCourseTitle() {
    return courseTitle;
  }

  /**
   * @return the creditHours
   */
  public int getCreditHours() {
    return creditHours;
  }

  /**
   * @return the studentGrade
   */
  public String getStudentGrade() {
    return studentGrade;
  }

  
}
