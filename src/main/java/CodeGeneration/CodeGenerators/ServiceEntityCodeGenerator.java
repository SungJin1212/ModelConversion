package CodeGeneration.CodeGenerators;

import CodeGeneration.CodeGenerationLogic.ActionCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.ServiceEntityCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ServiceEntityModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.strc.ServiceEntity;
import kr.ac.kaist.se.model.strc.SystemEntity;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceEntityCodeGenerator {
    public void ServiceEntityCodeGeneration(MapModelInfo mapModelInfo, ArrayList<ServiceEntityModelInfo> serviceEntityModelInfoList, IntegrationModelInfo integrationModelInfo) {
        ServiceEntityCodeGenerationLogic serviceEntityCodeGenerationLogic = new ServiceEntityCodeGenerationLogic();
        ActionCodeGenerationLogic actionCodeGenerationLogic = new ActionCodeGenerationLogic();

        HashMap<String, String> NameToType = new HashMap<>(0);

        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            NameToType.put(locDimensionVar.getVarName().trim(), locDimensionVar.getVarType().trim());
            //System.out.println(locDimensionVar.getVarName() + "   " + locDimensionVar.getVarType());
        }
        for (ServiceEntityModelInfo serviceEntityModelInfo : serviceEntityModelInfoList) {
            for (LocationInfo locationInfo : serviceEntityModelInfo.getLocationInfoList()) {
                locationInfo.setType(NameToType.get(locationInfo.getValName()));
            }
        }

        for (ServiceEntityModelInfo serviceEntityModelInfo : serviceEntityModelInfoList) {
            TypeSpec.Builder builder = TypeSpec.classBuilder(serviceEntityModelInfo.getServiceEntityName());

            builder.addModifiers(Modifier.PUBLIC);
            builder.superclass(ServiceEntity.class);

            builder.addMethod(serviceEntityCodeGenerationLogic.getConstructor(mapModelInfo, serviceEntityModelInfo));
            builder.addMethods(serviceEntityCodeGenerationLogic.getMethods(serviceEntityModelInfo));

            JavaFile javaFile = JavaFile.builder("GeneratedCode.ServiceModel", builder.build()).
                    build();
            try {
                javaFile.writeTo(Paths.get("./src/Main/java"));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }


            //SMModelCodeGeneration(systemEntityModelInfo.getSmModelInfo(), systemEntityModelInfo.getStateMachineName()); // Call SM Model Generation
            actionCodeGenerationLogic.ActionClassCodeGeneration(serviceEntityModelInfo.getActionInfoList(), serviceEntityModelInfo.getServiceEntityName(),integrationModelInfo, 2);

        }
    }
}
