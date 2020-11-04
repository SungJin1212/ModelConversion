package CodeGeneration.Parser;

import CodeGeneration.DataObject.SystemModelDataObject.ActionInfo;
import CodeGeneration.DataObject.SystemModelDataObject.LocationInfo;
import CodeGeneration.DataObject.SystemModelDataObject.ServiceEntityModelInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class ServiceEntityModelParser {
    public ServiceEntityModelInfo getServiceEntityModelInfo(String url) {
        ServiceEntityModelInfo serviceEntityModelInfo = new ServiceEntityModelInfo();
        ArrayList<ActionInfo> actionInfoList = new ArrayList<>(0);
        ArrayList<LocationInfo> locationInfoList = new ArrayList<>(0);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList referenceNodes = rootElement.getElementsByTagName("INTERREF"); //reference data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for (int i=0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);
                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("ServiceEntity")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("obj_name")) {
                            serviceEntityModelInfo.setServiceEntityName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if(className.equals("Action")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ActionInfo actionInfo = new ActionInfo();
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_name")) {
                            actionInfo.setName(childE.getTextContent());
                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_precondition")) {
                            actionInfo.setPrecondition(childE.getTextContent());

                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_duration")) {
                            actionInfo.setDuration(childE.getTextContent());

                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_cost")) {
                            actionInfo.setCost(childE.getTextContent());

                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_benefit")) {
                            actionInfo.setBenefit(childE.getTextContent());
                        }
                        else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("action_type")) {
                            actionInfo.setActionType(childE.getTextContent());
                        }
                    }
                    actionInfoList.add(actionInfo);
                }
                else if(className.equals("ObjectLocation")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0;j < childNodeList.getLength(); j++) {
                        Element childE= (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("loc_spec")) {

                            if(childE.getTextContent().equals("loc_spec_not_inserted")) {
                                break;
                            }

                            ArrayList<String> locSpecList = new ArrayList<>(Arrays.asList(childE.getTextContent().trim().split(",")));
                            for(String locSpec : locSpecList) {
                                String name = locSpec.split("=")[0];
                                String value = locSpec.split("=")[1];
                                LocationInfo locationInfo = new LocationInfo();
                                locationInfo.setValName(name.trim());
                                locationInfo.setValue(value.trim());
                                locationInfoList.add(locationInfo);
                            }
                            break;
                        }
                    }
                }
            }

            serviceEntityModelInfo.setActionInfoList(actionInfoList);
            serviceEntityModelInfo.setLocationInfoList(locationInfoList);

            } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceEntityModelInfo;
    }
}
