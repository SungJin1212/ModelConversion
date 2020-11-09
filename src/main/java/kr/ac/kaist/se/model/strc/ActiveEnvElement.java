package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;

import java.util.ArrayList;

// subject
public abstract class ActiveEnvElement extends EnvElement {

    public ActiveEnvElement(SoS sos, Environment environment){
        super(sos,environment);

        sos.addActiveEnvironment(this);
        environment.addActiveEnvElement(this);


        this.actionList = new ArrayList<_SimAction_>(0);
        this.selectedActionList = new ArrayList<_SimAction_>(0);
    }

    public void doAction(_SimAction_ simAction) {
        simAction.executeAction();
    }

    abstract public void notifyToServices();


    public RunResult run() {
        this.clearSelectedAction();
        this.selectActions();
        return new RunResult(this, this.selectedActionList);
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
}
