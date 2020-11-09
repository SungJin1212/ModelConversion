package kr.ac.kaist.se.model.abst.sys;

import kr.ac.kaist.se.intf.Simulatable;
import kr.ac.kaist.se.model.abst.comm._SimMessage_;
import kr.ac.kaist.se.model.geo.ObjectLocation;

import java.util.LinkedList;
import java.util.Queue;

abstract public class _SimObject_ implements Simulatable {
    protected String id;
    protected String name;
    protected boolean isAvailable;
    protected boolean isActivated;

    protected ObjectLocation location;

    protected Queue<_SimMessage_> msgQueue = new LinkedList<_SimMessage_>();

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public void setLocation(ObjectLocation location) {
        this.location = location;
    }

    public Queue<_SimMessage_> getMsgQueue() {
        return msgQueue;
    }

    public void setMsgQueue(Queue<_SimMessage_> msgQueue) {
        this.msgQueue = msgQueue;
    }

    public String getId() {
        return id;
    }

    public ObjectLocation getLocation(){
        return this.location;
    }
}
