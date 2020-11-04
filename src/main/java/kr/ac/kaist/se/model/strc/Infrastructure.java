package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimObject_;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.util.ArrayList;

abstract public class Infrastructure extends _SimObject_ {

    protected SoS soS;
    protected EnumInfraType infraType;

    protected ArrayList<SystemEntity> systemEntityList;
    protected ArrayList<ServiceEntity> serviceEntityList;
    protected ArrayList<ResourceEntity> resourceEntityList;

    public Infrastructure(String name, SoS soS) {
        this.name = name;
        this.soS = soS;
        soS.addInfra(this);

        this.systemEntityList = new ArrayList<>(0);
        this.serviceEntityList = new ArrayList<>(0);
        this.resourceEntityList = new ArrayList<>(0);
    }

    public RunResult run() {
        RunResult runResult = new RunResult(this, new ArrayList<_SimAction_>(0));

        for (SystemEntity systemEntity : this.systemEntityList) {
            runResult.addChildRunResult(systemEntity.run());
        }
        for (ServiceEntity serviceEntity : this.serviceEntityList) {
            runResult.addChildRunResult(serviceEntity.run());
        }
        return runResult;
    }

    public UpdateResult update(RunResult runResult) {
        UpdateResult updateResult = new UpdateResult(this.name);

        for(RunResult childRunResult: runResult.getChildRunResults()) {
            if (childRunResult.getTarget() instanceof SystemEntity){
                SystemEntity target = (SystemEntity) childRunResult.getTarget();
                UpdateResult updateResult1 = target.update(childRunResult);
                updateResult.addAllLog(updateResult1.getLog());
            }
            else if (childRunResult.getTarget() instanceof ServiceEntity) {
                ServiceEntity target = (ServiceEntity) childRunResult.getTarget();
                UpdateResult updateResult1 = target.update(childRunResult);
                updateResult.addAllLog(updateResult1.getLog());
            }
        }
        return updateResult;
    }

    public void addSystemEntity(SystemEntity systemEntity) {
        this.systemEntityList.add(systemEntity);
    }

    public void removeSystemEntity(SystemEntity systemEntity) {
        this.systemEntityList.remove(systemEntity);
    }

    public void addServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntityList.add(serviceEntity);
    }

    public void removeServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntityList.remove(serviceEntity);
    }
}
