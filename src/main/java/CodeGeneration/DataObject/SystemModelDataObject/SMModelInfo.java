package CodeGeneration.DataObject.SystemModelDataObject;

import java.util.ArrayList;

public class SMModelInfo {



    private ArrayList<State> states;
    private ArrayList<Transition> transitions;

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }


}
