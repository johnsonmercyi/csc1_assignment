package com.csci.assignment.basic_test_service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import com.csci.assignment.AdmissionInfo;

public class AssignmentInfoTestService {
  //AssignmentInfo object
  AdmissionInfo ai;
  // Use ClassLoader to load the resource
  ClassLoader classLoader;

  public AssignmentInfoTestService(AdmissionInfo ai) {
    this.ai = ai;
    classLoader = getClass().getClassLoader();
  }

  public void testGradeScale(Map<String, String> scales) {
    for (Map.Entry<String, String> scale : scales.entrySet()) {
      String resourcePath = scale.getValue();
      InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
      
      if (inputStream != null) {
        try (BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(inputStream))) {
          boolean isStored = ai.gradeScale(scale.getKey(), scaleInfoReader);
          if (isStored) {
            System.out.println("\n" + scale.getKey() + " scales stored successfully.");
          } else {
            System.out.println("\n" + scale.getKey() + " scales not stored.");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Resource not found: " + resourcePath);
      }
    }

  }

  public void testCoreAdmissionCourse(String[] courseStems) {
    int tracker = 0;
    for (String courseStem : courseStems) {
      boolean isStored = ai.coreAdmissionCourse(courseStem);
      if (isStored) tracker ++;
    }

    if (tracker == courseStems.length) {
      System.out.println("\nCourse stems stored successfully!");
    } else if (tracker == 0) {
      System.out.println("\nCourse stems couldn't be stored!");
    } else if (tracker > 0 && tracker < courseStems.length) {
      System.out.println("\nCourse stems couldn't store completely!");
    }
  }
  
  public void testApplicantTranscript(Map<String, String> transcripts) {
    for (Map.Entry<String, String> transcript : transcripts.entrySet()) {
      String resourcePath = transcript.getValue();
      InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

      if (inputStream != null) {
        try (BufferedReader transcriptInfoReader = new BufferedReader(new InputStreamReader(inputStream))) {
          boolean isStored = ai.applicantTranscript(transcript.getKey(), transcriptInfoReader);
          if (isStored) {
            System.out.println("\n" + transcript.getKey() + " transcript stored successfully.");
          } else {
            System.out.println("\n" + transcript.getKey() + " transcript not stored.");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Resource not found: " + resourcePath);
      }
    }
  }

  public void testTranslateTranscript(String applicantId, PrintWriter out, StringWriter sw) {
    // Translate the transcript and print the result
    if (ai.translateTranscript(applicantId, out)) {
      System.out.println("\nTranscript translation successful:");
      System.out.println(sw.toString());
    } else {
      System.out.println("\nTranscript translation failed.");
    }
  }

  public void applicantGPA(String applicantId, double maxHours, Set<String> coursesToExclude) {
    double gpa = ai.applicantGPA(applicantId, maxHours, coursesToExclude);

    // Print the GPA result
    System.out.println("GPA for Applicant " + applicantId + ": " + gpa);
  }

  public void testCoursesTaken(String applicantId) {
    Map<String, Integer> courses = ai.coursesTaken(applicantId);
    if (courses.size() > 0) {
      System.out.println("\nCourses taken successfully retrieved:");
      for (Map.Entry<String, Integer> course : courses.entrySet()) {
        if (course.getValue() != -1) {
          System.out.println(course.getKey() + " " + course.getValue());
        }
      }
    } else {
      System.out.println("No courses taken!");
    }
  }

}
