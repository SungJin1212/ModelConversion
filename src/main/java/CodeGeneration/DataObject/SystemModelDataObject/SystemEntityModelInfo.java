package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class SystemEntityModelInfo {

    private String SystemEntityName;
    private String StateMachineName; //SM_****
    private String StateMachineReferenceName; //System**_SM
    private ArrayList<ActionInfo> actionInfoList;
    private ArrayList<LocationInfo> locationInfoList;
    private SMModelInfo smModelInfo;

    public ArrayList<LocationInfo> getLocationInfoList() {
        return locationInfoList;
    }

    public void setLocationInfoList(ArrayList<LocationInfo> locationInfoList) {
        this.locationInfoList = locationInfoList;
    }

    public String getStateMachineReferenceName() {
        return StateMachineReferenceName;
    }

    public void setStateMachineReferenceName(String stateMachineReferenceName) {
        StateMachineReferenceName = stateMachineReferenceName;
    }

    public SMModelInfo getSmModelInfo() {
        return smModelInfo;
    }

    public void setSmModelInfo(SMModelInfo smModelInfo) {
        this.smModelInfo = smModelInfo;
    }

    public String getSystemEntityName() {
        return SystemEntityName;
    }

    public void setSystemEntityName(String systemEntityName) {
        SystemEntityName = systemEntityName;
    }

    public ArrayList<ActionInfo> getActionInfoList() {
        return actionInfoList;
    }

    public void setActionInfoList(ArrayList<ActionInfo> actionInfoList) {
        this.actionInfoList = actionInfoList;
    }

    public String getStateMachineName() {
        return StateMachineName;
    }

    public void setStateMachineName(String stateMachineName) {
        StateMachineName = stateMachineName;
    }
}
