package com.ishallnotwant.utils.excel2;


public class ExcelMergedLocation {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

    public ExcelMergedLocation(int firstRow, int lastRow) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    public ExcelMergedLocation(int firstRow, int firstCol, int lastRow, int lastCol) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

    public ExcelMergedLocation() {
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(int firstCol) {
        this.firstCol = firstCol;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }
}
