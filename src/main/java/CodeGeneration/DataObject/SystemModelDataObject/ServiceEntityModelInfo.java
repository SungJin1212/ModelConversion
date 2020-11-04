package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class ServiceEntityModelInfo {
    private String ServiceEntityName;
    private ArrayList<ActionInfo>  actionInfoList;
    private ArrayList<LocationInfo> locationInfoList;

    public String getServiceEntityName() {
        return ServiceEntityName;
    }

    public void setServiceEntityName(String serviceEntityName) {
        ServiceEntityName = serviceEntityName;
    }

    public ArrayList<ActionInfo> getActionInfoList() {
        return actionInfoList;
    }

    public void setActionInfoList(ArrayList<ActionInfo> actionInfoList) {
        this.actionInfoList = actionInfoList;
    }

    public ArrayList<LocationInfo> getLocationInfoList() {
        return locationInfoList;
    }

    public void setLocationInfoList(ArrayList<LocationInfo> locationInfoList) {
        this.locationInfoList = locationInfoList;
    }
}
