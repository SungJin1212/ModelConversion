package CodeGeneration.DataObject.GeoModelDataObject;

import java.util.ArrayList;

public class LocInformationVar {
    private String varName; // name
    private String varType; // enum, int, float...
    private String varMin; // min value
    private String varMax; // max value
    private ArrayList<String> varEnumList; // enumeration list
    private String discreteValue;
    private String defaultVal; // default value


    public String getDiscreteValue() {
        return discreteValue;
    }

    public void setDiscreteValue(String discreteValue) {
        this.discreteValue = discreteValue;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarMin() {
        return varMin;
    }

    public void setVarMin(String varMin) {
        this.varMin = varMin;
    }

    public String getVarMax() {
        return varMax;
    }

    public LocInformationVar(String varName, String varType, String varMin, String varMax, String discreteValue, String defaultVal) {
        this.varName = varName;
        this.varType = varType;
        this.varMin = varMin;
        this.varMax = varMax;
        this.discreteValue = discreteValue;
        this.defaultVal = defaultVal;
    }

    public void setVarMax(String varMax) {
        this.varMax = varMax;
    }

    public LocInformationVar(String varName, String varType, ArrayList<String> varEnumList) {
        this.varName = varName;
        this.varType = varType;
        this.varEnumList = varEnumList;
    }

    public ArrayList<String> getVarEnumList() {
        return varEnumList;
    }

    public void setVarEnumList(ArrayList<String> varEnumList) {
        this.varEnumList = varEnumList;
    }


}
