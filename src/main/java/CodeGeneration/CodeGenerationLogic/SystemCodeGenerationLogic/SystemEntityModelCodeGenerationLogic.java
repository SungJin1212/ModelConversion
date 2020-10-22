package CodeGeneration.CodeGenerationLogic.SystemCodeGenerationLogic;

import CodeGeneration.DataObject.SystemModelDataObject.State;
import CodeGeneration.DataObject.SystemModelDataObject.SystemEntityModelInfo;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import kr.ac.kaist.se.model.strc.Organization;
import kr.ac.kaist.se.model.strc.SoS;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class SystemEntityModelCodeGenerationLogic {
    public MethodSpec getConstructor(SystemEntityModelInfo systemEntityModelInfo) {

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

        CodeBlock.Builder constructorBody = CodeBlock.builder()
                .addStatement("super(sos,organization)")
                .addStatement("this.name = name")
                .addStatement("setStatus(Status.$N)", initialStateName);

        builder.addCode(constructorBody.build());

        return builder.build();
    }

    public ArrayList<MethodSpec> getMethods(SystemEntityModelInfo systemEntityModelInfo) { // changeState, selectActions, update
        ArrayList<MethodSpec> methods = new ArrayList<>(0);

        MethodSpec.Builder changeStateCode = MethodSpec.methodBuilder("changeState").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class);

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

        methods.add(changeStateCode.build());
        methods.add(selectActionsCode.build());
        methods.add(updateCode.build());

        return methods;

    }
}
