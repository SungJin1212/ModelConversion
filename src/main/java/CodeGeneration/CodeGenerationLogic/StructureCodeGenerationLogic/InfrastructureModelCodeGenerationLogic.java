package CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic;

import CodeGeneration.DataObject.StructureModelDataObject.InfrastructureModelInfo;
import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeVariableName;
import kr.ac.kaist.se.model.strc.SoS;

import javax.lang.model.element.Modifier;

public class InfrastructureModelCodeGenerationLogic {
    public MethodSpec getConstructor(InfrastructureModelInfo infrastructureModelInfo) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("String"), "name").addParameter(TypeVariableName.get(SoS.class), "soS");

        CodeBlock.Builder codeBlock = CodeBlock.builder().addStatement("super(name,soS)");

        builder.addCode(codeBlock.build());
        return builder.build();
    }
}
