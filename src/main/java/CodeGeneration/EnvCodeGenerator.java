package CodeGeneration;

import CodeGeneration.CodeGenerationLogic.ActionCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.EnvElmtCodeGenerationLogic.EnvElmtModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SMModelCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.EnvElmtModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.strc.ActiveEnvElement;
import kr.ac.kaist.se.model.strc.PassiveEnvElement;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class EnvCodeGenerator {
    public void EnvModelCodeGeneration(MapModelInfo mapModelInfo, ArrayList<EnvElmtModelInfo> envElmtModelInfoList) {
        EnvElmtModelCodeGenerationLogic envElmtModelCodeGenerationLogic = new EnvElmtModelCodeGenerationLogic();
        SMModelCodeGenerationLogic smModelCodeGenerationLogic = new SMModelCodeGenerationLogic();
        ActionCodeGenerationLogic actionCodeGenerationLogic = new ActionCodeGenerationLogic();

        HashMap<String, String> NameToType = new HashMap<>(0);

        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            NameToType.put(locDimensionVar.getVarName().trim(), locDimensionVar.getVarType().trim());
            //System.out.println(locDimensionVar.getVarName() + "   " + locDimensionVar.getVarType());
        }

        for (EnvElmtModelInfo envElmtModelInfo : envElmtModelInfoList) {
            if(envElmtModelInfo.getLocationInfoList() != null) {
                for (LocationInfo locationInfo : envElmtModelInfo.getLocationInfoList()) {
                    locationInfo.setType(NameToType.get(locationInfo.getValName()));
                }
            }
        }

        for (EnvElmtModelInfo envElmtModelInfo : envElmtModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(envElmtModelInfo.getEnvElmtName());

            builder.addModifiers(Modifier.PUBLIC);

            if (envElmtModelInfo.getType().equals("Active")) {
                builder.superclass(ActiveEnvElement.class);
            }
            else if (envElmtModelInfo.getType().equals("Passive")) {
                builder.superclass(PassiveEnvElement.class);
            }



            builder.addFields(smModelCodeGenerationLogic.getFieldsCode(envElmtModelInfo.getSmModelInfo())); // Status, time value
            builder.addMethods(smModelCodeGenerationLogic.getMethodsCode(envElmtModelInfo.getSmModelInfo())); // getStatus, setStatus
            builder.addType(smModelCodeGenerationLogic.getEnumCode(envElmtModelInfo.getSmModelInfo())); // enum (state info)
            //builder.addMethod(smModelCodeGenerationLogic.getDecisionMakingCode(envElmtModelInfo)); // DecisionMaking code

            builder.addFields(envElmtModelCodeGenerationLogic.getSystemEntityModelFieldsCode(envElmtModelInfo, mapModelInfo));
            builder.addMethods(envElmtModelCodeGenerationLogic.getMethods(envElmtModelInfo)); // changeState, selectActions, update
            builder.addMethod(envElmtModelCodeGenerationLogic.getConstructor(mapModelInfo, envElmtModelInfo));




            JavaFile javaFile = JavaFile.builder("CodeGeneration.GeneratedCode.model.EnvModel", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }


            //SMModelCodeGeneration(systemEntityModelInfo.getSmModelInfo(), systemEntityModelInfo.getStateMachineName()); // Call SM Model Generation
            actionCodeGenerationLogic.ActionClassCodeGeneration(envElmtModelInfo.getActionInfoList(), envElmtModelInfo.getEnvElmtName(), 0);

        }

    }



}
