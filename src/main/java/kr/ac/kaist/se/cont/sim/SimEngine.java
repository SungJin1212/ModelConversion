package kr.ac.kaist.se.cont.sim;

import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.behv.CommAction;
import kr.ac.kaist.se.model.strc.SoS;
import kr.ac.kaist.se.simdata.input.*;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;
import kr.ac.kaist.se.simdata.output.record.SimLog;

import java.util.ArrayList;

public class SimEngine {
    protected SoS sos;
    protected SimConfiguration simConfiguration;
    protected SimScenario simScenario;
    protected ArrayList<SimScenarioEvent> simScenarioEvents;

    public SimEngine(SoS sos, SimConfiguration simConfiguration, SimScenario simScenario) {
        this.sos = sos;
        this.simConfiguration = simConfiguration;
        this.simScenario = simScenario;
        this.simScenarioEvents = generateEvents(simScenario);
    }

    protected ArrayList<SimScenarioEvent> generateEvents(SimScenario simScenario) {
        return new ArrayList<SimScenarioEvent>(0);
    }

    public SimLog executeSimulation() {
        SimLog simLog = new SimLog();

        sos.updateMapObjectLocationArrayListHashMap();
        for(int i = 0; i < this.simConfiguration.totalTimeFrame; i++) {
            UpdateResult updateResult = new UpdateResult("Tick " + i);
            ArrayList<SimScenarioEvent> eventList = this.getEvent();
            if(eventList.size() > 0){
                executeEvent(eventList);
            }

            RunResult simulationResult = this.runSimulation();
            simulationResult = this.resolveConflict(simulationResult);

            ArrayList<CommAction> selectedCommActions = readCommActions(new ArrayList<>(0),simulationResult);
            //Enqueue messages of the selected CommActions
            for (CommAction commAction : selectedCommActions){ //execute commActions
                sos.getSimObject(commAction.getMessage().getReceiverId()).getMsgQueue().offer(commAction.getMessage()); //get corresponding simObject from receiverId and get msgQueue and push the message
            }
            //simulationResult:= target, selectedActionList, childRunResult

            UpdateResult updateResult1 = this.updateSimulation(simulationResult);
            updateResult.addAllLog(updateResult1.getLog());
            simLog.addUpdateResult(updateResult);

            sos.updateMapObjectLocationArrayListHashMap();
        }
        return simLog;
    }

    //Extract commAction from selectedActionList while remove commAction from s
    //TODO: DEBUG
    private ArrayList<CommAction> readCommActions(ArrayList<CommAction> curCommAction, RunResult aRunResult) {
        for (_SimAction_ simAction : aRunResult.getSelectedActionList()) {
            if (simAction instanceof CommAction) {
                curCommAction.add((CommAction) simAction);
                aRunResult.getSelectedActionList().remove(simAction);
            }
        }
        for (RunResult runResult: aRunResult.getChildRunResults()) {
            curCommAction.addAll(readCommActions(curCommAction,runResult));
        }
        return curCommAction;
    }

    protected ArrayList<SimScenarioEvent> getEvent() {
        for(SimScenarioEvent simScenarioEvent: simScenarioEvents) {

        }
        return new ArrayList<SimScenarioEvent>(0);
    }

    protected void executeEvent(ArrayList<SimScenarioEvent> eventList) {
    }

    protected RunResult runSimulation() {
        return sos.run();
    }

    protected RunResult resolveConflict(RunResult simulationResult) {
        return simulationResult;
    }

    protected UpdateResult updateSimulation(RunResult runResult) {
        return sos.update(runResult);
    }
}
