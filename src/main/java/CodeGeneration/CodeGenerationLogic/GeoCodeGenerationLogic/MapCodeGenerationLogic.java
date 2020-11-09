package CodeGeneration.CodeGenerationLogic.GeoCodeGenerationLogic;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import com.squareup.javapoet.*;
import kr.ac.kaist.se.model.geo.LocationInformation;
import kr.ac.kaist.se.model.geo.ObjectLocation;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapCodeGenerationLogic {



    public ArrayList<FieldSpec> getFieldsCode(MapModelInfo mapModelInfo) {
        String packageName = "GeneratedCode.geo";
        String ObjectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
        String LocationInformationName = mapModelInfo.getMapName() + "LocationInformation";
        String MapInfoName = mapModelInfo.getMapName() + "MapInfo";


        ArrayList<FieldSpec> fields = new ArrayList<>(0);

        ClassName ObjectLocation = ClassName.get(packageName, ObjectLocationName);
        ClassName LocationInformation = ClassName.get(packageName, LocationInformationName);
        ClassName hashMap = ClassName.get("java.util", "HashMap");

        FieldSpec.Builder hashMapField = FieldSpec.builder(ParameterizedTypeName.get(hashMap, ObjectLocation , LocationInformation),
                MapInfoName).initializer("new HashMap<>(0)").addModifiers(Modifier.PRIVATE);

        fields.add(hashMapField.build());
        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (!locDimensionVar.getVarType().toLowerCase().equals("enum")) {
                FieldSpec.Builder maxField = FieldSpec.builder(TypeVariableName.get(locDimensionVar.getVarType()), "max" + locDimensionVar.getVarName())
                        .addModifiers(Modifier.PRIVATE).initializer(locDimensionVar.getVarMax());
                FieldSpec.Builder minField = FieldSpec.builder(TypeVariableName.get(locDimensionVar.getVarType()), "min" + locDimensionVar.getVarName())
                        .addModifiers(Modifier.PRIVATE).initializer(locDimensionVar.getVarMin());

                fields.add(maxField.build());
                fields.add(minField.build());
            }
        }

        return fields;
    }

    public MethodSpec getIsValidLocationCode(MapModelInfo mapModelInfo) {
        String ObjectLocation = mapModelInfo.getMapName() + "ObjectLocation";
        MethodSpec.Builder isValidLocationCode = MethodSpec.methodBuilder("isValidLocation").addModifiers(Modifier.PUBLIC).returns(TypeName.BOOLEAN)
                .addParameter(ObjectLocation.class, "loc");

        isValidLocationCode.addAnnotation(Override.class);
        isValidLocationCode.addStatement("$N curLoc = ($N)loc", ObjectLocation, ObjectLocation);

        int index = 0;
        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) {
                isValidLocationCode.beginControlFlow("if (curLoc.get$N() != null)", locDimensionVar.getVarName());
            } else {
                isValidLocationCode.beginControlFlow("if (curLoc.get$N() >= min$N && curLoc.get$N() <= max$N)",
                        locDimensionVar.getVarName(), locDimensionVar.getVarName(), locDimensionVar.getVarName(), locDimensionVar.getVarName());
            }
            index++;

            if (index == mapModelInfo.getLocDimensionVarList().size()) {
                isValidLocationCode.addStatement("return true");
            }

        }

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            isValidLocationCode.endControlFlow();
        }
        isValidLocationCode.addStatement("return false");

        return isValidLocationCode.build();
    }

    public MethodSpec getLocationInfoCode(MapModelInfo mapModelInfo) {
        MethodSpec.Builder LocationInfoCode = MethodSpec.methodBuilder("getLocationInfo").addModifiers(Modifier.PUBLIC).returns(LocationInformation.class)
                .addParameter(ObjectLocation.class, "loc");

        String objectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
        String mapInfoName = mapModelInfo.getMapName() + "MapInfo";

        LocationInfoCode.addAnnotation(Override.class);
        LocationInfoCode.beginControlFlow("if(isValidLocation(loc))");
        LocationInfoCode.addStatement("$N curLoc = ($N) loc", objectLocationName, objectLocationName);
        LocationInfoCode.addStatement("return $N.get(curLoc)", mapInfoName);
        LocationInfoCode.endControlFlow();

        LocationInfoCode.beginControlFlow("else");
        LocationInfoCode.addStatement("return null");
        LocationInfoCode.endControlFlow();


        return LocationInfoCode.build();

    }

    public MethodSpec getInitCode(MapModelInfo mapModelInfo) {

        String ObjectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
        String LocationInformationName = mapModelInfo.getMapName() + "LocationInformation";
        String MapInfoName = mapModelInfo.getMapName() + "MapInfo";

        MethodSpec.Builder InitCode = MethodSpec.methodBuilder("InitMapInfo").addModifiers(Modifier.PRIVATE).returns(TypeName.VOID);

        ArrayList<String> initializationQuery = mapModelInfo.getMapInitializationList();

        HashMap <String, String> LocDimensionVarToType = new HashMap<>(0);

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            LocDimensionVarToType.put(locDimensionVar.getVarName(), locDimensionVar.getVarType().toLowerCase());
        }
        InitCode.addCode(GenerateMapInitializeCode(mapModelInfo, ObjectLocationName, LocationInformationName, MapInfoName));

        for (String Query : initializationQuery) {
        String[] decompose = Query.split("_");
        String forInfo = decompose[0].substring(decompose[0].indexOf("(") + 1, decompose[0].indexOf(")"));
        String initInfo = decompose[1].substring(decompose[1].indexOf("{") + 1, decompose[1].indexOf("}"));

        // forInfo: ALL, initInfo: dirtLevel:0,isWall:0,isChargingStation:0
        // System.out.println(forInfo + " " + initInfo);
        InitCode.addCode(getEachQueryCode(forInfo, initInfo, mapModelInfo,LocDimensionVarToType));

        }


        return InitCode.build();
    }

    //forInfo: ALL, initInfo: dirtLevel:0,isWall:0,isChargingStation:0
    //forInfo: floorNum==B1, initInfo: dirtLevel:3

    private CodeBlock getEachQueryCode(String forInfo, String initInfo, MapModelInfo mapModelInfo, HashMap<String, String> locDimensionVarToType) {

        String packageName = "GeneratedCode.geo";
        String ObjectLocationName = mapModelInfo.getMapName() + "ObjectLocation";
        String LocationInformationName = mapModelInfo.getMapName() + "LocationInformation";
        String MapInfoName = mapModelInfo.getMapName() + "MapInfo";
        CodeBlock.Builder eachQueryCode = CodeBlock.builder();


        //GenerateMapInitializeCode(mapModelInfo, ObjectLocationName, LocationInformationName, MapInfoName, eachQueryCode);

        eachQueryCode.add(GenerateEachQueryCode(mapModelInfo, forInfo, initInfo, locDimensionVarToType, ObjectLocationName, MapInfoName));

        //GenerateEachQueryCode(mapModelInfo, forInfo, initInfo, locDimensionVarToType, ObjectLocationName, MapInfoName, eachQueryCode);

        return eachQueryCode.build();
    }

    private CodeBlock GenerateEachQueryCode(MapModelInfo mapModelInfo, String forInfo, String initInfo, HashMap<String, String> locDimensionVarToType, String objectLocationName, String mapInfoName) {

        CodeBlock.Builder eachQueryCode = CodeBlock.builder();


        if (forInfo.equals("ALL")) { // forInfo is "ALL" case
            ArrayList<String> initList = new ArrayList <>(Arrays.asList(initInfo.split(",")));
            ArrayList <String> setSentenceList = new ArrayList<>(0);


            for(String init : initList) {
                String setSentence;

                String target = init.split(":")[0].trim();
                String value = init.split(":")[1].trim();

                setSentence = String.format("%s.get(%s).set%s(%s)", mapInfoName, getFistCharLower(objectLocationName), target, value);
                setSentenceList.add(setSentence);
            }

            eachQueryCode.beginControlFlow("for($N $N : $N.keySet())", objectLocationName, getFistCharLower(objectLocationName), mapInfoName);
            for(String setSentence : setSentenceList) {
                eachQueryCode.addStatement(setSentence);
            }
            eachQueryCode.endControlFlow();


        }
        else { // forInfo is not "ALL"
            ArrayList<String> initList = new ArrayList <>(Arrays.asList(initInfo.split(",")));
            ArrayList <String> forSentenceList = new ArrayList<>(Arrays.asList(forInfo.split("&&|\\|\\|"))); //split "&&" or "||"
            ArrayList <String> delimiters = new ArrayList<>(0);
            String ifSentence = "";
            ArrayList <String> setSentenceList = new ArrayList<>(0);
            ArrayList <String> ifSentenceList = new ArrayList<>(0);

            for(int i = 0; i < forSentenceList.size() - 1; i++) {
                int index = forInfo.indexOf(forSentenceList.get(i)) + forSentenceList.get(i).length();
                delimiters.add(Character.toString(forInfo.charAt(index)) + Character.toString(forInfo.charAt(index + 1)));
            }

            for(String forSentence : forSentenceList) {

                String target = forSentence.split("==")[0].trim();
                String value = forSentence.split("==")[1].trim();
                if(locDimensionVarToType.get(target).equals("enum")) {
                    ifSentenceList.add(String.format("%s.get%s() == %s.%s", getFistCharLower(objectLocationName), target, target,value));
                }
                else {
                    ifSentenceList.add(String.format("%s.get%s() == %s", getFistCharLower(objectLocationName), target,value));
                }
            }

            int index = 0;
            ifSentence = forInfo.trim();
            for(String eachIfSentence : ifSentenceList) {
                //System.out.println(ifSentence);
                ifSentence = ifSentence.replace(forSentenceList.get(index).trim(), ifSentenceList.get(index).trim());
                //System.out.println(ifSentence);

                index++;
            }


            for(String init : initList) {
                String setSentence;

                String target = init.split(":")[0].trim();
                String value = init.split(":")[1].trim();

                setSentence = String.format("%s.get(%s).set%s(%s)", mapInfoName, getFistCharLower(objectLocationName), target, value);
                setSentenceList.add(setSentence);
            }

            eachQueryCode.beginControlFlow("for($N $N : $N.keySet())", objectLocationName, getFistCharLower(objectLocationName), mapInfoName);
            eachQueryCode.beginControlFlow("if($N)", ifSentence);
            for(String setSentence : setSentenceList) {
                eachQueryCode.addStatement(setSentence);
            }
            eachQueryCode.endControlFlow();
            eachQueryCode.endControlFlow();
        }
        return eachQueryCode.build();
    }


    private CodeBlock GenerateMapInitializeCode(MapModelInfo mapModelInfo, String objectLocationName, String locationInformationName, String mapInfoName) {

        CodeBlock.Builder mapInitializeCode = CodeBlock.builder();


        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                mapInitializeCode.beginControlFlow("for($N $N : $N.values())",locDimensionVar.getVarName()
                        , locDimensionVar.getVarName().toLowerCase(), locDimensionVar.getVarName());
            }
            else {
                mapInitializeCode.beginControlFlow("for($N $N = $N; $N <= $N; $N+=$N)", locDimensionVar.getVarType()
                        ,locDimensionVar.getVarName(), "min" + locDimensionVar.getVarName(), locDimensionVar.getVarName(), "max" + locDimensionVar.getVarName()
                        ,locDimensionVar.getVarName(), locDimensionVar.getDiscreteValue());
            }
        }
        String keyCode = "";
        int index = 0;
        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            if (locDimensionVar.getVarType().toLowerCase().equals("enum")) { // enum case
                if (index == 0) {
                    keyCode += locDimensionVar.getVarName().toLowerCase();
                }
                else {
                    keyCode += "," + locDimensionVar.getVarName().toLowerCase();
                }
            }
            else {
                if (index == 0) {
                    keyCode += locDimensionVar.getVarName();
                }
                else {
                    keyCode += "," + locDimensionVar.getVarName();
                }
            }
            index++;
        }
        mapInitializeCode.addStatement("$N.put(new $N($N), new $N(null))", mapInfoName, objectLocationName, keyCode, locationInformationName);

        for (LocDimensionVar locDimensionVar : mapModelInfo.getLocDimensionVarList()) {
            mapInitializeCode.endControlFlow();
        }
        return mapInitializeCode.build();
    }

    private String getFistCharLower(String str) {
        return str.substring(0 ,1).toLowerCase() + str.substring(1);
    }

}
