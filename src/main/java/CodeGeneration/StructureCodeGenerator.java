package CodeGeneration;

import CodeGeneration.DataObject.StructureDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureDataObject.OrganizationModelInfo;
import CodeGeneration.Parser.PreprocessParser;
import CodeGeneration.Parser.StructureParser;

import java.util.ArrayList;

public class StructureCodeGenerator {

    public static void main(String[] args) {
        StructureParser structureParser = new StructureParser();
        PreprocessParser preprocessParser = new PreprocessParser();
        String url = "1917.xml";


        ArrayList<String> generatedModelNames = preprocessParser.getXMLData(url);

        IntegrationModelInfo integrationModelInfo = new IntegrationModelInfo();
        ArrayList <OrganizationModelInfo> organizationModelInfoList = new ArrayList<>(0);
        ArrayList <EnvironmentModelInfo> environmentModelInfoList = new ArrayList<>(0);


        for(String modelName : generatedModelNames) {
            System.out.println(modelName);
            if (modelName.contains("SoS Integration Model")) {
                integrationModelInfo = structureParser.getIntegrationModelInfo(modelName);
            }
            else if (modelName.contains("SoS Organization Model")) {
                organizationModelInfoList.add(structureParser.getOrganizationModelInfo(modelName));
            }
            else if (modelName.contains("SoS Infrastructure Model")) {

            }
            else if (modelName.contains("SoS Environment Model")) {
                environmentModelInfoList.add(structureParser.getEnvironmentalModelInfo(modelName));
            }
            else if (modelName.contains("SoS Map Model")) {

            }
        }

        //IntegrationModelInfo integrationModelInfo = structureParser.getIntegrationModelInfo(url);
        //ArrayList <OrganizationModelInfo> organizationModelInfos = structureParser.getOrganizationModelInfo(url);
        //ArrayList <EnvironmentModelInfo> environmentModelInfos = structureParser.getEnvironmentalModelInfo(url);

        StructureCodeGeneration(integrationModelInfo, organizationModelInfoList, environmentModelInfoList);

        

    }

    private static void StructureCodeGeneration(IntegrationModelInfo integrationModelInfo, ArrayList<OrganizationModelInfo> organizationModelInfos, ArrayList<EnvironmentModelInfo> environmentModelInfos) {

    }


}
