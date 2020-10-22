package CodeGeneration.DataObject.StructureModelDataObject;

import java.util.ArrayList;

public class EnvironmentModelInfo {
    private String EnvName;
    private String StateMachineName;
    private ArrayList<String> EnvElementNames;

    public ArrayList<String> getEnvElementNames() {
        return EnvElementNames;
    }

    public void setEnvElementNames(ArrayList<String> envElementNames) {
        EnvElementNames = envElementNames;
    }

    public String getEnvName() {
        return EnvName;
    }

    public void setEnvName(String envName) {
        EnvName = envName;
    }

    public String getStateMachineName() {
        return StateMachineName;
    }

    public void setStateMachineName(String stateMachineName) {
        StateMachineName = stateMachineName;
    }

}
