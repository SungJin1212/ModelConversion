package CodeGeneration.Parser;

import CodeGeneration.DataObject.StructureDataObject.EnvironmentModelInfo;
import CodeGeneration.DataObject.StructureDataObject.IntegrationModelInfo;
import CodeGeneration.DataObject.StructureDataObject.OrganizationModelInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class StructureParser {

    public IntegrationModelInfo getIntegrationModelInfo(String url) {
        IntegrationModelInfo integrationModelInfo = new IntegrationModelInfo();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for(int i=0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);

                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("SoS")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("obj_name")) {
                            integrationModelInfo.setSoSName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if(className.equals("ModelRefEnv")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> EnvNameList = new ArrayList<>(0);
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            EnvNameList.add(childE.getTextContent());
                        }
                    }
                    integrationModelInfo.setEnvNames(EnvNameList);
                }
                else if(className.equals("ModelRefMap")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> MapNameList = new ArrayList<>(0);
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            MapNameList.add(childE.getTextContent());
                        }
                    }
                    integrationModelInfo.setMapNames(MapNameList);
                }
                else if(className.equals("ModelRefOrg")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> OrgNameList = new ArrayList<>(0);
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            OrgNameList.add(childE.getTextContent());
                        }
                    }
                    integrationModelInfo.setOrgNames(OrgNameList);
                }
                else if(className.equals("ModelRefStateMachine")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            integrationModelInfo.setStateMachineName(childE.getTextContent());
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return integrationModelInfo;
    }

    public OrganizationModelInfo getOrganizationModelInfo(String url) {
        OrganizationModelInfo organizationModelInfo = new OrganizationModelInfo();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for(int i=0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);

                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("Organization")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("obj_name")) {
                            organizationModelInfo.setOrgName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if(className.equals("ModelRefOrg")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> OrgNameList = new ArrayList<>(0);
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            OrgNameList.add(childE.getTextContent());
                        }
                    }
                    organizationModelInfo.setSubOrgNames(OrgNameList);
                }
                else if(className.equals("ModelRefSystem")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> CSNames = new ArrayList<>(0);

                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            CSNames.add(childE.getTextContent());
                        }
                    }
                    organizationModelInfo.setCSNames(CSNames);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return organizationModelInfo;
    }

    public EnvironmentModelInfo getEnvironmentalModelInfo(String url) {
        EnvironmentModelInfo environmentModelInfo = new EnvironmentModelInfo();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for(int i=0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);

                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("Environment")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("obj_name")) {
                            environmentModelInfo.setEnvName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if(className.equals("ModelRefStateMachine")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            environmentModelInfo.setStateMachineName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if(className.equals("ObjRefEnvElmt")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    ArrayList<String> EnvNames = new ArrayList<>(0);

                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            EnvNames.add(childE.getTextContent());
                        }
                    }
                    environmentModelInfo.setEnvElementNames(EnvNames);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return environmentModelInfo;

    }
}
