package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.StructureDataObject.IntegrationModelInfo;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import javax.lang.model.element.Modifier;

public class IntegrationModelCodeGenerationLogic {
    public MethodSpec getConstructor(IntegrationModelInfo integrationModelInfo) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("String"), "name");

        CodeBlock.Builder codeBlock = CodeBlock.builder().addStatement("super(name)");

        for(String mapName : integrationModelInfo.getMapNames()) {
            codeBlock.addStatement("this.map = new $N()", mapName);
        }

        builder.addCode(codeBlock.build());
        return builder.build();
    }
}
