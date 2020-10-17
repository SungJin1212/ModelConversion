package CodeGeneration;

import CodeGeneration.CodeGenerationLogic.IntegrationModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.OrganizationModelCodeGenerationLogic;
import CodeGeneration.DataObject.StructureDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureDataObject.OrganizationModelInfo;
import CodeGeneration.Parser.PreprocessParser;
import CodeGeneration.Parser.StructureParser;

import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.strc.Organization;
import kr.ac.kaist.se.model.strc.SoS;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
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

        IntegrationModelCodeGeneration(integrationModelInfo);
        OrganizationModelCodeGeneration(organizationModelInfoList);
        EnvironmentModelCodeGeneration(environmentModelInfoList);
        //StructureCodeGeneration(integrationModelInfo, organizationModelInfoList, environmentModelInfoList);

    }

    private static void IntegrationModelCodeGeneration(IntegrationModelInfo integrationModelInfo) {
        IntegrationModelCodeGenerationLogic integrationModelCodeGenerationLogic = new IntegrationModelCodeGenerationLogic();

        TypeSpec.Builder builder = TypeSpec.classBuilder(integrationModelInfo.getSoSName());

        MethodSpec constructor = integrationModelCodeGenerationLogic.getConstructor(integrationModelInfo);

        builder.superclass(SoS.class);
        builder.addModifiers(Modifier.PUBLIC);
        builder.addMethod(constructor);
        builder.build();


        JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static void OrganizationModelCodeGeneration(ArrayList <OrganizationModelInfo> organizationModelInfoList) {
        OrganizationModelCodeGenerationLogic organizationModelCodeGenerationLogic = new OrganizationModelCodeGenerationLogic();

        for (OrganizationModelInfo organizationModelInfo : organizationModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(organizationModelInfo.getOrgName());

            MethodSpec constructor = organizationModelCodeGenerationLogic.getConstructor(organizationModelInfo);

            builder.superclass(Organization.class);
            builder.addModifiers(Modifier.PUBLIC);
            builder.addMethod(constructor);
            builder.build();

            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
    private static void EnvironmentModelCodeGeneration(ArrayList <EnvironmentModelInfo> environmentModelInfoList) {

    }


}
