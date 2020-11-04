package CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import kr.ac.kaist.se.model.geo.ObjectLocation;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;

public class ObjectLocationCodeGenerationLogic {
    public ArrayList<FieldSpec> getFieldsCode(MapModelInfo mapModelInfo) {
        ArrayList <FieldSpec> fields = new ArrayList<>(0);

        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                FieldSpec.Builder field = FieldSpec.builder(TypeVariableName.get(locDimensionVar.getVarName()), locDimensionVar.getVarName().toLowerCase())
                        .addModifiers(Modifier.PRIVATE);
                fields.add(field.build());
            }
            else {
                FieldSpec.Builder field = FieldSpec.builder(TypeVariableName.get(locDimensionVar.getVarType()), locDimensionVar.getVarName())
                        .addModifiers(Modifier.PRIVATE);
                fields.add(field.build());
            }
        }

        return fields;
    }

    public MethodSpec getConstructorCode(MapModelInfo mapModelInfo) {
        MethodSpec.Builder constructorCode = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                constructorCode.addParameter(TypeVariableName.get(locDimensionVar.getVarName()), locDimensionVar.getVarName().toLowerCase());
            } else {
                constructorCode.addParameter(TypeVariableName.get(locDimensionVar.getVarType()), locDimensionVar.getVarName());
            }
        }

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                constructorCode.addStatement("this.$N = $N", locDimensionVar.getVarName().toLowerCase(), locDimensionVar.getVarName().toLowerCase());
            } else {
                constructorCode.addStatement("this.$N = $N", locDimensionVar.getVarName(), locDimensionVar.getVarName());
            }
        }
        return constructorCode.build();
    }

    public ArrayList<MethodSpec> getGettersAndSettersCode(MapModelInfo mapModelInfo) {
        ArrayList<MethodSpec> GettersAndSettersCode = new ArrayList<>(0);
        ArrayList<MethodSpec> GettersCode = new ArrayList<>(0);
        ArrayList<MethodSpec> SettersCode = new ArrayList<>(0);

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + locDimensionVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .returns(TypeVariableName.get(locDimensionVar.getVarName()));
                getter.addStatement("return $N", locDimensionVar.getVarName().toLowerCase());
                GettersCode.add(getter.build());
            }
            else {
                MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + locDimensionVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .returns(TypeVariableName.get(locDimensionVar.getVarType()));
                getter.addStatement("return $N", locDimensionVar.getVarName());
                GettersCode.add(getter.build());
            }
        }

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                MethodSpec.Builder setter = MethodSpec.methodBuilder("set" + locDimensionVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeVariableName.get(locDimensionVar.getVarName()), locDimensionVar.getVarName().toLowerCase()).returns(TypeName.VOID);
                setter.addStatement("this.$N = $N", locDimensionVar.getVarName().toLowerCase(), locDimensionVar.getVarName().toLowerCase());
                SettersCode.add(setter.build());
            }
            else {
                MethodSpec.Builder setter = MethodSpec.methodBuilder("set" + locDimensionVar.getVarName()).addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeVariableName.get(locDimensionVar.getVarType()), locDimensionVar.getVarName()).returns(TypeName.VOID);
                setter.addStatement("this.$N = $N", locDimensionVar.getVarName(), locDimensionVar.getVarName());
                SettersCode.add(setter.build());
            }
        }

        GettersAndSettersCode.addAll(GettersCode);
        GettersAndSettersCode.addAll(SettersCode);

        return GettersAndSettersCode;

    }

    public MethodSpec getIsSameLocationCode(MapModelInfo mapModelInfo) {

        String objectName = mapModelInfo.getMapName() + "ObjectLocation";
        String lowerObjectName = objectName.substring(0,1).toLowerCase();
        lowerObjectName = lowerObjectName + objectName.substring(1);

        MethodSpec.Builder isSameLocationCode = MethodSpec.methodBuilder("isSameLocation").addModifiers(Modifier.PUBLIC).returns(TypeName.BOOLEAN)
                .addParameter(ObjectLocation.class, "target");

        isSameLocationCode.beginControlFlow("if(!(target instanceof $N))", objectName);
        isSameLocationCode.addStatement("return false");
        isSameLocationCode.endControlFlow();

        isSameLocationCode.addStatement("$N $N = ($N) target", objectName, lowerObjectName, objectName);

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                isSameLocationCode.beginControlFlow("if (this.$N != $N.get$N())", locDimensionVar.getVarName().toLowerCase(), lowerObjectName, locDimensionVar.getVarName());
                isSameLocationCode.addStatement("return false");
                isSameLocationCode.endControlFlow();
            }
            else {
                isSameLocationCode.beginControlFlow("if (this.$N != $N.get$N())", locDimensionVar.getVarName(), lowerObjectName, locDimensionVar.getVarName());
                isSameLocationCode.addStatement("return false");
                isSameLocationCode.endControlFlow();
            }
        }


        isSameLocationCode.addStatement("return true");
        return isSameLocationCode.build();

    }
}
