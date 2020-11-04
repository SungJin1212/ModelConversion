package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.intf.Possessable;
import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.util.ArrayList;

abstract public class ServiceEntity extends _SimActionableObject_ implements Possessable {

    protected SoS sos;
    protected Infrastructure infrastructure;

    protected String serviceDesc;
    protected String serviceProviderId;

    protected ArrayList<SystemEntity> reqSysEntities;

    public ServiceEntity(SoS sos, Infrastructure infrastructure) {
        this.sos = sos;
        this.infrastructure = infrastructure;

        this.actionList = new ArrayList<>(0);
        this.selectedActionList = new ArrayList<>(0);
    }

    public RunResult run() {
        this.clearSelectedAction();
        // TODO: Add Run Logic
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

    //TODO: Service interface
    //Service interface


}
