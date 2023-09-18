package com.csci.assignment.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class Transcript {
  //Array to hold header information
  private String[] headerInformation;

  // Map to store course information
  private Map<String, Course> courses = new LinkedHashMap<>();

  //contructor
  public Transcript(String[] headerInformation) {
    this.headerInformation = headerInformation;
  }

  // Method to add course information to the transcript
  public void addCourse(String term, String subjectCode, int courseNumber, String courseTitle, int creditHours, String studentGrade) {
    Course course = new Course(term, subjectCode, courseNumber, courseTitle, creditHours, studentGrade);

    //⚠️ List data structure might suffice for this
    courses.put(term + subjectCode + courseNumber, course);
  }

  //getter methods
  public String[] getHeaderInformation() {
    return headerInformation;
  }

  public Map<String, Course> getCourses() {
    return courses;
  }
}
