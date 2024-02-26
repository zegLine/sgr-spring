package com.zegline.sgrspring.model.filter;

public enum SGRFilterType {
    STRING(0),
    NUMERIC(1);

    private int numVal;

    SGRFilterType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
