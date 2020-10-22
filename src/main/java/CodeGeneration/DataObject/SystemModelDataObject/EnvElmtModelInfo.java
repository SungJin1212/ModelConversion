package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class EnvElmtModelInfo {
    private String EnvElmtName;
    private String Type;
    private String StateMachineName;
    private ArrayList<String> actions;

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

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }
}
