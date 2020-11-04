package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class EnvElmtModelInfo {
    private String EnvElmtName;
    private String Type; //"Active" or "Passive"
    private String StateMachineName; // SM_OutDoorDust
    private String StateMachineReferenceName; // EnvFactor01_SM
    private ArrayList<ActionInfo> actionInfoList;
    private ArrayList<LocationInfo> locationInfoList;
    private SMModelInfo smModelInfo;

    public String getEnvElmtName() {
        return EnvElmtName;
    }

    public void setEnvElmtName(String envElmtName) {
        EnvElmtName = envElmtName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStateMachineName() {
        return StateMachineName;
    }

    public void setStateMachineName(String stateMachineName) {
        StateMachineName = stateMachineName;
    }

    public String getStateMachineReferenceName() {
        return StateMachineReferenceName;
    }

    public void setStateMachineReferenceName(String stateMachineReferenceName) {
        StateMachineReferenceName = stateMachineReferenceName;
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

    public SMModelInfo getSmModelInfo() {
        return smModelInfo;
    }

    public void setSmModelInfo(SMModelInfo smModelInfo) {
        this.smModelInfo = smModelInfo;
    }
}
