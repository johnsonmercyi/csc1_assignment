package com.csci.assignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.csci.assignment.basic_test_service.AssignmentInfoTestService;
import com.csci.assignment.util.TaskManager;

public class A1 {
  public static void main(String[] args) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      // Create an instance of AdmissionInfo
      AdmissionInfo ai = new AdmissionInfo();
      AssignmentInfoTestService test = new AssignmentInfoTestService(ai);

      // Read applicantId from keyboard input
      System.out.print("Enter applicantId: ");
      String applicantId = reader.readLine();

      /**
       * Test `gradeScale` method
       */
      TaskManager.loadTask(() -> {
        // List to hold scales information to be stored
        Map<String, String> scales = new LinkedHashMap<>();
        scales.put("IntegerPairGradeScale", "IntegerPairGradeScale.txt");
        scales.put("LetterPairsGradeScale", "LetterPairsGradeScale.txt");

        // run gradeScale test
        test.testGradeScale(scales);
      });

      /**
       * Test `coreAdmissionCourse` method
       */
      TaskManager.loadTask(() -> {
        String[] courseStems = {
            "abacus",
            "tablet",
            "algorithm",
        };

        // run coreAdmissionCourse test
        test.testCoreAdmissionCourse(courseStems);
      });

      /**
       * Test `applicantTranscript` method
       */
      TaskManager.loadTask(() -> {
        // List to hold scales information to be stored
        Map<String, String> transcripts = new LinkedHashMap<>();

        transcripts.put(applicantId, "transcripts/Transcript1.txt");

        // run applicantTranscript test
        test.testApplicantTranscript(transcripts);
      });

      /**
       * Test `translateTranscript` method
       */
      TaskManager.loadTask(() -> {
        // Create a StringWriter to capture the translated transcript
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        // run applicantTranscript test
        test.testTranslateTranscript(applicantId, pw, sw);
      });

      /**
       * Test `applicantGPA` method
       */
      TaskManager.loadTask(() -> {
        double maxHours = 12.0;// test max hours
        Set<String> coursesToExclude = new LinkedHashSet<>();
        coursesToExclude.add("abacus");
        coursesToExclude.add("tablets");

        // run applicantTranscript test
        test.applicantGPA(applicantId, maxHours, coursesToExclude);
      });

      /**
       * Test `coursesTaken` method
       */
      TaskManager.loadTask(() -> {
        // run applicantTranscript test
        test.testCoursesTaken(applicantId);
      });

      TaskManager.executeTasks();
    } catch (Exception e) {
      System.out.println("Error reading input: " + e.getMessage());
    }

  }
}
