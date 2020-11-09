package kr.ac.kaist.se.model.abst.cap;

import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.model.strc.SoS;

abstract public class _SimAction_ {
    protected SoS sos;
    protected _SimActionableObject_ actionSubject;

    protected String id;
    protected String tag;
    protected String name;


    protected int duration;
    protected float cost;
    protected float benefit;

    abstract protected float calcUtility();

    abstract public void executeAction(); //TODO: set return

    abstract protected boolean checkPrecondition(); //TODO: where to define the precondition

    public String getName() {
        return name;
    }
}
