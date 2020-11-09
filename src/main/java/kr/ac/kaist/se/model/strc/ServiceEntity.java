package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.intf.Communicatable;
import kr.ac.kaist.se.intf.Possessable;
import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.util.ArrayList;

abstract public class ServiceEntity extends _SimActionableObject_ implements Possessable, Communicatable {

    protected SoS sos;
    protected Infrastructure infrastructure;

    protected String serviceDesc;
    protected String serviceProviderId;

    protected ArrayList<SystemEntity> reqSysEntities;

    public ServiceEntity(SoS sos, Infrastructure infrastructure) {
        this.sos = sos;
        sos.addServiceEntity(this);
        this.infrastructure = infrastructure;
        infrastructure.addServiceEntity(this);

        this.actionList = new ArrayList<>(0);
        this.selectedActionList = new ArrayList<>(0);
    }

    public RunResult run() {
        this.clearSelectedAction();

        // TODO: Add Run Logic
        return new RunResult(this, this.selectedActionList);
    }
    @Override
    public void readIncomingMsgs() {
    }

//    public UpdateResult update(RunResult runResult) {
//        UpdateResult updateResult = new UpdateResult(this.name);
//
//        for (_SimAction_ selectedAction: runResult.getSelectedActionList()) {
//            doAction(selectedAction);
//            updateResult.addLog(selectedAction.getName());
//        }
//        return updateResult;
//    }

    abstract public void updateValue();


    public void doAction(_SimAction_ action){
        action.executeAction();
    }

    //TODO: Service interface
    //Service interface


}
