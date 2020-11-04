package CodeGeneration.CodeGenerationLogic;

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
                .addAnnotation(Override.class);;
        return builder.build();
    }

    private MethodSpec getUtilityCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("calcUtility").addModifiers(Modifier.PROTECTED).returns(TypeName.FLOAT)
                .addAnnotation(Override.class);;
        CodeBlock utilityValue = CodeBlock.builder().addStatement("return 0").build();
        builder.addCode(utilityValue);
        return builder.build();
    }

    private ArrayList<FieldSpec> getActionFieldsCode(ActionInfo actionInfo, String SystemName, int modelType) {

        String packageName = "";

        if (modelType == 0) {
            packageName = "CodeGeneration.GeneratedCode.model.EnvModel";
        }
        else if (modelType == 1) {
            packageName = "CodeGeneration.GeneratedCode.model.SystemModel";
        }
        else if (modelType == 2) {
            packageName = "CodeGeneration.GeneratedCode.model.ServiceModel";

        }

        ArrayList<FieldSpec> actionFields = new ArrayList<>(0);

        FieldSpec.Builder costField = FieldSpec.builder(TypeName.FLOAT, "cost").addModifiers(Modifier.PRIVATE)
                .initializer("$N", actionInfo.getCost());
        FieldSpec.Builder benefitField = FieldSpec.builder(TypeName.FLOAT, "benefit").addModifiers(Modifier.PRIVATE)
                .initializer("$N", actionInfo.getBenefit());
        FieldSpec.Builder csField = FieldSpec.builder(ClassName.get(packageName,SystemName), "target").addModifiers(Modifier.PRIVATE);


        actionFields.add(costField.build());
        actionFields.add(benefitField.build());
        actionFields.add(csField.build());


        return actionFields;
    }

    public void ActionClassCodeGeneration(ArrayList<ActionInfo> actionInfoList, String SystemName, int modelType) { //modelType 0: SystemModel, 1: EnvModel, 2: ServiceModel

        for(ActionInfo actionInfo : actionInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(SystemName + "_" + actionInfo.getName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(Action.class);
            builder.addFields(getActionFieldsCode(actionInfo, SystemName, modelType));
            builder.addMethod(getUtilityCode(actionInfo));
            builder.addMethod(getExecuteCode(actionInfo));
            builder.addMethod(getCheckPreconditionCode(actionInfo));


            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.Behavior", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }
}
