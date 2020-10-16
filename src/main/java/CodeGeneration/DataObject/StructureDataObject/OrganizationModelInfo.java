package CodeGeneration.DataObject.StructureDataObject;

import java.util.ArrayList;

public class OrganizationModelInfo {
    private String OrgName;
    private String SoSName;
    ArrayList<String> SubOrgNames;
    ArrayList<String> CSNames;

    public String getSoSName() {
        return SoSName;
    }

    public void setSoSName(String soSName) {
        SoSName = soSName;
    }



    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public ArrayList<String> getSubOrgNames() {
        return SubOrgNames;
    }

    public void setSubOrgNames(ArrayList<String> subOrgNames) {
        SubOrgNames = subOrgNames;
    }

    public ArrayList<String> getCSNames() {
        return CSNames;
    }

    public void setCSNames(ArrayList<String> CSNames) {
        this.CSNames = CSNames;
    }
}
