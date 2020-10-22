package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class State {
    private String isInitialState;
    private String StateName;
    private String time;

    private ArrayList<Transition> transitionList; // transitions whose src is a state

    public String getIsInitialState() {
        return isInitialState;
    }

    public void setIsInitialState(String isInitialState) {
        this.isInitialState = isInitialState;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Transition> getTransitionList() {
        return transitionList;
    }

    public void setTransitionList(ArrayList<Transition> transitionList) {
        this.transitionList = transitionList;
    }
}
