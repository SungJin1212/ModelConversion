package CodeGeneration.DataObject.GeoModelDataObject;

import java.util.ArrayList;

public class MapModelInfo {
    private ArrayList<String> mapInitializationList;
    private ArrayList<LocDimensionVar> locDimensionVarList;
    private ArrayList<LocInformationVar> locInformationVarList;
    private String mapName;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public ArrayList<String> getMapInitializationList() {
        return mapInitializationList;
    }

    public void setMapInitializationList(ArrayList<String> mapInitializationList) {
        this.mapInitializationList = mapInitializationList;
    }

    public ArrayList<LocDimensionVar> getLocDimensionVarList() {
        return locDimensionVarList;
    }

    public void setLocDimensionVarList(ArrayList<LocDimensionVar> locDimensionVarList) {
        this.locDimensionVarList = locDimensionVarList;
    }

    public ArrayList<LocInformationVar> getLocInformationVarList() {
        return locInformationVarList;
    }

    public void setLocInformationVarList(ArrayList<LocInformationVar> locInformationVarList) {
        this.locInformationVarList = locInformationVarList;
    }
}

