package com.ISHALLNOTWANT.utils.excel2;


public class POIUtilBean {

    /**
     * 单元格包含几行
     */
    private int rowWidth = 1;

    /**
     * 单元格包含几列
     */
    private int colWidth = 1;

    /**
     * 单元格是否是合并单元格
     */
    private boolean merged = false;

    public int getRowWidth() {
        return rowWidth;
    }

    public void setRowWidth(int rowWidth) {
        this.rowWidth = rowWidth;
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }
}
