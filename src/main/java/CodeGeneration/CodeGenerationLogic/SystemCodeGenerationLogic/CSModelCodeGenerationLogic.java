package CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.State;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.strc.Organization;
import kr.ac.kaist.se.model.strc.SoS;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class CSModelCodeGenerationLogic {
    public MethodSpec getConstructor(MapModelInfo mapModelInfo, SystemEntityModelInfo systemEntityModelInfo) {

        String packageName = "GeneratedCode.geo";
        String objectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
        ClassName objectLocationClassName = ClassName.get(packageName ,objectLocationName);
        String initialStateName = "Dummy";

        for(State state : systemEntityModelInfo.getSmModelInfo().getStates()) {
            if (state.getIsInitialState().equals("INITIAL_STATE")) {
                initialStateName = state.getStateName();
                break;
            }
        }

        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(SoS.class, "sos").addParameter(Organization.class, "organization")
                .addParameter(String.class, "name"); // TODO: Add Location Info

        builder.addStatement("super(sos,organization)");
        String objectLocationInitStr = "";

        for(LocationInfo locationInfo : systemEntityModelInfo.getLocationInfoList()) {
            if (locationInfo.getType().toLowerCase().equals("enum")) {
                ClassName className = ClassName.get(packageName, locationInfo.getValName());
                builder.addStatement("$N = $T.$N", locationInfo.getValName().toLowerCase(), className, locationInfo.getValue());

            }
            else {
                builder.addStatement("$N = $N", locationInfo.getValName(), locationInfo.getValue());

            }
        }
        int index = 0;
        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if(locDimensionVar.getVarType().toLowerCase().equals("enum")) {
                if (index == 0) {
                    objectLocationInitStr += locDimensionVar.getVarName().toLowerCase();
                }
                else {
                    objectLocationInitStr += "," + locDimensionVar.getVarName().toLowerCase();
                }
            }
            else {
                if (index == 0) {
                    objectLocationInitStr += locDimensionVar.getVarName();
                }
                else {
                    objectLocationInitStr += "," + locDimensionVar.getVarName();
                }
            }
            index++;
        }

        if (systemEntityModelInfo.getLocationInfoList().size() != 0) {
            builder.addStatement("this.location = new $T($N)", objectLocationClassName, objectLocationInitStr);
        }
        builder.addStatement("this.name = name");
        builder.addStatement("setStatus(Status.$N)", initialStateName);

//        CodeBlock.Builder constructorBody = CodeBlock.builder()
//                //.addStatement("super(sos,organization)")
//                .addStatement("this.location = new $T($N)", objectLocationClassName, objectLocationInitStr)
//                .addStatement("this.name = name")
//                .addStatement("setStatus(Status.$N)", initialStateName);
//        builder.addCode(constructorBody.build());

        return builder.build();
    }

    public ArrayList<MethodSpec> getMethods(SystemEntityModelInfo systemEntityModelInfo) { // changeState, selectActions, update
        ArrayList<MethodSpec> methods = new ArrayList<>(0);

//        MethodSpec.Builder changeStateCode = MethodSpec.methodBuilder("changeState").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
//                .addAnnotation(Override.class);

        MethodSpec.Builder selectActionsCode = MethodSpec.methodBuilder("selectActions").addModifiers(Modifier.PROTECTED).returns(TypeName.VOID)
                .addAnnotation(Override.class);

        CodeBlock.Builder selectActionsCodeBody = CodeBlock.builder().addStatement("doDecisionMaking()");

        selectActionsCode.addCode(selectActionsCodeBody.build());

        MethodSpec.Builder updateCode = MethodSpec.methodBuilder("update").addModifiers(Modifier.PUBLIC).returns(UpdateResult.class)
                .addParameter(RunResult.class, "runResult").addAnnotation(Override.class);


        CodeBlock.Builder updateCodeBody = CodeBlock.builder()
                .addStatement("UpdateResult updateResult = super.update(runResult)")
                .addStatement("return updateResult");

        updateCode.addCode(updateCodeBody.build());

//        methods.add(changeStateCode.build());
        methods.add(selectActionsCode.build());
        methods.add(updateCode.build());

        return methods;
    }

    public ArrayList<FieldSpec> getSystemEntityModelFieldsCode(SystemEntityModelInfo systemEntityModelInfo, MapModelInfo mapModelInfo) {

        String packageName = "GeneratedCode.geo";

        ArrayList<FieldSpec> SystemEntityModelFieldsCode = new ArrayList<>(0);



        for(LocationInfo locationInfo : systemEntityModelInfo.getLocationInfoList()) {

            if(locationInfo.getType().toLowerCase().equals("enum")) {
                ClassName field = ClassName.get(packageName, locationInfo.getValName());
                FieldSpec.Builder enumFieldCode = FieldSpec.builder(field, locationInfo.getValName().toLowerCase()).addModifiers(Modifier.PRIVATE);

                SystemEntityModelFieldsCode.add(enumFieldCode.build());
            }
            else {
                FieldSpec.Builder fieldCode = FieldSpec.builder(TypeVariableName.get(locationInfo.getType()), locationInfo.getValName()).addModifiers(Modifier.PRIVATE);

                SystemEntityModelFieldsCode.add(fieldCode.build());
            }
        }

        return SystemEntityModelFieldsCode;
    }
}
