package CodeGeneration.CodeGenerationLogic;

import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ServiceEntityModelInfo;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import kr.ac.kaist.se.model.strc.Infrastructure;
import kr.ac.kaist.se.model.strc.SoS;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class ServiceEntityCodeGenerationLogic {
    public MethodSpec getConstructor(MapModelInfo mapModelInfo, ServiceEntityModelInfo serviceEntityModelInfo) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(SoS.class, "sos").addParameter(Infrastructure.class, "infrastructure")
                .addParameter(String.class, "name"); // TODO: Add Location Info

        builder.addStatement("super(sos,infrastructure)");
        builder.addStatement("this.name = name");

        return builder.build();
    }

    public ArrayList<MethodSpec> getMethods(ServiceEntityModelInfo serviceEntityModelInfo) { // changeState, selectActions, update
        ArrayList<MethodSpec> methods = new ArrayList<>(0);

//        MethodSpec.Builder changeStateCode = MethodSpec.methodBuilder("changeState").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
//                .addAnnotation(Override.class);

        MethodSpec.Builder acceptOccCode = MethodSpec.methodBuilder("acceptOcc").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addAnnotation(Override.class).addParameter(String.class, "id");

        MethodSpec.Builder selectActionsCode = MethodSpec.methodBuilder("selectActions").addModifiers(Modifier.PROTECTED).returns(TypeName.VOID)
                .addAnnotation(Override.class);


        methods.add(selectActionsCode.build());
        methods.add(acceptOccCode.build());

        return methods;
    }
}
