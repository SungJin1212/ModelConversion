package CodeGeneration.DataObject.StructureDataObject;

import java.util.ArrayList;

public class IntegrationModelInfo {
    private String SoSName;
    private String StateMachineName;
    ArrayList <String> OrgNames;
    ArrayList <String> InfraName;
    ArrayList <String> EnvNames;
    ArrayList <String> CSs;
    ArrayList <String> MapNames;


    public ArrayList<String> getCSs() {
        return CSs;
    }

    public void setCSs(ArrayList<String> CSs) {
        this.CSs = CSs;
    }



    public String getSoSName() {
        return SoSName;
    }

    public void setSoSName(String soSName) {
        SoSName = soSName;
    }

    public ArrayList<String> getOrgNames() {
        return OrgNames;
    }

    public void setOrgNames(ArrayList<String> orgNames) {
        OrgNames = orgNames;
    }

    public ArrayList<String> getInfraName() {
        return InfraName;
    }

    public void setInfraName(ArrayList<String> infraName) {
        InfraName = infraName;
    }

    public ArrayList<String> getEnvNames() {
        return EnvNames;
    }

    public void setEnvNames(ArrayList<String> envNames) {
        EnvNames = envNames;
    }

    public ArrayList<String> getMapNames() {
        return MapNames;
    }

    public void setMapNames(ArrayList<String> mapNames) {
        MapNames = mapNames;
    }

    public String getStateMachineName() {
        return StateMachineName;
    }

    public void setStateMachineName(String stateMachineName) {
        StateMachineName = stateMachineName;
    }
}
