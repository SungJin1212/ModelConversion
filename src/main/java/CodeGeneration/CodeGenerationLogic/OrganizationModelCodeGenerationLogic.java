package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.StructureDataObject.OrganizationModelInfo;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeVariableName;
import kr.ac.kaist.se.model.strc.SoS;

import javax.lang.model.element.Modifier;

public class OrganizationModelCodeGenerationLogic {
    public MethodSpec getConstructor(OrganizationModelInfo organizationModelInfo) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("String"), "name").addParameter(SoS.class, "sos");

        CodeBlock.Builder codeBlock = CodeBlock.builder().addStatement("super(name,sos)");

        builder.addCode(codeBlock.build());
        return builder.build();
    }
}
