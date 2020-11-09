package CodeGeneration.CodeGenerationLogic.EnvElmtCodeGenerationLogic;

import CodeGeneration.CodeGenerationLogic.SMModelCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.EnvElmtModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.State;
import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.strc.Environment;
import kr.ac.kaist.se.model.strc.SoS;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class EnvElmtModelCodeGenerationLogic {

    public MethodSpec getConstructor(MapModelInfo mapModelInfo, EnvElmtModelInfo envElmtModelInfo) {

        String initialStateName = "Dummy";

        for(State state : envElmtModelInfo.getSmModelInfo().getStates()) {
            if (state.getIsInitialState().equals("INITIAL_STATE")) {
                initialStateName = state.getStateName();
                break;
            }
        }

        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(SoS.class, "sos")
                .addParameter(Environment.class, "environment")
                .addParameter(String.class, "name");
                 // TODO: Add Location Info


        builder.addStatement("super(sos, environment)");
        builder.addStatement("this.name=name");

        if (envElmtModelInfo.getLocationInfoList().size() >= 1) { // if locationInfo is exist
            String packageName = "GeneratedCode.geo";
            String objectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
            ClassName objectLocationClassName = ClassName.get(packageName ,objectLocationName);

            String objectLocationInitStr = "";

            for(LocationInfo locationInfo : envElmtModelInfo.getLocationInfoList()) {
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
            builder.addStatement("this.location = new $T($N)", objectLocationClassName, objectLocationInitStr);
        }

        builder.addStatement("setStatus(Status.$N)", initialStateName);

        return builder.build();
    }

    public ArrayList<MethodSpec> getMethods(EnvElmtModelInfo envElmtModelInfo) { // changeState, selectActions, update
        ArrayList<MethodSpec> methods = new ArrayList<>(0);
        SMModelCodeGenerationLogic smModelCodeGenerationLogic = new SMModelCodeGenerationLogic();


        if (envElmtModelInfo.getType().equals("Active")) {

            methods.add(smModelCodeGenerationLogic.getSelectActionsCode(envElmtModelInfo));

//            MethodSpec.Builder selectActionsCode = MethodSpec.methodBuilder("selectActions").addModifiers(Modifier.PROTECTED).returns(TypeName.VOID)
//                    .addAnnotation(Override.class);
//
//            CodeBlock.Builder selectActionsCodeBody = CodeBlock.builder().addStatement("doDecisionMaking()");
//
//            selectActionsCode.addCode(selectActionsCodeBody.build());
//            methods.add(selectActionsCode.build());
        }
        MethodSpec.Builder updateCode = MethodSpec.methodBuilder("update").addModifiers(Modifier.PUBLIC).returns(UpdateResult.class)
                .addParameter(RunResult.class, "runResult").addAnnotation(Override.class);


        CodeBlock.Builder updateCodeBody = CodeBlock.builder()
                .addStatement("UpdateResult updateResult = super.update(runResult)")
                .addStatement("return updateResult");

        updateCode.addCode(updateCodeBody.build());

        methods.add(updateCode.build());

        return methods;
    }

    public ArrayList<FieldSpec> getSystemEntityModelFieldsCode(EnvElmtModelInfo envElmtModelInfo, MapModelInfo mapModelInfo) {

        String packageName = "GeneratedCode.geo";

        ArrayList<FieldSpec> ElmtModelFieldsCode = new ArrayList<>(0);



        for(LocationInfo locationInfo : envElmtModelInfo.getLocationInfoList()) {

            if(locationInfo.getType().toLowerCase().equals("enum")) {
                ClassName field = ClassName.get(packageName, locationInfo.getValName());
                FieldSpec.Builder enumFieldCode = FieldSpec.builder(field, locationInfo.getValName().toLowerCase()).addModifiers(Modifier.PRIVATE);

                ElmtModelFieldsCode.add(enumFieldCode.build());
            }
            else {
                FieldSpec.Builder fieldCode = FieldSpec.builder(TypeVariableName.get(locationInfo.getType()), locationInfo.getValName()).addModifiers(Modifier.PRIVATE);

                ElmtModelFieldsCode.add(fieldCode.build());
            }
        }

        return ElmtModelFieldsCode;
    }
}
