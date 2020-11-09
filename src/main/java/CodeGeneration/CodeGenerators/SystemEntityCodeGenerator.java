package CodeGeneration.CodeGenerators;

import CodeGeneration.CodeGenerationLogic.ActionCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SMModelCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic.SystemEntityModelCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.strc.SystemEntity;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemEntityCodeGenerator {
    public void SystemEntityCodeGeneration(MapModelInfo mapModelInfo, ArrayList<SystemEntityModelInfo> SystemEntityModelInfoList, IntegrationModelInfo integrationModelInfo) {
        SystemEntityModelCodeGenerationLogic systemEntityModelCodeGenerationLogic = new SystemEntityModelCodeGenerationLogic();
        SMModelCodeGenerationLogic smModelCodeGenerationLogic = new SMModelCodeGenerationLogic();
        ActionCodeGenerationLogic actionCodeGenerationLogic = new ActionCodeGenerationLogic();

        HashMap<String, String> NameToType = new HashMap<>(0);

        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            NameToType.put(locDimensionVar.getVarName().trim(), locDimensionVar.getVarType().trim());
            //System.out.println(locDimensionVar.getVarName() + "   " + locDimensionVar.getVarType());
        }
        for (SystemEntityModelInfo systemEntityModelInfo : SystemEntityModelInfoList) {
            for (LocationInfo locationInfo : systemEntityModelInfo.getLocationInfoList()) {
                locationInfo.setType(NameToType.get(locationInfo.getValName()));
            }
        }

        for (SystemEntityModelInfo systemEntityModelInfo : SystemEntityModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(systemEntityModelInfo.getSystemEntityName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(SystemEntity.class);

            builder.addFields(smModelCodeGenerationLogic.getFieldsCode(systemEntityModelInfo.getSmModelInfo())); // Status, time value
            builder.addMethods(smModelCodeGenerationLogic.getMethodsCode(systemEntityModelInfo.getSmModelInfo())); // getStatus, setStatus
            builder.addType(smModelCodeGenerationLogic.getEnumCode(systemEntityModelInfo.getSmModelInfo())); // enum (state info)
            builder.addMethod(smModelCodeGenerationLogic.getDecisionMakingCode(systemEntityModelInfo)); // DecisionMaking code

            builder.addFields(systemEntityModelCodeGenerationLogic.getSystemEntityModelFieldsCode(systemEntityModelInfo, mapModelInfo));
            builder.addMethods(systemEntityModelCodeGenerationLogic.getMethods(systemEntityModelInfo)); // changeState, selectActions, update
            builder.addMethod(systemEntityModelCodeGenerationLogic.getConstructor(mapModelInfo, systemEntityModelInfo));




            JavaFile javaFile = JavaFile.builder("GeneratedCode.SystemModel", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/Main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }


            //SMModelCodeGeneration(systemEntityModelInfo.getSmModelInfo(), systemEntityModelInfo.getStateMachineName()); // Call SM Model Generation
            actionCodeGenerationLogic.ActionClassCodeGeneration(systemEntityModelInfo.getActionInfoList(), systemEntityModelInfo.getSystemEntityName(),integrationModelInfo, 1);

        }
    }


}
