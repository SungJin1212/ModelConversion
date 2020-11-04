package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.intf.Communicatable;
import kr.ac.kaist.se.intf.DecisionMakeable;
import kr.ac.kaist.se.intf.Stateful;
import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.util.ArrayList;

abstract public class SystemEntity extends _SimActionableObject_
        implements Communicatable, DecisionMakeable {

    protected SoS sos;
    protected Infrastructure infrastructure;

    protected EnumSystemState curState;

    public SystemEntity(SoS sos, Infrastructure infrastructure) {
        this.sos = sos;
        sos.addSystemEntity(this);

        this.infrastructure = infrastructure;
        infrastructure.addSystemEntity(this);

        this.actionList = new ArrayList<>(0);
        this.selectedActionList = new ArrayList<>(0);
    }

    public SoS getSos() {
        return sos;
    }

    public void setSos(SoS sos) {
        this.sos = sos;
    }

    public Infrastructure getInfrastructure() {return infrastructure;}
    public void setInfrastructure(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
        infrastructure.addSystemEntity(this);
    }

    public RunResult run() {
        this.clearSelectedAction();
        this.doDecisionMaking();
        return new RunResult(this, this.selectedActionList);
    }

    public UpdateResult update(RunResult runResult) {
        UpdateResult updateResult = new UpdateResult(this.name);

        for (_SimAction_ selectedAction: runResult.getSelectedActionList()) {
            doAction(selectedAction);
            updateResult.addLog(selectedAction.getName());
        }
        return updateResult;
    }

    public void doAction(_SimAction_ action){
        action.executeAction();
    }
}
