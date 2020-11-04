package CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.LocInformationVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.abst.sys._SimObject_;

import javax.lang.model.element.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class LocationInformationCodeGenerationLogic {
    public ArrayList<FieldSpec> getFieldsCode(MapModelInfo mapModelInfo) {
        ArrayList<FieldSpec> fields = new ArrayList<>(0);

        for(LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {
            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                FieldSpec.Builder field = FieldSpec.builder(TypeVariableName.get(locInformationVar.getVarName()), locInformationVar.getVarName().toLowerCase())
                        .addModifiers(Modifier.PRIVATE);
                fields.add(field.build());
            }
            else {
                FieldSpec.Builder field = FieldSpec.builder(TypeVariableName.get(locInformationVar.getVarType()), locInformationVar.getVarName())
                        .addModifiers(Modifier.PRIVATE);
                fields.add(field.build());
            }
        }
        return fields;
    }

    public ArrayList<MethodSpec> getConstructorCode(MapModelInfo mapModelInfo) {

        ArrayList<MethodSpec> constructors = new ArrayList<>(0);

        String packageName = "CodeGeneration.CodeGenerationLogic.GeneratedCode.geo";
        MethodSpec.Builder constructorCode = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC).addParameter(ParameterizedTypeName.get(ArrayList.class, _SimObject_.class), "objectArrayList");

        for (LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {

            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                constructorCode.addParameter(TypeVariableName.get("$T", ClassName.get(packageName, locInformationVar.getVarName())), locInformationVar.getVarName().toLowerCase());

            } else {
                constructorCode.addParameter(TypeVariableName.get(locInformationVar.getVarType()), locInformationVar.getVarName());
            }
        }
        constructorCode.addStatement("super(objectArrayList)");
        for (LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {
            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                constructorCode.addStatement("this.$N = $N", locInformationVar.getVarName().toLowerCase(), locInformationVar.getVarName().toLowerCase());

            } else {
                constructorCode.addStatement("this.$N = $N", locInformationVar.getVarName(), locInformationVar.getVarName());
            }
        }

        MethodSpec.Builder initConstructorCode = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC).addParameter(ParameterizedTypeName.get(ArrayList.class, _SimObject_.class), "objectArrayList");

        initConstructorCode.addStatement("super(objectArrayList)");
        for (LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {
            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                initConstructorCode.addStatement("this.$N = $N", locInformationVar.getVarName().toLowerCase(), locInformationVar.getDefaultVal());

            } else {
                initConstructorCode.addStatement("this.$N = $N", locInformationVar.getVarName(), locInformationVar.getDefaultVal());
            }
        }
        constructors.add(constructorCode.build());
        constructors.add(initConstructorCode.build());


        return constructors;
    }

    public ArrayList<MethodSpec> getGettersCodeAndSettersCode(MapModelInfo mapModelInfo) {
        ArrayList<MethodSpec> GettersAndSettersCode = new ArrayList<>(0);
        ArrayList<MethodSpec> GettersCode = new ArrayList<>(0);
        ArrayList<MethodSpec> SettersCode = new ArrayList<>(0);

        for(LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {
            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + locInformationVar.getVarName())
                        .addModifiers(Modifier.PUBLIC).returns(TypeVariableName.get(locInformationVar.getVarName()));
                getter.addStatement("return $N", locInformationVar.getVarName().toLowerCase());
                GettersCode.add(getter.build());
            }
            else {
                MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + locInformationVar.getVarName())
                        .addModifiers(Modifier.PUBLIC).returns(TypeVariableName.get(locInformationVar.getVarType()));
                getter.addStatement("return $N", locInformationVar.getVarName());
                GettersCode.add(getter.build());
            }
        }

        for (LocInformationVar locInformationVar : mapModelInfo.getLocInformationVarList()) {
            if (locInformationVar.getVarType().toLowerCase().equals("enum")) { // enum case
                MethodSpec.Builder setter = MethodSpec.methodBuilder("set" + locInformationVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeVariableName.get(locInformationVar.getVarName()), locInformationVar.getVarName().toLowerCase()).returns(TypeName.VOID);
                setter.addStatement("this.$N = $N", locInformationVar.getVarName().toLowerCase(), locInformationVar.getVarName().toLowerCase());
                SettersCode.add(setter.build());
            }
            else {
                MethodSpec.Builder setter = MethodSpec.methodBuilder("set" + locInformationVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeVariableName.get(locInformationVar.getVarType()), locInformationVar.getVarName()).returns(TypeName.VOID);
                setter.addStatement("this.$N = $N", locInformationVar.getVarName(), locInformationVar.getVarName());
                SettersCode.add(setter.build());
            }
        }

        GettersAndSettersCode.addAll(GettersCode);
        GettersAndSettersCode.addAll(SettersCode);

        return GettersAndSettersCode;
    }
}
