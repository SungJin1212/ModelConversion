package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.model.abst.sys._SimActionableObject_;

import java.util.ArrayList;

public abstract class EnvElement extends _SimActionableObject_ {

    protected ArrayList<ServiceEntity> observers = new ArrayList<>(0);
    protected SoS sos;
    protected Environment environment;

    public EnvElement(SoS sos, Environment environment) {
        this.sos = sos;
        this.environment = environment;
    }

    public void attachService(ServiceEntity serviceEntity) {
        observers.add(serviceEntity);
    }

    public void removeService(ServiceEntity serviceEntity) {
        observers.remove(serviceEntity);
    }

    abstract public void notifyToServices();

}
