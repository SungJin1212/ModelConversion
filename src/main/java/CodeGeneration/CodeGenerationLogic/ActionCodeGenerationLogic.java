package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.SystemModelDataObject.ActionInfo;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class ActionCodeGenerationLogic {

    public MethodSpec getCheckPreconditionCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("checkPrecondition").addModifiers(Modifier.PROTECTED).returns(TypeName.BOOLEAN)
                .addAnnotation(Override.class);
        CodeBlock preConditionCode = CodeBlock.builder().addStatement("return true").build(); // TODO: addStatement(actionInfo.getPrecondition()).
        builder.addCode(preConditionCode);
        return builder.build();
    }

    public MethodSpec getExecuteCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("executeAction").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class);;
        return builder.build();
    }

    public MethodSpec getUtilityCode(ActionInfo actionInfo) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("calcUtility").addModifiers(Modifier.PROTECTED).returns(TypeName.FLOAT)
                .addAnnotation(Override.class);;
        CodeBlock utilityValue = CodeBlock.builder().addStatement("return 0").build();
        builder.addCode(utilityValue);
        return builder.build();
    }

    public ArrayList<FieldSpec> getActionFieldsCode(ActionInfo actionInfo, String SystemName) {
        String packageName = "CodeGeneration.GeneratedCode.model.SystemModel"; // TODO: Configure Path

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
}
