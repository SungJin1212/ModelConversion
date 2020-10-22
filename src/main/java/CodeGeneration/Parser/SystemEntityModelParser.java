package CodeGeneration.Parser;

import CodeGeneration.DataObject.SystemModelDataObject.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class SystemEntityModelParser {
    public SMModelInfo getSMModelInfo(String url) {
        SMModelInfo smModelInfo = new SMModelInfo();
        ArrayList<State> states = new ArrayList<>(0);
        ArrayList<Transition> transitions = new ArrayList<>(0);
        HashMap <String, String> stateIDList = new HashMap<>(0);

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for (int i = 0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);
                String className = e.getAttributes().getNamedItem("class").getNodeValue();
                String stateID = e.getAttributes().getNamedItem("name").getNodeValue();

                if (className.equals("State")) {
                    State s = new State();
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element) childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_name")) {
                            s.setStateName(childE.getTextContent());
                            stateIDList.put(stateID, childE.getTextContent());
                        } else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_isInitialState")) {
                            s.setIsInitialState(childE.getTextContent());
                        } else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_timeConstraint")) {
                            s.setTime(childE.getTextContent());
                        }
                    }
                    states.add(s);
                }
            }

            for (int i = 0; i < relationNodes.getLength(); i++) {
                Element e = (Element) relationNodes.item(i);
                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("StateTransition")) {
                    Transition t = new Transition();
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    NodeList fromNode = e.getElementsByTagName("FROM");
                    NodeList toNode = e.getElementsByTagName("TO");

                    for(int j=0; j < fromNode.getLength(); j++) {
                        Element childE = (Element) fromNode.item(j);
                        String fromID = childE.getAttributes().getNamedItem("instance").getNodeValue();
                        String fromName = stateIDList.get(fromID);
                        t.setFrom(fromName);
                    }

                    for(int j=0; j < toNode.getLength(); j++) {
                        Element childE = (Element) toNode.item(j);
                        String toID = childE.getAttributes().getNamedItem("instance").getNodeValue();
                        String toName = stateIDList.get(toID);
                        t.setTo(toName);
                    }


                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element) childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_transition_guard")) {
                            t.setGuard(childE.getTextContent());
                        } else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_transition_probability")) {
                            t.setProbability(childE.getTextContent());

                        } else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_transition_trigger")) {
                            t.setTrigger(childE.getTextContent());

                        } else if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("state_transition_action")) {
                            ArrayList<String> actions = new ArrayList<>(Arrays.asList(childE.getTextContent().split(",")));
                            t.setActions(actions);

                        }
                    }
                    transitions.add(t);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for(State state : states) {
            ArrayList<Transition> srcTransitions = new ArrayList<>(0);
            for(Transition transition : transitions) {
                if (state.getStateName().equals(transition.getFrom())) {
                    srcTransitions.add(transition);
                }
            }
            state.setTransitionList(srcTransitions);
        }

        smModelInfo.setStates(states);
        smModelInfo.setTransitions(transitions);

        return smModelInfo;
    }

    public SystemEntityModelInfo getCSModelInfo(String url) {
        SystemEntityModelInfo systemEntityModelInfo = new SystemEntityModelInfo();
        ArrayList <ActionInfo> actionInfoList = new ArrayList<>(0);

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("INSTANCE"); //object data from xml file
            NodeList referenceNodes = rootElement.getElementsByTagName("INTERREF"); //reference data from xml file
            NodeList relationNodes = rootElement.getElementsByTagName("CONNECTOR"); //relation data from xml file

            for(int i=0; i<referenceNodes.getLength(); i++) {
                Element e = (Element) referenceNodes.item(i);
                NodeList childNodeList = e.getElementsByTagName("IREF");

                for(int j=0; j <childNodeList.getLength(); j++) {
                    Element childE = (Element) childNodeList.item(j);
                    if(childE.getAttributes().getNamedItem("tmodeltype").getNodeValue().equals("State Machine Model")) {
                        String referenceName = childE.getAttributes().getNamedItem("tmodelname").getNodeValue();
                        systemEntityModelInfo.setStateMachineReferenceName(referenceName);
                        break;
                    }

                }
            }

            for(int i=0; i < instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);
                String className = e.getAttributes().getNamedItem("class").getNodeValue();

                if (className.equals("SystemEntity")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("obj_name")) {
                            systemEntityModelInfo.setSystemEntityName(childE.getTextContent());
                            break;
                        }
                    }
                }
                else if (className.equals("Action")) {
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
                    }
                    actionInfoList.add(actionInfo);
                }
                else if (className.equals("ModelRefStateMachine")) {
                    NodeList childNodeList = e.getElementsByTagName("ATTRIBUTE");
                    for(int j=0; j < childNodeList.getLength(); j++) {
                        Element childE = (Element)childNodeList.item(j);
                        if (childE.getAttributes().getNamedItem("name").getNodeValue().equals("ref_targetName")) {
                            systemEntityModelInfo.setStateMachineName(childE.getTextContent());
                            break;
                        }
                    }
                }
            }
            systemEntityModelInfo.setActionInfoList(actionInfoList);
            systemEntityModelInfo.setSmModelInfo(getSMModelInfo(systemEntityModelInfo.getStateMachineReferenceName() + ".xml")); // set StateMachine Info

        } catch (Exception e) {
            e.printStackTrace();
        }


        return systemEntityModelInfo;
    }
}
