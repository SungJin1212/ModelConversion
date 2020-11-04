package CodeGeneration.Parser;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import CodeGeneration.DataObject.GeoModelDataObject.LocInformationVar;
import CodeGeneration.DataObject.GeoModelDataObject.MapModelInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class GeoModelParser {
    public MapModelInfo getMapModelInfo(String url) {
        MapModelInfo mapModelInfo = new MapModelInfo();

        HashMap<String, String> VarToType = new HashMap<>(0);
        HashMap<String, String> VarToMin = new HashMap<>(0);
        HashMap<String, String> VarToMax = new HashMap<>(0);
        HashMap<String, String> VarToEnum = new HashMap<>(0);
        HashMap<String, String> VarToID = new HashMap<>(0);
        HashMap<String, String> VarToDefault = new HashMap<>(0);

        HashSet <String> locInformationVarList = new HashSet<>(0);
        HashSet <String> locDimensionVarList = new HashSet<>(0);

        ArrayList<LocDimensionVar> locDimensionVarArrayList = new ArrayList<>(0);
        ArrayList<LocInformationVar> locInformationVarArrayList = new ArrayList<>(0);
        ArrayList<String> mapInitializationList;


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for(int i=0; i<relationNodes.getLength(); i++) {
                Element e = (Element) relationNodes.item(i);

                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if(className.equals("LocDimensionAggregation")) {
                    NodeList childNodeList = e.getElementsByTagName("TO");
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element) childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("class").getNodeValue().equals("LocDimensionVar")) {
                            locDimensionVarList.add(childE.getAttributes().getNamedItem("instance").getNodeValue());
                        }
                    }
                }
                else if(className.equals("LocInformationAggregation")) {
                    NodeList childNodeList = e.getElementsByTagName("TO");
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element) childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("class").getNodeValue().equals("LocInformationVar")) {
                            locInformationVarList.add(childE.getAttributes().getNamedItem("instance").getNodeValue());
                        }
                    }
                }
            }


            for(int i=0; i <instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);

                String className = e.getAttributes().getNamedItem("class").getNodeValue();


                if(className.equals("Map")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j< childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if(childE.getAttributes().getNamedItem("name").getNodeValue().equals("map_name")) {
                            mapModelInfo.setMapName(childE.getTextContent());
                        }

                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("map_initialization")) {
                            mapInitializationList = new ArrayList<>(Arrays.asList(childE.getTextContent().split(";")));
                            mapModelInfo.setMapInitializationList(mapInitializationList);
                        }
                    }
                }
                else if (className.equals("LocDimensionVar") || className.equals("LocInformationVar")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    String varName = "" , varType = "";
                    String varID = e.getAttributes().getNamedItem("name").getNodeValue();
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("var_name")) {
                            varName = childE.getTextContent();
                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("var_type")) {
                            varType = childE.getTextContent();
                        }
                    }
                    VarToType.put(varName, varType);
                    VarToID.put(varName, varID);
                }

                else if (className.equals("VariableDomain")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    String varType = "";
                    for(int j=0; j< childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_type")) {
                            varType = childE.getTextContent();
                        }
                    }

                    if (varType.equals("ENUMERATION")) {
                        String varName = "", varEnum = "";
                        for(int j=0; j <childNodeList.getLength(); j++) {
                            Element childE = (Element)childNodeList.item(j);
                            if(childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_name")) {
                                varName = childE.getTextContent();
                            }
                            else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_valEnum")) {
                                varEnum = childE.getTextContent();
                            }
                            VarToEnum.put(varName, varEnum);
                        }
                    }
                    else if (varType.equals("VALUE_RANGE")) {
                        String varName = "", varMin = "", varMax = "", varDefault = "";
                        for(int j=0; j <childNodeList.getLength(); j++) {
                            Element childE = (Element)childNodeList.item(j);
                            if(childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_name")) {
                                varName = childE.getTextContent();
                            }
                            else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_valMin")) {
                                varMin = childE.getTextContent();
                            }
                            else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_valMax")) {
                                varMax = childE.getTextContent();
                            }
                            else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("domain_valDefault")) {
                                varDefault = childE.getTextContent();
                            }
                        }

                        VarToMin.put(varName, varMin);
                        VarToMax.put(varName, varMax);
                        VarToDefault.put(varName, varDefault);
                    }
                }
            }

            for(String var : VarToID.keySet()) {
                String id = VarToID.get(var);
                if (locInformationVarList.contains(id)) {
                    String type = VarToType.get(var);
                    if (!type.toLowerCase().equals("enum")) { // not enum
                        String min = VarToMin.get(var);
                        String max = VarToMax.get(var);
                        String defaultVal = VarToDefault.get(var);
                        locInformationVarArrayList.add(new LocInformationVar(var,type,min,max, "1", defaultVal)); //TODO: discrete Value

                    }
                    else { // enum
                        ArrayList<String> enumList = new ArrayList<>(Arrays.asList(VarToEnum.get(var).split(",")));
                        locInformationVarArrayList.add(new LocInformationVar(var,type,enumList));
                    }
                }
                else if (locDimensionVarList.contains(id)) {
                    String type = VarToType.get(var);
                    if (!type.toLowerCase().equals("enum")) { // not enum
                        String min = VarToMin.get(var);
                        String max = VarToMax.get(var);
                        String defaultVal = VarToDefault.get(var);
                        locDimensionVarArrayList.add(new LocDimensionVar(var,type,min,max, "1", defaultVal)); //TODO: discrete Value

                    }
                    else { // enum
                        ArrayList<String> enumList = new ArrayList<>(Arrays.asList(VarToEnum.get(var).split(",")));
                        locDimensionVarArrayList.add(new LocDimensionVar(var,type,enumList));
                    }
                }
            }

            mapModelInfo.setLocDimensionVarList(locDimensionVarArrayList);
            mapModelInfo.setLocInformationVarList(locInformationVarArrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapModelInfo;
    }
}
