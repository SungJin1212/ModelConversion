package CodeGeneration;

import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.InfrastructureModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.OrganizationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import CodeGeneration.Parser.GeoModelParser;
import CodeGeneration.Parser.PreprocessParser;
import CodeGeneration.Parser.StructureModelParser;
import CodeGeneration.Parser.SystemEntityModelParser;

import java.util.ArrayList;

/**
 Structure Model: SoS Integration Model ,SoS Organization Model, SoS Infrastructure Model, Environment Model
 System Model: System Entity Model, State Machine Model, EnvElement Model
 Geo Model: SoS Map Model
 */
public class CodeGenerator {
    public static void main(String[] args) {

        String url = "201022_1627.xml";

        StructureCodeGenerator structureCodeGenerator = new StructureCodeGenerator();
        SystemCodeGenerator systemCodeGenerator = new SystemCodeGenerator();
        GeoModelCodeGenerator geoModelCodeGenerator = new GeoModelCodeGenerator();

        PreprocessParser preprocessParser = new PreprocessParser();
        StructureModelParser structureModelParser = new StructureModelParser();
        GeoModelParser geoModelParser = new GeoModelParser();
        SystemEntityModelParser systemEntityModelParser = new SystemEntityModelParser();


        ArrayList<String> generatedModelNames = preprocessParser.getXMLData(url);

        IntegrationModelInfo integrationModelInfo = new IntegrationModelInfo();
        ArrayList <OrganizationModelInfo> organizationModelInfoList = new ArrayList<>(0);
        ArrayList <EnvironmentModelInfo> environmentModelInfoList = new ArrayList<>(0);
        ArrayList <InfrastructureModelInfo> infrastructureModelInfoList = new ArrayList<>(0);
        ArrayList <MapModelInfo> mapModelInfoList = new ArrayList<>(0);
        ArrayList <SystemEntityModelInfo> SystemEntityModelInfoList = new ArrayList<>(0);


        for(String modelName : generatedModelNames) {
            System.out.println(modelName);
            if (modelName.contains("SoS Integration Model")) {
                integrationModelInfo = structureModelParser.getIntegrationModelInfo(modelName);
            }
            else if (modelName.contains("SoS Organization Model")) {
                organizationModelInfoList.add(structureModelParser.getOrganizationModelInfo(modelName));
            }
            else if (modelName.contains("SoS Infrastructure Model")) {
                infrastructureModelInfoList.add(structureModelParser.getInfrastructureModelInfo(modelName));
            }
            else if (modelName.contains("SoS Environment Model")) {
                environmentModelInfoList.add(structureModelParser.getEnvironmentalModelInfo(modelName));
            }
            else if (modelName.contains("SoS Map Model")) {
                mapModelInfoList.add(geoModelParser.getMapModelInfo(modelName));
            }
            else if(modelName.contains("System") && !modelName.contains("SM")) {
                SystemEntityModelInfoList.add(systemEntityModelParser.getCSModelInfo(modelName));
            }
        }

        structureCodeGenerator.IntegrationModelCodeGeneration(integrationModelInfo);
        structureCodeGenerator.OrganizationModelCodeGeneration(organizationModelInfoList);
        structureCodeGenerator.EnvironmentModelCodeGeneration(environmentModelInfoList);

        structureCodeGenerator.InfrastructureModelCodeGeneration(infrastructureModelInfoList);


        //geoModelCodeGenerator.MapModelCodeGenerator(mapModelInfoList);

        systemCodeGenerator.CSModelCodeGeneration(SystemEntityModelInfoList);
    }
}
