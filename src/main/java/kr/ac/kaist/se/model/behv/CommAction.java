package kr.ac.kaist.se.model.behv;

import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;
import kr.ac.kaist.se.model.strc.Message;
import kr.ac.kaist.se.model.strc.SoS;

public class CommAction extends _SimAction_ {

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public CommAction(Message message) {
        this.message = message;

    }

    public CommAction(SoS sos, _SimActionableObject_ actionSubject, Message message) {
        this.sos = sos;
        this.actionSubject = actionSubject;
        this.message = message;
    }

    @Override
    protected float calcUtility() {
        return 0;
    }

    @Override
    public void executeAction() {

    }

    @Override
    protected boolean checkPrecondition() {
        return false;
    }
}
