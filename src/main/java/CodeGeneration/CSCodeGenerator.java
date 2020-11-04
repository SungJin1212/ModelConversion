package CodeGeneration;

import CodeGeneration.CodeGenerationLogic.ActionCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SMModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic.CSModelCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SMModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.strc.CS;


import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 SystemEntityCode, SMModelCode, ActionCode corresponding to the SystemEntityCode
 */

class CSCodeGenerator {

    public void CSModelCodeGeneration(MapModelInfo mapModelInfo, ArrayList<SystemEntityModelInfo> systemEntityModelInfoList) {
        CSModelCodeGenerationLogic CSModelCodeGenerationLogic = new CSModelCodeGenerationLogic();
        SMModelCodeGenerationLogic smModelCodeGenerationLogic = new SMModelCodeGenerationLogic();
        ActionCodeGenerationLogic actionCodeGenerationLogic = new ActionCodeGenerationLogic();

        HashMap<String, String> NameToType = new HashMap<>(0);

        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            NameToType.put(locDimensionVar.getVarName().trim(), locDimensionVar.getVarType().trim());
            //System.out.println(locDimensionVar.getVarName() + "   " + locDimensionVar.getVarType());
        }
        for (SystemEntityModelInfo systemEntityModelInfo : systemEntityModelInfoList) {
            for (LocationInfo locationInfo : systemEntityModelInfo.getLocationInfoList()) {
                locationInfo.setType(NameToType.get(locationInfo.getValName()));
            }
        }

        for (SystemEntityModelInfo systemEntityModelInfo : systemEntityModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(systemEntityModelInfo.getSystemEntityName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(CS.class);

            builder.addFields(smModelCodeGenerationLogic.getFieldsCode(systemEntityModelInfo.getSmModelInfo())); // Status, time value
            builder.addMethods(smModelCodeGenerationLogic.getMethodsCode(systemEntityModelInfo.getSmModelInfo())); // getStatus, setStatus
            builder.addType(smModelCodeGenerationLogic.getEnumCode(systemEntityModelInfo.getSmModelInfo())); // enum (state info)
            builder.addMethod(smModelCodeGenerationLogic.getDecisionMakingCode(systemEntityModelInfo)); // DecisionMaking code

            builder.addFields(CSModelCodeGenerationLogic.getSystemEntityModelFieldsCode(systemEntityModelInfo, mapModelInfo));
            builder.addMethods(CSModelCodeGenerationLogic.getMethods(systemEntityModelInfo)); // changeState, selectActions, update
            builder.addMethod(CSModelCodeGenerationLogic.getConstructor(mapModelInfo, systemEntityModelInfo));




            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.SystemModel", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }


            //SMModelCodeGeneration(systemEntityModelInfo.getSmModelInfo(), systemEntityModelInfo.getStateMachineName()); // Call SM Model Generation
            actionCodeGenerationLogic.ActionClassCodeGeneration(systemEntityModelInfo.getActionInfoList(), systemEntityModelInfo.getSystemEntityName(), 1);

        }
    }



    void SMModelCodeGeneration(SMModelInfo smModelInfo, String SMName) {
        CSModelCodeGenerationLogic CSModelCodeGenerationLogic = new CSModelCodeGenerationLogic();

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
