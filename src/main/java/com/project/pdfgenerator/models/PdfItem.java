package com.project.pdfgenerator.models;

import java.util.Date;
import java.util.List;

public class PdfItem {
    public String Region, Borrower, City, Relationship, Examiner, LeverageFinancing;
    public int BusType, Guarantor;
    public boolean NewBorrower;
    public Date ExamDate;
    public List<TableData> tableDataList;

    public PdfItem(String region, String borrower, String city, String relationship, String examiner,
                   String leverageFinancing, int busType, int guarantor, boolean newBorrower, Date examDate,
                   List<TableData> tableDataList) {
        Region = region;
        Borrower = borrower;
        City = city;
        Relationship = relationship;
        Examiner = examiner;
        LeverageFinancing = leverageFinancing;
        BusType = busType;
        Guarantor = guarantor;
        NewBorrower = newBorrower;
        ExamDate = examDate;
        this.tableDataList = tableDataList;
    }
}
