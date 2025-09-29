package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for reading test cases from Excel files
 * Supports the Welldoc SMIT Portal test case format
 */
public class ExcelReader {
    
    private static final String TEST_CASE_ID = "Test Case ID";
    private static final String AUTOMATION_CLASS_NAME = "Automation Class Name";
    private static final String AUTOMATION_METHOD_NAME = "Automation Method Name";
    private static final String PRE_CONDITIONS = "Pre-Conditions";
    private static final String TEST_SCENARIO_SUMMARY = "Test Scenario Summary";
    private static final String TEST_DATA = "Test Data";
    private static final String TEST_CASE_STEPS = "Test Case (steps)";
    private static final String EXPECTED_RESULTS = "Expected Results";

    /**
     * Reads test cases from Excel file and converts them to TestCase objects
     * @param filePath Path to the Excel file
     * @return List of TestCase objects
     */
    public static List<TestCase> readTestCases(String filePath) throws IOException {
        List<TestCase> testCases = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> columnMap = createColumnMap(sheet);
            
            // Skip header row, start from row 1
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                TestCase testCase = createTestCaseFromRow(row, columnMap);
                if (testCase != null && testCase.getId() != null && !testCase.getId().trim().isEmpty()) {
                    testCases.add(testCase);
                }
            }
        }
        
        return testCases;
    }
    
    /**
     * Creates a column mapping for the Excel headers
     */
    private static Map<String, Integer> createColumnMap(Sheet sheet) {
        Map<String, Integer> columnMap = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        
        if (headerRow != null) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerName = getCellValueAsString(cell).trim();
                    columnMap.put(headerName, i);
                }
            }
        }
        
        return columnMap;
    }
    
    /**
     * Creates a TestCase object from an Excel row
     */
    private static TestCase createTestCaseFromRow(Row row, Map<String, Integer> columnMap) {
        TestCase testCase = new TestCase();
        
        testCase.setId(getCellValue(row, columnMap, TEST_CASE_ID));
        testCase.setClassName(getCellValue(row, columnMap, AUTOMATION_CLASS_NAME));
        testCase.setMethodName(getCellValue(row, columnMap, AUTOMATION_METHOD_NAME));
        testCase.setPreConditions(getCellValue(row, columnMap, PRE_CONDITIONS));
        testCase.setSummary(getCellValue(row, columnMap, TEST_SCENARIO_SUMMARY));
        testCase.setTestData(getCellValue(row, columnMap, TEST_DATA));
        testCase.setSteps(parseSteps(getCellValue(row, columnMap, TEST_CASE_STEPS)));
        testCase.setExpected(getCellValue(row, columnMap, EXPECTED_RESULTS));
        
        // Extract structured test data
        extractStructuredTestData(testCase);
        
        return testCase;
    }
    
    /**
     * Gets cell value as string, handling different cell types
     */
    private static String getCellValue(Row row, Map<String, Integer> columnMap, String columnName) {
        Integer columnIndex = columnMap.get(columnName);
        if (columnIndex == null) return "";
        
        Cell cell = row.getCell(columnIndex);
        return getCellValueAsString(cell);
    }
    
    /**
     * Converts cell value to string regardless of cell type
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Parses test case steps from a single cell value
     * Assumes steps are separated by newlines or semicolons
     */
    private static List<String> parseSteps(String stepsText) {
        List<String> steps = new ArrayList<>();
        if (stepsText == null || stepsText.trim().isEmpty()) {
            return steps;
        }
        
        // Split by newlines, semicolons, or numbered lists
        String[] stepArray = stepsText.split("[\n\r;]+");
        for (String step : stepArray) {
            String cleanStep = step.trim();
            if (!cleanStep.isEmpty()) {
                // Remove step numbers (1., 2., etc.)
                cleanStep = cleanStep.replaceAll("^\\d+\\.\\s*", "");
                steps.add(cleanStep);
            }
        }
        
        return steps;
    }
    
    /**
     * Extracts structured test data from test data field
     * Looks for patterns like "Username: value" and "Password: value"
     */
    private static void extractStructuredTestData(TestCase testCase) {
        String testData = testCase.getTestData();
        if (testData == null || testData.trim().isEmpty()) {
            return;
        }
        
        Map<String, String> structuredData = new HashMap<>();
        String[] lines = testData.split("[\n\r]+");
        
        for (String line : lines) {
            line = line.trim();
            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase();
                    String value = parts[1].trim();
                    structuredData.put(key, value);
                }
            }
        }
        
        testCase.setStructuredTestData(structuredData);
    }
}
