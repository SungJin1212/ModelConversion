package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.intf.Communicatable;
import kr.ac.kaist.se.intf.DecisionMakeable;
import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.data._SimData_;
import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;

import java.util.ArrayList;
import java.util.Objects;

abstract public class CS extends _SimActionableObject_ implements DecisionMakeable, Communicatable {
    protected SoS sos;
    protected Organization organization;

    //TODO: Using HashMap or HashTable
    protected ArrayList<InformationData> knowledgeBase;

    public CS (SoS sos, Organization organization) {
        this.sos = sos;
        sos.addCS(this);

        this.organization = organization;
        organization.addCS(this);

        this.actionList = new ArrayList<_SimAction_>(0);
        this.selectedActionList = new ArrayList<_SimAction_>(0);
    }

    public SoS getSos() {
        return sos;
    }

    public void setSos(SoS sos) {
        this.sos = sos;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
        organization.addCS(this);
    }

    @Override
    public void readIncomingMsgs() {
        //read msg
        //update knowledge base
        //in case of CS, msgDataList is automatically stored into its knowledge base
        ArrayList<_SimData_> temp = Objects.requireNonNull(this.getMsgQueue().poll()).getMsgDataList();
        for (_SimData_ simData : temp) {
            this.knowledgeBase.add((InformationData) simData);
        }

        //TODO: If a received data already exists, it should be updated instead of addition.
    }

    public RunResult run() {
        //TODO: read incoming messages
        this.readIncomingMsgs();

        this.clearSelectedAction();
        this.doDecisionMaking();
        return new RunResult(this, this.selectedActionList);
    }

//    public UpdateResult update(RunResult runResult) {
//        UpdateResult updateResult = new UpdateResult(this.name);
//
//
//
//        for (_SimAction_ selectedAction: runResult.getSelectedActionList()) {
//            doAction(selectedAction);
//            updateResult.addLog(selectedAction.getName());
//        }
//        return updateResult;
//    }

    public void doAction(_SimAction_ action){
        action.executeAction();
    }
}
