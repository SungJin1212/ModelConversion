package kr.ac.kaist.se.model.abst.sys;

import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

abstract public class _SimNonActionableObject_ extends _SimObject_ {
    protected String reference;

    public UpdateResult update(RunResult runResult) {
        return new UpdateResult(this.name);
    }

}
