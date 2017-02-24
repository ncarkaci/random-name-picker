/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomnamepicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author computer
 */
public class CSVReader {
    
    String csvFile          = ""; // CSV file path
    String csvSplitter      = ","; // default comma separater
    int[]  columnNumbers    =  null; 
    boolean isFirstLineSkip = true;  

    
    /**
     * Constracter of the class
     * Use comma separater as default csv file separater
     * @param csvFilePath csv file absolute path 
     */
    public CSVReader(String csvFilePath) {
        this.csvFile = csvFilePath;
    }
    
    
    public CSVReader(String csvFilePath, String csvSeparater) {
        this.csvFile        = csvFilePath;
        this.csvSplitter    = csvSeparater;
    }
    
    public CSVReader(String csvFilePath, String csvSeparater, int[] colums) {
        this.csvFile        = csvFilePath;
        this.csvSplitter    = csvSeparater;
        this.columnNumbers  = colums;
    }

    public String getCsvSplitter() {
        return csvSplitter;
    }

    public void setCsvSplitter(String csvSplitter) {
        this.csvSplitter = csvSplitter;
    }

    public int[] getColumnNumbers() {
        return columnNumbers;
    }

    public void setColumnNumbers(int[] columnNumbers) {
        this.columnNumbers = columnNumbers;
    }

    public boolean isIsFirstLineSkip() {
        return isFirstLineSkip;
    }

    public void setIsFirstLineSkip(boolean isFirstLineSkip) {
        this.isFirstLineSkip = isFirstLineSkip;
    }

    
    public List<String> getNameList(){
    
        List<String> resultSet  = new ArrayList<>();
        String currentPath      = new File("").getAbsolutePath();
        File file               = new File(currentPath+File.separator+csvFile);
        
        BufferedReader reader;
        
        try{
            
            String line ="";
            reader      = new BufferedReader(new FileReader(file));
                    
            // Skip first line  
            if (this.isFirstLineSkip){  reader.readLine(); }
            
            // Read file content
            while ((line = reader.readLine()) != null) {
             
                String[] tokens     = line.split(this.csvSplitter);
                String resultLine   = "";
                
                // Get specific colums if specified
                if (this.columnNumbers != null){
                    for (int columnNumber : this.columnNumbers) {
                        //-1 because of array index start from 0 however column number 1
                        resultLine = resultLine+" "+tokens[columnNumber-1]; 
                    }
                }else {
                    for (String token : tokens) {
                        resultLine = resultLine+" "+token;
                    }
                }
                
                resultSet.add(resultLine);
             
            }

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        toString(resultSet);
        return resultSet;
    }
    
    public void toString(List<String> list){
        for (String item : list) {
            
            System.err.println(item);
        }        
    }
    public static void main(String[] args) {
        CSVReader csvFileConverter = new CSVReader("orders.csv");
        int[] columnNumbers = new int[]{2, 14, 15};
        csvFileConverter.setColumnNumbers(columnNumbers);
        csvFileConverter.getNameList();
    }
    
}
