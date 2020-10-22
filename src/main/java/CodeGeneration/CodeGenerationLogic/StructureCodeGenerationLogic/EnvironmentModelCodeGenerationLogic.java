package CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic;

import CodeGeneration.DataObject.StructureModelDataObject.EnvironmentModelInfo;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeVariableName;
import kr.ac.kaist.se.model.strc.SoS;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import javax.lang.model.element.Modifier;

public class EnvironmentModelCodeGenerationLogic {
    public MethodSpec getConstructor(EnvironmentModelInfo environmentModelInfo) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("String"), "name").addParameter(SoS.class, "sos");

        CodeBlock.Builder codeBlock = CodeBlock.builder().addStatement("super(name,sos)");

        builder.addCode(codeBlock.build());
        return builder.build();

    }

    public MethodSpec getRunResult() {
        CodeBlock RunResultCode = CodeBlock.builder().addStatement("return super.run()").build();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("run").addModifiers(Modifier.PUBLIC)
                .returns(TypeVariableName.get(RunResult.class)).addCode(RunResultCode);

        return builder.build();

    }

    public MethodSpec getUpdateResult() {
        CodeBlock UpdateResultCode = CodeBlock.builder().addStatement("return super.update(runResult)").build();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("update").addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get(RunResult.class),"runResult").returns(TypeVariableName.get(UpdateResult.class))
                .addCode(UpdateResultCode);

        return builder.build();
    }
}
