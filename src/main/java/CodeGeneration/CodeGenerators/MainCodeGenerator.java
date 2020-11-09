package CodeGeneration.CodeGenerators;

import CodeGeneration.CodeGenerationLogic.MainCodeGenerationLogic;
import CodeGeneration.DataObject.StructureModelDataObject.InfrastructureModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.OrganizationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ServiceEntityModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainCodeGenerator {

    void MainCodeGeneration(IntegrationModelInfo integrationModelInfo, ArrayList <InfrastructureModelInfo> infrastructureModelInfoList,
                            ArrayList<OrganizationModelInfo> organizationModelInfoList, ArrayList<ServiceEntityModelInfo> serviceEntityModelInfoList,
                            ArrayList<SystemEntityModelInfo> SystemEntityModelInfoList, ArrayList<SystemEntityModelInfo> CSEntityModelInfoList) {

        MainCodeGenerationLogic mainCodeGenerationLogic = new MainCodeGenerationLogic();
        TypeSpec.Builder mainCode = TypeSpec.classBuilder("Main").addModifiers(Modifier.PUBLIC);

        JavaFile javaFile = JavaFile.builder("GeneratedCode", mainCode.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/Main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }
}
