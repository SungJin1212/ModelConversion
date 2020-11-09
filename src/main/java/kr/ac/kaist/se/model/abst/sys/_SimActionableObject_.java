package kr.ac.kaist.se.model.abst.sys;

import kr.ac.kaist.se.intf.Actionable;
import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.behv.Action;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.util.ArrayList;

abstract public class _SimActionableObject_ extends _SimObject_ implements Actionable {

    protected ArrayList<_SimAction_> actionList;
    protected ArrayList<_SimAction_> selectedActionList;

    public ArrayList<_SimAction_> getActionList() {
        return actionList;
    }

    public void addAction(_SimAction_ simAction) {
        this.actionList.add(simAction);
    }

    public void removeAction(_SimAction_ simAction) {
        this.actionList.remove(simAction);
    }

    public ArrayList<_SimAction_> getSelectedActionList() {
        return selectedActionList;
    }

    public void addSelectedAction(_SimAction_ simAction) {
        this.selectedActionList.add(simAction);
    }

    public void removeSelectedAction(_SimAction_ simAction) {
        this.selectedActionList.remove(simAction);
    }

    public void clearSelectedAction() {
        this.selectedActionList.clear();
    }

    abstract protected void selectActions(); //TODO: set return

    public UpdateResult update(RunResult runResult) {
        UpdateResult updateResult = new UpdateResult(this.name);

        for (_SimAction_ selectedAction: runResult.getSelectedActionList()) {
            doAction(selectedAction);
            updateResult.addLog(selectedAction.getName());
        }
        return updateResult;
    }
}
