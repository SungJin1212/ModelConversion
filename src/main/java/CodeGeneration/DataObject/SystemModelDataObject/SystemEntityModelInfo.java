package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class SystemEntityModelInfo {

    private String SystemEntityName;
    private String StateMachineName;
    private String StateMachineReferenceName;
    private ArrayList<ActionInfo> actionInfoList;
    private SMModelInfo smModelInfo;

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
