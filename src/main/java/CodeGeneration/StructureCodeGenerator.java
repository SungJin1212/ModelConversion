package CodeGeneration;

import CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic.EnvironmentModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic.InfrastructureModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic.IntegrationModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic.OrganizationModelCodeGenerationLogic;
import CodeGeneration.DataObject.StructureModelDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.InfrastructureModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.OrganizationModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.strc.Environment;
import kr.ac.kaist.se.model.strc.Infrastructure;
import kr.ac.kaist.se.model.strc.Organization;
import kr.ac.kaist.se.model.strc.SoS;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

class StructureCodeGenerator {

    void IntegrationModelCodeGeneration(IntegrationModelInfo integrationModelInfo) {
        IntegrationModelCodeGenerationLogic integrationModelCodeGenerationLogic = new IntegrationModelCodeGenerationLogic();

        TypeSpec.Builder builder = TypeSpec.classBuilder(integrationModelInfo.getSoSName());

        MethodSpec constructor = integrationModelCodeGenerationLogic.getConstructor(integrationModelInfo);

        builder.superclass(SoS.class);
        builder.addModifiers(Modifier.PUBLIC);
        builder.addMethod(constructor);
        builder.build();


        JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.structure", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    void OrganizationModelCodeGeneration(ArrayList <OrganizationModelInfo> organizationModelInfoList) {
        OrganizationModelCodeGenerationLogic organizationModelCodeGenerationLogic = new OrganizationModelCodeGenerationLogic();

        for (OrganizationModelInfo organizationModelInfo : organizationModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(organizationModelInfo.getOrgName());

            MethodSpec constructor = organizationModelCodeGenerationLogic.getConstructor(organizationModelInfo);

            builder.superclass(Organization.class);
            builder.addModifiers(Modifier.PUBLIC);
            builder.addMethod(constructor);
            builder.build();

            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.structure", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
    void EnvironmentModelCodeGeneration(ArrayList <EnvironmentModelInfo> environmentModelInfoList) {
        EnvironmentModelCodeGenerationLogic environmentModelCodeGenerationLogic = new EnvironmentModelCodeGenerationLogic();

        for(EnvironmentModelInfo environmentModelInfo : environmentModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(environmentModelInfo.getEnvName());

            MethodSpec constructor = environmentModelCodeGenerationLogic.getConstructor(environmentModelInfo);
            MethodSpec RunResult = environmentModelCodeGenerationLogic.getRunResult();
            MethodSpec UpdateResult = environmentModelCodeGenerationLogic.getUpdateResult();

            builder.superclass(Environment.class);
            builder.addModifiers(Modifier.PUBLIC);
            builder.addMethod(constructor);
            builder.addMethod(RunResult);
            builder.addMethod(UpdateResult);
            builder.build();

            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.structure", builder.build()).
                    build();

            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    void InfrastructureModelCodeGeneration(ArrayList <InfrastructureModelInfo> infrastructureModelInfoList) {
        InfrastructureModelCodeGenerationLogic infrastructureModelCodeGenerationLogic = new InfrastructureModelCodeGenerationLogic();

        for(InfrastructureModelInfo infrastructureModelInfo : infrastructureModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(infrastructureModelInfo.getInfraName());

            MethodSpec constructor = infrastructureModelCodeGenerationLogic.getConstructor(infrastructureModelInfo);

            builder.superclass(Infrastructure.class);
            builder.addModifiers(Modifier.PUBLIC);
            builder.addMethod(constructor);
            builder.build();

            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.structure", builder.build()).
                    build();

            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }



}
