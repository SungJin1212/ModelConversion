package CodeGeneration;

import CodeGeneration.CodeGenerationLogic.ActionCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic.SMModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic.SystemEntityModelCodeGenerationLogic;
import CodeGeneration.DataObject.SystemModelDataObject.ActionInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SMModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.behv.Action;
import kr.ac.kaist.se.model.strc.CS;


import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 SystemEntityCode, SMModelCode, ActionCode corresponding to the SystemEntityCode
 */

class SystemCodeGenerator {
    void CSModelCodeGeneration(ArrayList<SystemEntityModelInfo> systemEntityModelInfoList) {
        SystemEntityModelCodeGenerationLogic systemEntityModelCodeGenerationLogic = new SystemEntityModelCodeGenerationLogic();
        SMModelCodeGenerationLogic smModelCodeGenerationLogic = new SMModelCodeGenerationLogic();

        for (SystemEntityModelInfo systemEntityModelInfo : systemEntityModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(systemEntityModelInfo.getSystemEntityName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(CS.class);

            builder.addFields(smModelCodeGenerationLogic.getFieldsCode(systemEntityModelInfo.getSmModelInfo())); // Status, time value
            builder.addMethods(smModelCodeGenerationLogic.getMethodsCode(systemEntityModelInfo.getSmModelInfo())); // getStatus, setStatus
            builder.addType(smModelCodeGenerationLogic.getEnumCode(systemEntityModelInfo.getSmModelInfo())); // enum (state info)
            builder.addMethod(smModelCodeGenerationLogic.getDecisionMakingCode(systemEntityModelInfo)); // DecisionMaking code

            builder.addMethods(systemEntityModelCodeGenerationLogic.getMethods(systemEntityModelInfo)); // changeState, selectActions, update
            builder.addMethod(systemEntityModelCodeGenerationLogic.getConstructor(systemEntityModelInfo));




            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.SystemModel", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }


            //SMModelCodeGeneration(systemEntityModelInfo.getSmModelInfo(), systemEntityModelInfo.getStateMachineName()); // Call SM Model Generation
            ActionClassCodeGeneration(systemEntityModelInfo.getActionInfoList(), systemEntityModelInfo.getSystemEntityName());

        }
    }

    private void ActionClassCodeGeneration(ArrayList<ActionInfo> actionInfoList, String SystemName) {


        ActionCodeGenerationLogic actionCodeGenerationLogic = new ActionCodeGenerationLogic();

        for(ActionInfo actionInfo : actionInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(SystemName + "_" + actionInfo.getName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(Action.class);
            builder.addFields(actionCodeGenerationLogic.getActionFieldsCode(actionInfo, SystemName));
            builder.addMethod(actionCodeGenerationLogic.getUtilityCode(actionInfo));
            builder.addMethod(actionCodeGenerationLogic.getExecuteCode(actionInfo));
            builder.addMethod(actionCodeGenerationLogic.getCheckPreconditionCode(actionInfo));


            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.Behavior", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }

    void SMModelCodeGeneration(SMModelInfo smModelInfo, String SMName) {
        SystemEntityModelCodeGenerationLogic systemEntityModelCodeGenerationLogic = new SystemEntityModelCodeGenerationLogic();

        TypeSpec.Builder builder = TypeSpec.classBuilder(SMName);

        builder.addModifiers(Modifier.PUBLIC);



        JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.SystemModel", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
