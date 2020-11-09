package CodeGeneration.CodeGenerators;

import CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic.LocationInformationCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic.MapCodeGenerationLogic;
import CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic.ObjectLocationCodeGenerationLogic;
import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import kr.ac.kaist.se.model.geo.LocationInformation;
import kr.ac.kaist.se.model.geo.Map;
import kr.ac.kaist.se.model.geo.ObjectLocation;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

public class GeoCodeGenerator {

    public void LocationInformationCodeGeneration(MapModelInfo mapModelInfo) {
        LocationInformationCodeGenerationLogic locationInformationCodeGenerationLogic = new LocationInformationCodeGenerationLogic();

        TypeSpec.Builder builder = TypeSpec.classBuilder(mapModelInfo.getMapName() + "LocationInformation").addModifiers(Modifier.PUBLIC);

        builder.superclass(LocationInformation.class);
        builder.addFields(locationInformationCodeGenerationLogic.getFieldsCode(mapModelInfo));
        builder.addMethods(locationInformationCodeGenerationLogic.getConstructorCode(mapModelInfo));
        builder.addMethods(locationInformationCodeGenerationLogic.getGettersCodeAndSettersCode(mapModelInfo));

        JavaFile javaFile = JavaFile.builder("GeneratedCode.geo", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/Main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public void EnumCodeGeneration(MapModelInfo mapModelInfo) {
        for(LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if(locDimensionVar.getVarType().toLowerCase().equals("enum")) {

                TypeSpec.Builder builder = TypeSpec.enumBuilder(locDimensionVar.getVarName()).addModifiers(Modifier.PUBLIC);


                for (String enumVar : locDimensionVar.getVarEnumList()) {
                    builder.addEnumConstant(enumVar);
                }

                JavaFile javaFile = JavaFile.builder("GeneratedCode.geo", builder.build()).
                        build();
                try {
                    javaFile.writeTo(Paths.get("./src/Main/java"));
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    public void MapCodeGeneration(MapModelInfo mapModelInfo) {
        MapCodeGenerationLogic mapCodeGenerationLogic = new MapCodeGenerationLogic();
        TypeSpec.Builder builder = TypeSpec.classBuilder(mapModelInfo.getMapName() + "Map").addModifiers(Modifier.PUBLIC);

        builder.superclass(Map.class);
        builder.addFields(mapCodeGenerationLogic.getFieldsCode(mapModelInfo));
        builder.addMethod(mapCodeGenerationLogic.getIsValidLocationCode(mapModelInfo));
        builder.addMethod(mapCodeGenerationLogic.getLocationInfoCode(mapModelInfo));
        builder.addMethod(mapCodeGenerationLogic.getInitCode(mapModelInfo));

        JavaFile javaFile = JavaFile.builder("GeneratedCode.geo", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/Main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    public void ObjectLocationCodeGeneration(MapModelInfo mapModelInfo) {
        ObjectLocationCodeGenerationLogic objectLocationCodeGenerationLogic = new ObjectLocationCodeGenerationLogic();

        TypeSpec.Builder builder = TypeSpec.classBuilder( mapModelInfo.getMapName() + "ObjectLocation").addModifiers(Modifier.PUBLIC);

        builder.superclass(ObjectLocation.class);
        builder.addFields(objectLocationCodeGenerationLogic.getFieldsCode(mapModelInfo));
        builder.addMethod(objectLocationCodeGenerationLogic.getConstructorCode(mapModelInfo));
        builder.addMethods(objectLocationCodeGenerationLogic.getGettersAndSettersCode(mapModelInfo));
        builder.addMethod(objectLocationCodeGenerationLogic.getIsSameLocationCode(mapModelInfo));

        JavaFile javaFile = JavaFile.builder("GeneratedCode.geo", builder.build()).
                build();
        try {
            javaFile.writeTo(Paths.get("./src/Main/java"));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
