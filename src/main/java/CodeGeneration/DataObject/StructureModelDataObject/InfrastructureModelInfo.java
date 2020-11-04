package CodeGeneration.DataObject.StructureModelDataObject;

import java.util.ArrayList;

public class InfrastructureModelInfo {
    private String InfraName;
    private String SoSName;
    private ArrayList<String> SystemEntityNames;
    private ArrayList<String> ServiceEntityNames;

    public String getInfraName() {
        return InfraName;
    }

    public void setInfraName(String infraName) {
        InfraName = infraName;
    }

    public String getSoSName() {
        return SoSName;
    }

    public void setSoSName(String soSName) {
        SoSName = soSName;
    }

    public ArrayList<String> getSystemEntityNames() {
        return SystemEntityNames;
    }

    public void setSystemEntityNames(ArrayList<String> systemEntityNames) {
        SystemEntityNames = systemEntityNames;
    }

    public ArrayList<String> getServiceEntityNames() {
        return ServiceEntityNames;
    }

    public void setServiceEntityNames(ArrayList<String> serviceEntityNames) {
        ServiceEntityNames = serviceEntityNames;
    }
}
