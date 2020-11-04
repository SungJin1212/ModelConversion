package CodeGeneration;

import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.InfrastructureModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.OrganizationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.EnvElmtModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ServiceEntityModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import CodeGeneration.Parser.*;

import java.util.ArrayList;

/**
 Structure Model: SoS Integration Model, SoS Organization Model (including Sub_Org.), SoS Infrastructure Model, Environment Model
 System Model: System Entity Model (including State Machine Model)
 Environment Model: EnvElement Model
 Geo Model: SoS Map Model (SoSMap, ObjectLocation, LocationInformation, enumVariables)
 */
public class CodeGenerator {
    public static void main(String[] args) {

        String url = "201104_1302.xml";

        PreprocessParser preprocessParser = new PreprocessParser();
        StructureModelParser structureModelParser = new StructureModelParser();
        GeoModelParser geoModelParser = new GeoModelParser();
        SystemEntityModelParser systemEntityModelParser = new SystemEntityModelParser();
        EnvFactorModelParser envFactorModelParser = new EnvFactorModelParser();
        ServiceEntityModelParser serviceEntityModelParser = new ServiceEntityModelParser();

        ArrayList<String> generatedModelNames = preprocessParser.getXMLData(url);

        IntegrationModelInfo integrationModelInfo = new IntegrationModelInfo();
        ArrayList <OrganizationModelInfo> organizationModelInfoList = new ArrayList<>(0);
        ArrayList <EnvironmentModelInfo> environmentModelInfoList = new ArrayList<>(0);
        ArrayList <InfrastructureModelInfo> infrastructureModelInfoList = new ArrayList<>(0);
        MapModelInfo mapModelInfo = new MapModelInfo();
        ArrayList <SystemEntityModelInfo> CSModelInfoList = new ArrayList<>(0);
        ArrayList <SystemEntityModelInfo> systemEntityModelInfoList = new ArrayList<>(0);
        ArrayList <EnvElmtModelInfo> envElmtModelInfoList = new ArrayList<>(0);
        ArrayList <ServiceEntityModelInfo> serviceEntityModelInfoList = new ArrayList<>(0);

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
                mapModelInfo = geoModelParser.getMapModelInfo(modelName);
            }
            else if(modelName.contains("System") && !modelName.contains("SM") && !modelName.contains("Infra")) { // get CS model Info
                CSModelInfoList.add(systemEntityModelParser.getCSModelInfo(modelName));
            }
            else if(modelName.contains("EnvFactor") && !modelName.contains("SM")) {
                envElmtModelInfoList.add(envFactorModelParser.getEnvElmtModelInfo(modelName));
            }
            else if(modelName.contains("InfraSystem") && !modelName.contains("SM")) { // get SystemEntity model Info
                systemEntityModelInfoList.add(systemEntityModelParser.getCSModelInfo(modelName));
            }
            else if(modelName.contains("Service")) {
                serviceEntityModelInfoList.add(serviceEntityModelParser.getServiceEntityModelInfo(modelName));
            }
        }

        StructureCodeGenerator structureCodeGenerator = new StructureCodeGenerator();
        CSCodeGenerator CSCodeGenerator = new CSCodeGenerator();
        GeoCodeGenerator geoCodeGenerator = new GeoCodeGenerator();
        EnvCodeGenerator envCodeGenerator = new EnvCodeGenerator();
        SystemEntityCodeGenerator systemEntityCodeGenerator = new SystemEntityCodeGenerator();
        ServiceEntityCodeGenerator serviceEntityCodeGenerator = new ServiceEntityCodeGenerator();

        structureCodeGenerator.IntegrationModelCodeGeneration(integrationModelInfo);
        structureCodeGenerator.OrganizationModelCodeGeneration(organizationModelInfoList);
        structureCodeGenerator.EnvironmentModelCodeGeneration(environmentModelInfoList);
        structureCodeGenerator.InfrastructureModelCodeGeneration(infrastructureModelInfoList);

        geoCodeGenerator.LocationInformationCodeGeneration(mapModelInfo);
        geoCodeGenerator.EnumCodeGeneration(mapModelInfo);
        geoCodeGenerator.MapCodeGeneration(mapModelInfo);
        geoCodeGenerator.ObjectLocationCodeGeneration(mapModelInfo);
        //geoModelCodeGenerator.MapModelCodeGeneration(mapModelInfoList);

        CSCodeGenerator.CSModelCodeGeneration(mapModelInfo, CSModelInfoList);
        systemEntityCodeGenerator.SystemEntityCodeGeneration(mapModelInfo, systemEntityModelInfoList);
        serviceEntityCodeGenerator.ServiceEntityCodeGeneration(mapModelInfo, serviceEntityModelInfoList);

        envCodeGenerator.EnvModelCodeGeneration(mapModelInfo, envElmtModelInfoList);
    }
}
