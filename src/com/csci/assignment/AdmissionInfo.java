package com.csci.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csci.assignment.beans.Course;
import com.csci.assignment.beans.Transcript;

public class AdmissionInfo {

  // Helper methods class
  private HelperMethods hm;

  // Map to store grading scales with their names
  private static Map<String, Map<String, String>> gradingScales;

  // Set to store courses of interest identified by their course stems
  private Set<String> interestCourses;

  // Map to store applicant transcripts identified by their applicantId
  private Map<String, Transcript> applicantTranscripts;

  // Class constructor
  public AdmissionInfo() {
    hm = new HelperMethods();
    gradingScales = new LinkedHashMap<>();
    applicantTranscripts = new LinkedHashMap<>();
    interestCourses = new LinkedHashSet<>();
  }

  /**
   * Records a grading scale for university transcripts.
   *
   * @param scaleName The name of the grading scale.
   * @param scaleInfo A BufferedReader containing the scale information.
   * @return True if the grading scale was successfully recorded, false otherwise.
   */
  public boolean gradeScale(String scaleName, BufferedReader scaleInfo) {
    try {
      if (scaleName == null || scaleName.trim().isEmpty() || gradingScales.containsKey(scaleName) || scaleInfo == null
          || !scaleInfo.ready()) {
        System.out.println("Invalid scale format");
        return false; // Invalid scaleName or scaleName already exists
      }

      Set<String> validDalGrades = new HashSet<>(
          Arrays.asList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"));
      Map<String, String> scaleMapping = new LinkedHashMap<>();
      String line;

      // Initialize prevEndGrade to -1
      int prevEndGrade = -1;
      boolean hasHole = false;

      while ((line = scaleInfo.readLine()) != null) {
        System.out.println("Line read: " + line);
        // Split the line into original grade and Dalhousie letter grade
        String[] parts = line.split("\t");

        // Check if the line has the correct format
        if (parts.length == 2) {
          String originalGrade = parts[0];
          String dalhousieGrade = parts[1];

          if (originalGrade.matches(".*[a-zA-Z].*")) {
            if (originalGrade.contains("-")) {
              System.out.println("Invalid alphabet grade: " + originalGrade);
              return false;
            }
          }

          if (originalGrade.matches(".*\\d.*")) {
            int startGrade = Integer.parseInt(originalGrade.split("-")[0]);
            int endGrade = Integer.parseInt(originalGrade.split("-")[1]);
            if (startGrade > endGrade) {
              System.out.println("Malformed numeric grade: " + originalGrade);
              return false;
            }

            // Check that the numeric grade range has no holes
            if (prevEndGrade != -1 && startGrade != prevEndGrade + 1) {
              System.out.println("Hole in numeric grade range: " + (prevEndGrade + 1) + "-" + (startGrade - 1));
              hasHole = true;
            }

            prevEndGrade = endGrade;
          }

          // Check if the Dalhousie grade is in the validDalGrades set
          if (!validDalGrades.contains(dalhousieGrade)) {
            System.out.println("Invalid Dalhousie grade: " + dalhousieGrade);
            return false;
          }

          if (scaleMapping.containsKey(originalGrade)) {
            System.out.println("Duplicate numeric grade: " + originalGrade);
            return false; // Duplicate  grade
          }

          // Add the mapping to the grading scale
          scaleMapping.put(originalGrade, dalhousieGrade);
        } else {
          // Invalid format, return false
          return false;
        }
      }

      //If it has holes then it's not a valid grade scale
      if (hasHole) {
        System.out.println("Numeric grade range contains holes");
        return false;
      }

      // Store the grading scale in the gradingScales map
      gradingScales.put(scaleName, scaleMapping);
      return true;

    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Records a core admission course stem.
   *
   * @param courseStem The course stem to record.
   * @return True if the course stem was successfully recorded, false otherwise.
   */
  public boolean coreAdmissionCourse(String courseStem) {
    // Check if the courseStem is not null or empty
    if (courseStem != null && !courseStem.trim().isEmpty()) {
      // Add the courseStem to the set of interest courses
      interestCourses.add(courseStem);
      return true;
    }
    return false; // Return false if the courseStem is invalid
  }

  /**
   * Accepts and stores an applicant's transcript.
   *
   * @param applicantId      The ID of the applicant.
   * @param transcriptStream A BufferedReader containing the applicant's
   *                         transcript.
   * @return True if the transcript was successfully recorded, false otherwise.
   */
  public boolean applicantTranscript(String applicantId, BufferedReader transcriptStream) {
    try {
      if (applicantId == null || applicantId.trim().isEmpty() || applicantId.trim() == "") {
        System.err.println("Applicant not found!");
        return false; // Invalid scaleName or scaleName already exists
      }
      return hm.readAndParseHeaderLines(applicantId, transcriptStream);
    } catch (Exception e) {
      // TODO: handle exception
      return false;
    }
  }

  public boolean translateTranscript(String applicantId, PrintWriter convertedTranscript) {
    // Check if the applicantId exists in the transcripts
    if (applicantId == null || applicantId.trim().isEmpty() || !applicantTranscripts.containsKey(applicantId)) {
      return false;
    }

    try {
      // Get the applicant's transcript
      Transcript transcript = applicantTranscripts.get(applicantId);

      // Write the header information to the PrintWriter
      String[] headerInformation = transcript.getHeaderInformation();

      for (String headerLine : headerInformation) {
        // Check if the header line is the Grade Scale header
        if (headerLine.startsWith("Grade scale")) {
          convertedTranscript.println("Grade scale\tDalhousie standard"); // Set the grade scale to Dalhousie standard
        } else {
          convertedTranscript.println(headerLine); // Include the rest of the header information
        }
      }

      // Write the transcript courses
      Map<String, Course> courses = transcript.getCourses();
      for (Map.Entry<String, Course> entry : courses.entrySet()) {
        Course course = entry.getValue();

        // Translate the student grade to Dalhousie letter grade
        String translatedGrade = hm.translateGrade(course.getStudentGrade());

        // Write the course information to the PrintWriter
        convertedTranscript.println(
            course.getTerm() + "\t" +
                course.getSubjectCode() + "\t" +
                course.getCourseNumber() + "\t" +
                course.getCourseTitle() + "\t" +
                course.getCreditHours() + "\t" +
                translatedGrade);
      }

      // Flush the PrintWriter and return true to indicate success
      convertedTranscript.flush();
      return true;
    } catch (Exception e) {
      // Handle exceptions
      System.out.println("Exception: " + e.getMessage());
      return false;
    }
  }

  /**
   * Calculates the GPA of an applicant based on their transcript.
   * 
   * @param applicantId      The ID of the applicant.
   * @param maxHours         The maximum credit hours to consider for GPA
   *                         calculation.
   * @param coursesToExclude A set of course titles to exclude from GPA
   *                         calculation.
   * @return The computed GPA or -1.0 in case of an error.
   */
  public double applicantGPA(String applicantId, double maxHours, Set<String> coursesToExclude) {
    // Check if the applicantId exists in the transcripts
    if (applicantId == null || applicantId.trim().isEmpty() || maxHours == 0.0
        || !applicantTranscripts.containsKey(applicantId)) {
      return -1.0; // Return a negative number to indicate an error
    }

    // Retrieve the applicant's transcript
    Transcript transcript = applicantTranscripts.get(applicantId);

    // Filter and sort courses based on criteria
    List<Course> filteredCourses = hm.filterAndSortCourses(transcript, maxHours, coursesToExclude);

    // Calculate GPA
    double totalCredits = 0.0;
    double weightedGradeSum = 0.0;

    for (Course course : filteredCourses) {
      // Convert the course grade to a numeric value using the grade conversion scale
      double numericGrade = hm.convertGradeToNumeric(course.getStudentGrade());

      if (numericGrade >= 0) { // Exclude courses with no numeric equivalent
        // Calculate the weighted contribution of the course
        double weightedContribution = numericGrade * course.getCreditHours();

        // Update total credits and weighted grade sum
        totalCredits += course.getCreditHours();
        weightedGradeSum += weightedContribution;

        // Check if the maximum credit hours have been reached
        if (totalCredits >= maxHours) {
          break; // Stop including courses once the maximum credit hours are met
        }
      }
    }

    // Calculate the GPA
    if (totalCredits > 0) {
      double gpa = weightedGradeSum / totalCredits;
      return Math.round(gpa * 100.0) / 100.0; // Round to two decimal points
    } else {
      return -1.0; // Return a negative number if there are no valid courses to calculate GPA
    }
  }

  /**
   * Determines when core courses were taken by an applicant.
   *
   * @param applicantId The ID of the applicant.
   * @return A map of core course stems and the last term taken, or null in case
   *         of an error.
   */
  public Map<String, Integer> coursesTaken(String applicantId) {
    // Check if the applicantId exists in the transcripts
    if (applicantId == null || applicantId.trim().isEmpty() || !applicantTranscripts.containsKey(applicantId)) {
      return null; // Applicant not found
    }

    // Get the applicant's transcript
    Transcript transcript = applicantTranscripts.get(applicantId);

    // Create a map to store the last occurrence of words from coreAdmissionCourse
    Map<String, Integer> lastOccurrenceMap = new LinkedHashMap<>();

    // Initialize the map with words from coreAdmissionCourse
    for (String word : interestCourses) {
      lastOccurrenceMap.put(word, -1); // Initialize to -1 (not found)
    }

    // Get the courses from the transcript
    Map<String, Course> courses = transcript.getCourses();

    // Initialize the date of the first course registration
    LocalDate firstRegistrationDate = null;

    for (Map.Entry<String, Course> entry : courses.entrySet()) {
      Course course = entry.getValue();
      LocalDate courseDate = LocalDate.parse(course.getTerm() + "-01"); // Assuming YYYY-MM format

      // Initialize the first registration date
      if (firstRegistrationDate == null) {
        firstRegistrationDate = courseDate;
      }

      // Calculate the number of months since the first registration
      int monthsSinceFirstRegistration = (int) ChronoUnit.MONTHS.between(firstRegistrationDate, courseDate);

      // Check if the course title contains words from coreAdmissionCourse
      for (String word : interestCourses) {
        if (course.getCourseTitle().toLowerCase().contains(word.toLowerCase())) {
          // Update the last occurrence of the word
          lastOccurrenceMap.put(word, monthsSinceFirstRegistration);
        }
      }
    }

    return lastOccurrenceMap;
  }

  /**
   * Contains helper methods
   */
  private class HelperMethods {

    public boolean readAndParseHeaderLines(String applicantId, BufferedReader transcriptStream) throws IOException {
      // Read and parse header lines
      String[] headerLines = new String[6];
      for (int i = 0; i < headerLines.length; i++) {
        headerLines[i] = transcriptStream.readLine();
        if (headerLines[i] == null) {
          // Insufficient header lines
          return false;
        }
      }

      // Validate header lines
      if (!isValidHeader(headerLines))
        return false;

      // Create a new transcript object with header information
      Transcript transcript = new Transcript(headerLines);

      return readAndParseCourseLines(applicantId, transcriptStream, transcript);
    }

    public boolean readAndParseCourseLines(String applicantId, BufferedReader transcriptStream, Transcript transcript)
        throws NumberFormatException, IOException {
      // Read and parse course grade lines
      String line;
      while ((line = transcriptStream.readLine()) != null) {
        String[] fields = line.split("\t");
        if (fields.length != 6) {
          // Invalid course grade line
          return false;
        }

        // Parse and add course information
        String term = fields[0];
        String subjectCode = fields[1];
        int courseNumber = Integer.parseInt(fields[2]);
        String courseTitle = fields[3];
        int creditHours = Integer.parseInt(fields[4]);
        String studentGrade = fields[5];

        transcript.addCourse(term, subjectCode, courseNumber, courseTitle, creditHours, studentGrade);
      }

      // Associate the transcript with the applicantId
      applicantTranscripts.put(applicantId, transcript);
      return true;
    }

    // Method to validate header lines
    public boolean isValidHeader(String[] headerLines) {
      // Check if the number of header lines is correct (should be 6)
      if (headerLines.length != 6) {
        return false;
      }

      // Validate header lines based on the specified order of identifiers
      return headerLines[0].startsWith("Institution")
          && headerLines[1].startsWith("Grade scale")
          && headerLines[2].startsWith("Student name")
          && headerLines[3].startsWith("Student identifier")
          && headerLines[4].startsWith("Program")
          && headerLines[5].startsWith("Major");
    }

    private String translateGrade(String originalGrade) {
      // Iterate over the available grading scales
      for (Map<String, String> scale : gradingScales.values()) {
        // Try to find the translation in the current grading scale
        String translatedGrade = scale.get(originalGrade);

        // If found, return the translated grade
        if (translatedGrade != null) {
          return translatedGrade;
        }

        if (originalGrade.matches("\\d+")) {
          // If not found, check for numeric range formats in the grading scale
          for (String scaleKey : scale.keySet()) {
            String[] rangeParts = scaleKey.split("-");
            if (rangeParts.length == 2) {
              int startGrade = Integer.parseInt(rangeParts[0]);
              int endGrade = Integer.parseInt(rangeParts[1]);
              int numericGrade = Integer.parseInt(originalGrade);

              // Check if the numeric grade falls within the range
              if (numericGrade >= startGrade && numericGrade <= endGrade) {
                return scale.get(scaleKey);
              }
            }
          }
        }
      }

      // If not found in any scale, return the original grade
      return originalGrade;
    }

    private List<Course> filterAndSortCourses(Transcript transcript, double maxHours, Set<String> coursesToExclude) {
      List<Course> filteredCourses = new ArrayList<>();

      // Iterate over the transcript courses
      for (Course course : transcript.getCourses().values()) {
        String courseTitle = course.getCourseTitle();

        // Check if the course title contains any word from coursesToExclude
        boolean excludeCourse = false;

        for (String exclusionKeyword : coursesToExclude) {
          if (courseTitle.contains(exclusionKeyword)) {
            excludeCourse = true;
            break;
          }
        }

        if (!excludeCourse) {
          filteredCourses.add(course);
        }
      }

      // Sort filtered courses by course number (descending) and subject code
      // (ascending)
      Collections.sort(filteredCourses, (course1, course2) -> {
        if (course1.getCourseNumber() != course2.getCourseNumber()) {
          // course numbers are not a tie
          return course2.getCourseNumber() - course1.getCourseNumber();
        } else {
          // there is a tie
          return course1.getSubjectCode().compareTo(course2.getSubjectCode());
        }
      });

      return filteredCourses;
    }

    private double convertGradeToNumeric(String grade) {
      // Define the Dalhousie grade scale mapping
      Map<String, Double> dalhousieGradeScale = new LinkedHashMap<>();
      dalhousieGradeScale.put("A+", 4.3);
      dalhousieGradeScale.put("A", 4.0);
      dalhousieGradeScale.put("A-", 3.7);
      dalhousieGradeScale.put("B+", 3.3);
      dalhousieGradeScale.put("B", 3.0);
      dalhousieGradeScale.put("B-", 2.7);
      dalhousieGradeScale.put("C+", 2.3);
      dalhousieGradeScale.put("C", 2.0);
      dalhousieGradeScale.put("C-", 1.7);
      dalhousieGradeScale.put("D", 1.0);
      dalhousieGradeScale.put("F", 0.0);

      // Check if the grade exists in the Dalhousie grade scale
      if (dalhousieGradeScale.containsKey(grade)) {
        return dalhousieGradeScale.get(grade);
      } else {
        try {
          // Check if grade is an integer or not
          if (grade.matches("\\d+")) {
            // Grade is an integer

            // Convert grade to an integer
            int numGrade = Integer.parseInt(grade);

            // Find the matching grading scale based on numeric range
            for (Map<String, String> scale : gradingScales.values()) {
              for (String key : scale.keySet()) {

                if (key.contains("-")) {
                  String[] gradeParts = key.split("-");
                  int startGrade = Integer.parseInt(gradeParts[0]);
                  int endGrade = Integer.parseInt(gradeParts[1]);

                  // check if grade is within the range of the current grade scale
                  if (numGrade >= startGrade && numGrade <= endGrade) {
                    return dalhousieGradeScale.get(scale.get(key));
                  }
                }
              }
            }
          } else {
            // Grade is a pair of letters

            // Find the matching grading scale based on letter grade
            for (Map<String, String> scale : gradingScales.values()) {
              if (scale.containsKey(grade)) {
                return dalhousieGradeScale.get(scale.get(grade));
              }
            }
          }
        } catch (Exception e) {
          // Return an error value
          return -1.0;
        }
      }

      return -1.0; // Grade not found in any grading scale
    }
  }
}
