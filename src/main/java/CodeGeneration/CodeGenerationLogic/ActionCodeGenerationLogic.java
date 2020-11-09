package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ActionInfo;
import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.behv.Action;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ActionCodeGenerationLogic {

    private MethodSpec getCheckPreconditionCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("checkPrecondition").addModifiers(Modifier.PROTECTED).returns(TypeName.BOOLEAN)
                .addAnnotation(Override.class);
        CodeBlock preConditionCode = CodeBlock.builder().addStatement("return true").build(); // TODO: addStatement(actionInfo.getPrecondition()).
        builder.addCode(preConditionCode);
        return builder.build();
    }


    private MethodSpec getExecuteCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("executeAction").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class);
        ;
        return builder.build();
    }

    private MethodSpec getUtilityCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("calcUtility").addModifiers(Modifier.PROTECTED).returns(TypeName.FLOAT)
                .addAnnotation(Override.class);

        CodeBlock utilityValue = CodeBlock.builder().addStatement("return 0").build();
        builder.addCode(utilityValue);
        return builder.build();
    }

    private ArrayList<FieldSpec> getActionFieldsCode(ActionInfo actionInfo, String SystemName, int modelType, String soSName) { // ex) SystemName = sweepingRobot

        String packageName = "";
        String sosPackageName = "GeneratedCode.structure";
        if (modelType == 0) {
            packageName = "GeneratedCode.EnvModel";
        } else if (modelType == 1) {
            packageName = "GeneratedCode.SystemModel";
        } else if (modelType == 2) {
            packageName = "GeneratedCode.ServiceModel";

        }

        ArrayList<FieldSpec> actionFields = new ArrayList<>(0);

        FieldSpec.Builder costField = FieldSpec.builder(TypeName.FLOAT, "cost").addModifiers(Modifier.PRIVATE)
                .initializer("$N", actionInfo.getCost());
        FieldSpec.Builder benefitField = FieldSpec.builder(TypeName.FLOAT, "benefit").addModifiers(Modifier.PRIVATE)
                .initializer("$N", actionInfo.getBenefit());
        FieldSpec.Builder csField = FieldSpec.builder(ClassName.get(packageName, SystemName), "target").addModifiers(Modifier.PRIVATE);

        FieldSpec.Builder soSField = FieldSpec.builder(ClassName.get(sosPackageName, soSName), getFieldString(soSName)).addModifiers(Modifier.PRIVATE);

        actionFields.add(costField.build());
        actionFields.add(benefitField.build());
        actionFields.add(csField.build());
        actionFields.add(soSField.build());

        return actionFields;
    }

    public void ActionClassCodeGeneration(ArrayList<ActionInfo> actionInfoList, String SystemName, IntegrationModelInfo integrationModelInfo, int modelType) { //modelType 0: SystemModel, 1: EnvModel, 2: ServiceModel

        for (ActionInfo actionInfo : actionInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(SystemName + "_" + actionInfo.getName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(Action.class);
            builder.addFields(getActionFieldsCode(actionInfo, SystemName, modelType, integrationModelInfo.getSoSName()));
            builder.addMethod(getConstructorCode(SystemName,modelType,integrationModelInfo.getSoSName()));
            builder.addMethod(getUtilityCode(actionInfo));
            builder.addMethod(getExecuteCode(actionInfo));
            builder.addMethod(getCheckPreconditionCode(actionInfo));



            JavaFile javaFile = JavaFile.builder("GeneratedCode.Behavior", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/Main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    private MethodSpec getConstructorCode(String systemName, int modelType, String soSName) {
        String packageName = "";
        String sosPackageName = "GeneratedCode.structure";
        if (modelType == 0) {
            packageName = "GeneratedCode.EnvModel";
        }
        else if (modelType == 1) {
            packageName = "GeneratedCode.SystemModel";
        }
        else if (modelType == 2) {
            packageName = "GeneratedCode.ServiceModel";
        }

        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(ClassName.get(packageName, systemName), getFieldString(systemName)).addParameter(ClassName.get(sosPackageName, soSName), getFieldString(soSName));

        builder.addStatement("this.target = $N", getFieldString(systemName));
        builder.addStatement("this.$N = $N", getFieldString(soSName), getFieldString(soSName));

        return builder.build();
    }

    private String getFieldString(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}

