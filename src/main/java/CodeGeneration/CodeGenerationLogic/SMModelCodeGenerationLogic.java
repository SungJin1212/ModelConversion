package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.SystemModelDataObject.*;
import com.squareup.javapoet.*;
import com.sun.org.apache.bcel.internal.generic.PUSH;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class SMModelCodeGenerationLogic {
    public ArrayList<FieldSpec> getFieldsCode(SMModelInfo smModelInfo) {
        ArrayList<FieldSpec> fields = new ArrayList<>(0);

        FieldSpec.Builder statusField = FieldSpec.builder(TypeVariableName.get("Status"), "status").addModifiers(Modifier.PRIVATE);

        for(State state : smModelInfo.getStates()) {
            FieldSpec.Builder timeField = FieldSpec.builder(TypeName.INT, state.getStateName() + "Time").addModifiers(Modifier.PRIVATE)
            .initializer("$N", state.getTime());
            fields.add(timeField.build());
        }

        fields.add(statusField.build());

        return fields;

    }

    public ArrayList<MethodSpec> getMethodsCode(SMModelInfo smModelInfo) { // getStatus, setStatus
        ArrayList<MethodSpec> methods = new ArrayList<>(0);

        MethodSpec.Builder getterCode = MethodSpec.methodBuilder("getStatus").addModifiers(Modifier.PUBLIC).returns(TypeVariableName.get("Status"));
        CodeBlock getterCodeBody = CodeBlock.builder().addStatement("return status").build();
        getterCode.addCode(getterCodeBody);

        MethodSpec.Builder setterCode = MethodSpec.methodBuilder("setStatus").addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("Status"), "aStatus");
        CodeBlock setterCodeBody = CodeBlock.builder().addStatement("status = aStatus").build();
        setterCode.addCode(setterCodeBody);

        methods.add(getterCode.build());
        methods.add(setterCode.build());

        return methods;

    }

    public TypeSpec getEnumCode(SMModelInfo smModelInfo) {

        TypeSpec.Builder builder = TypeSpec.enumBuilder("Status").addModifiers(Modifier.PUBLIC);
        for (State state : smModelInfo.getStates()) {
            builder.addEnumConstant(state.getStateName());
        }
        return builder.build();
    }

    public MethodSpec getSelectActionsCode(EnvElmtModelInfo envElmtModelInfo) {

        MethodSpec.Builder decisionMakingCode = MethodSpec.methodBuilder("selectActions").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class);

        CodeBlock.Builder decisionMakingBody = CodeBlock.builder();

        decisionMakingBody.addStatement("Status aStatus = status");
        decisionMakingBody.beginControlFlow("switch(aStatus)");

        for(State state : envElmtModelInfo.getSmModelInfo().getStates()) {
            decisionMakingBody.add(getStateTransitionCode(state,envElmtModelInfo.getEnvElmtName()));
        }
        decisionMakingBody.endControlFlow();

        decisionMakingCode.addCode(decisionMakingBody.build());

        return decisionMakingCode.build();
    }


    public MethodSpec getDecisionMakingCode(SystemEntityModelInfo systemEntityModelInfo) {

        MethodSpec.Builder decisionMakingCode = MethodSpec.methodBuilder("doDecisionMaking").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class);

        CodeBlock.Builder decisionMakingBody = CodeBlock.builder();

        decisionMakingBody.addStatement("Status aStatus = status");
        decisionMakingBody.beginControlFlow("switch(aStatus)");

        for(State state : systemEntityModelInfo.getSmModelInfo().getStates()) {
            decisionMakingBody.add(getStateTransitionCode(state,systemEntityModelInfo.getSystemEntityName()));
        }
        decisionMakingBody.endControlFlow();

        decisionMakingCode.addCode(decisionMakingBody.build());

        return decisionMakingCode.build();
    }

    private CodeBlock getStateTransitionCode(State state, String systemEntityName) {
        String packageName = "GeneratedCode.Behavior"; // TODO: Configure Path

        String timeValue = state.getStateName() + "Time";
        String probabilityList = getProbabilityList(state);
        CodeBlock.Builder stateTransitionCode = CodeBlock.builder();

        stateTransitionCode.beginControlFlow("case $N:", state.getStateName());
        stateTransitionCode.beginControlFlow("if(--$N == 0)", timeValue);
        stateTransitionCode.addStatement(timeValue + "= $N", state.getTime());
        for(Transition transition : state.getTransitionList()) {
            stateTransitionCode.addStatement("int $NTo$N = 0", transition.getFrom(), transition.getTo());
            stateTransitionCode.addStatement("if ($N) $NTo$N = $N", transition.getGuard(), transition.getFrom(), transition.getTo(), transition.getProbability());
        }

        stateTransitionCode.addStatement("int probabilityList[] = $N", probabilityList);

        stateTransitionCode.beginControlFlow("for(int i=$L; i < probabilityList.length; i++)", "1");
        stateTransitionCode.addStatement("probabilityList[i] += probabilityList[i-1]");
        stateTransitionCode.endControlFlow();

        stateTransitionCode.addStatement("int randomProVal = (int)($T.random() * probabilityList[probabilityList.length-1])", Math.class);

        int index = 0;
        for(Transition transition : state.getTransitionList()) {
            if(index == 0) {
                stateTransitionCode.beginControlFlow("if ($N <= randomProVal && $N > randomProVal)", "0", String.format("probabilityList[%d]", index));
                for(String action : transition.getActions()) {
                    stateTransitionCode.addStatement("selectedActionList.add(new $T())", ClassName.get(packageName, systemEntityName + "_" + action));
                    stateTransitionCode.addStatement("setStatus(Status.$N)",transition.getTo());
                }
                stateTransitionCode.endControlFlow();
            }
            else {

                stateTransitionCode.beginControlFlow("else if ($N <= randomProVal && $N > randomProVal)", String.format("probabilityList[%d]", index), String.format("probabilityList[%d]", (index +1)));
                for(String action : transition.getActions()) {
                    stateTransitionCode.addStatement("selectedActionList.add(new $T())", ClassName.get(packageName, systemEntityName + "_" + action));
                    stateTransitionCode.addStatement("setStatus(Status.$N)",transition.getTo());
                }
                stateTransitionCode.endControlFlow();
            }
            index++;
        }

        stateTransitionCode.endControlFlow();
        stateTransitionCode.addStatement("break");
        stateTransitionCode.endControlFlow();

        return stateTransitionCode.build();
    }

    private String getProbabilityList(State state) {
        StringBuilder ret = new StringBuilder("{");
        for(Transition transition : state.getTransitionList()) {
            ret.append(transition.getFrom()).append("To").append(transition.getTo()).append(",");
        }
        ret = new StringBuilder(ret.substring(0, ret.length() - 1));
        ret.append("}");

        return ret.toString();
    }
}
