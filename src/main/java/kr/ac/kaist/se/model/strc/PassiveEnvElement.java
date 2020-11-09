package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.model.abst.sys._SimNonActionableObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

public abstract class PassiveEnvElement extends EnvElement {


    public PassiveEnvElement(SoS sos, Environment environment){
        super(sos,environment);

        sos.addPassiveEnvironment(this);
        environment.addPassiveEnvElement(this);
    }

    public RunResult run() {
        return new RunResult(this, null);
    }

//    public UpdateResult update(RunResult runResult) {
//        return new UpdateResult(this.name);
//    }
}
