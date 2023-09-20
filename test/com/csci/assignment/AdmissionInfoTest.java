package com.csci.assignment;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdmissionInfoTest {

  // AdmissionInfo object
  private AdmissionInfo admissionInfo;

  @BeforeEach
  public void setUp() {
    admissionInfo = new AdmissionInfo();
  }

  @Test
  public void testGradeScaleWithNullScaleName() {
    String scaleName = null;
    String scaleInfo = "resources/SingleLineGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithEmptyScaleName() {
    String scaleName = "";
    String scaleInfo = "resources/SingleLineGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithDuplicateScaleName() {
    String scaleName = "DuplicateScaleName";
    String scaleInfo = "resources/SingleLineGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader)); // Duplicate
  }

  @Test
  public void testGradeScaleWithNullScaleInfo() {
    assertFalse(admissionInfo.gradeScale("NullScaleInfo", null));
  }

  @Test
  public void testGradeScaleWithEmptyScaleInfo() {
    String scaleName = "EmptyScaleInfo";
    String scaleInfo = "resources/EmptyGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName,scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithInvalidScaleInfoWithNoTabSeparation() {
    String scaleName = "InvalidScaleInfo";
    String scaleInfo = "resources/NoTabSeparationGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithThreeFieldsInLine() {
    // Prepare valid input for the gradeScale method with a line containing 3 fields
    String scaleName = "ThreeFieldsScale";
    String scaleInfo = "resources/ThreeFieldsGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithOneLine() {
    // Prepare valid input for the gradeScale method with a single line
    String scaleName = "SingleLineScale";
    String scaleInfo = "resources/SingleLineGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithMultipleLines() {
    // Prepare valid input for the gradeScale method
    String scaleName = "MultipleLinesGradeScale";
    String scaleInfo = "resources/IntegerPairGradeScale.txt";

    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method with valid input
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

}
