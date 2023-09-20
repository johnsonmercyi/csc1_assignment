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

  @Test
  public void testGradeScaleWithAlphabeticGradeToDalGrade() {
    // Prepare valid input for the gradeScale method with an alphabetic grade that
    // translates to a Dal grade
    String scaleName = "AlphabetToDalGradeScale";
    String scaleInfo = "resources/AlphabetToDalGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithAlphabeticGradeToNonDalGrade() {
    // Prepare valid input for the gradeScale method with an alphabetic grade that
    // translates to a non-Dal grade
    String scaleName = "AlphabetToNonDalGradeScale";
    String scaleInfo = "resources/AlphabetToNonDalGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithAlphabeticGradeWithDash() {
    // Prepare valid input for the gradeScale method with an alphabetic grade
    // containing a dash
    String scaleName = "AlphabetWithDashGradeScale";
    String scaleInfo = "resources/AlphabetWithDashGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeRange() {
    // Prepare valid input for the gradeScale method with a properly formatted
    // numeric grade range
    String scaleName = "NumericGradeRangeScale";
    String scaleInfo = "resources/NumericGradeRangeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeNoEndingGrade() {
    // Prepare valid input for the gradeScale method with a numeric grade missing
    // the ending grade
    String scaleName = "NumericNoEndingGradeScale";
    String scaleInfo = "resources/NumericNoEndingGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeNoStartingGrade() {
    // Prepare valid input for the gradeScale method with a numeric grade missing
    // the starting grade
    String scaleName = "NumericNoStartingGradeScale";
    String scaleInfo = "resources/NumericNoStartingGradeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericStartingGradeGreaterThanEndingGrade() {
    // Prepare valid input for the gradeScale method with a numeric grade where
    // starting grade is greater than ending grade
    String scaleName = "NumericInvalidGradeRangeScale";
    String scaleInfo = "resources/NumericInvalidGradeRangeScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeRangeHole() {
    // Prepare valid input for the gradeScale method with a hole in the numeric
    // grade range
    String scaleName = "NumericGradeRangeWithHoleScale";
    String scaleInfo = "resources/NumericGradeRangeWithHoleScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeInOrder() {
    // Prepare valid input for the gradeScale method with numeric grades in order
    String scaleName = "NumericGradeInOrderScale";
    String scaleInfo = "resources/NumericGradeInOrderScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader scaleInfoReader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertTrue(admissionInfo.gradeScale(scaleName, scaleInfoReader));
  }

  @Test
  public void testGradeScaleWithNumericGradeInRandomOrder() {
    // Prepare valid input for the gradeScale method with numeric grades in random
    // order
    String scaleName = "NumericGradeRandomOrderScale";
    String scaleInfo = "resources/NumericGradeRandomOrderScale.txt";
    InputStream in = getClass().getClassLoader().getResourceAsStream(scaleInfo);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    // Test the gradeScale method
    assertFalse(admissionInfo.gradeScale(scaleName, reader));
  }

}
