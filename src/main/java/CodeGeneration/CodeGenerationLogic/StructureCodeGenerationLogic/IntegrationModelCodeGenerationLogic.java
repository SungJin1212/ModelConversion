package CodeGeneration.CodeGenerationLogic.StructureCodeGenerationLogic;

import CodeGeneration.DataObject.StructureModelDataObject.IntegrationModelInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

public class IntegrationModelCodeGenerationLogic {
    public MethodSpec getConstructor(IntegrationModelInfo integrationModelInfo) {
        String packageName = "cleaningSoSModel.model.geo"; // TODO: Configure Path
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(TypeVariableName.get("String"), "name");

        CodeBlock.Builder codeBlock = CodeBlock.builder().addStatement("super(name)");

        for(String mapName : integrationModelInfo.getMapNames()) {

            //ClassName MapName = ClassName.get(integrationModelInfo.getSoSName()+".model.geo",mapName);
            codeBlock.addStatement("this.map = new $T()", ClassName.get(packageName,mapName));
        }

        builder.addCode(codeBlock.build());
        return builder.build();
    }
}
