package kr.ac.kaist.se.model.strc;

import kr.ac.kaist.se.model.abst.cap._SimAction_;
import kr.ac.kaist.se.model.abst.sys._SimObject_;
import kr.ac.kaist.se.model.behv.Action;
import kr.ac.kaist.se.model.behv.Task;
import kr.ac.kaist.se.model.geo.Map;
import kr.ac.kaist.se.simdata.output.intermediate.RunResult;
import kr.ac.kaist.se.simdata.output.intermediate.UpdateResult;

import java.lang.reflect.Array;
import java.util.ArrayList;

abstract public class SoS extends _SimObject_ {

    //High-level containers
    protected ArrayList<Organization> orgList;
    protected ArrayList<Infrastructure> infraList;
    protected ArrayList<Environment> envList;

    //All member constituents
    protected ArrayList<CS> csList;

    //All tasks
    protected ArrayList<Task> taskList;

    //Infrastructure entities
    protected ArrayList<SystemEntity> systemEntityList;
    protected ArrayList<ResourceEntity> resourceEntityList;
    protected ArrayList<ServiceEntity> serviceEntityList;

    //Environment entities
    protected ArrayList<ActiveEnvElement> activeEnvElmtList;
    protected ArrayList<PassiveEnvElement> passiveEnvElmtList;

    protected Map map;

    public SoS(String name) {
        this.name = name;
        this.orgList = new ArrayList<Organization>(0);
        this.csList = new ArrayList<CS>(0);
        this.envList = new ArrayList<Environment>(0);
        this.infraList = new ArrayList<>(0);
        this.systemEntityList = new ArrayList<>(0);
        this.serviceEntityList = new ArrayList<>(0);
    }

    public _SimObject_ getSimObject(String id) {
        for(Organization organization : orgList) {
            if(organization.getId().equals(id)) {
                return organization;
            }
        }
        for (Infrastructure infrastructure : infraList) {
            if(infrastructure.getId().equals(id)) {
                return infrastructure;
            }
        }
        for (Environment environment : envList) {
            if(environment.getId().equals(id)) {
                return environment;
            }
        }
        for (CS cs : csList) {
            if(cs.getId().equals(id)) {
                return cs;
            }
        }
        for (SystemEntity systemEntity : systemEntityList) {
            if(systemEntity.getId().equals(id)) {
                return systemEntity;
            }
        }
        for (ResourceEntity resourceEntity : resourceEntityList) {
            if(resourceEntity.getId().equals(id)) {
                return resourceEntity;
            }
        }
        for (ServiceEntity serviceEntity : serviceEntityList) {
            if(serviceEntity.getId().equals(id)) {
                return serviceEntity;
            }
        }
        for (ActiveEnvElement activeEnvElement : activeEnvElmtList) {
            if(activeEnvElement.getId().equals(id)) {
                return activeEnvElement;
            }
        }
        for (PassiveEnvElement passiveEnvElement : passiveEnvElmtList) {
            if(passiveEnvElement.getId().equals(id)) {
                return passiveEnvElement;
            }
        }
        return null;
    }

    public RunResult run(){
        RunResult runResult = new RunResult(this, new ArrayList<_SimAction_>(0));
        for(Environment environment: this.envList) {
            runResult.addChildRunResult(environment.run());
        }

        for(Infrastructure infrastructure: this.infraList) {
            runResult.addChildRunResult(infrastructure.run());
        }

        for(Organization organization: this.orgList) {
            runResult.addChildRunResult(organization.run());
        }

        return runResult;
    }

    public UpdateResult update(RunResult runResult){
        UpdateResult updateResult = new UpdateResult(this.name);
        for(RunResult childRunResult: runResult.getChildRunResults()) {
            if (childRunResult.getTarget() instanceof Organization){
                Organization target = (Organization) childRunResult.getTarget();
                UpdateResult updateResult1 = target.update(childRunResult);
                updateResult.addAllLog(updateResult1.getLog());
            }
            if (childRunResult.getTarget() instanceof Environment){
                Environment target = (Environment) childRunResult.getTarget();
                UpdateResult updateResult1 = target.update(childRunResult);
                updateResult.addAllLog(updateResult1.getLog());
            }
            if (childRunResult.getTarget() instanceof  Infrastructure) {
                Infrastructure target = (Infrastructure) childRunResult.getTarget();
                UpdateResult updateResult1 = target.update(childRunResult);
                updateResult.addAllLog(updateResult1.getLog());
            }
        }
        return updateResult;
    }
    public void addInfra(Infrastructure infrastructure) {infraList.add(infrastructure);}
    public void removeInfra(Infrastructure infrastructure) {infraList.remove(infrastructure);}

    public void addSystemEntity(SystemEntity systemEntity) {systemEntityList.add(systemEntity);}
    public void removeSystemEntity(SystemEntity systemEntity) {systemEntityList.remove(systemEntity);}

    public void addServiceEntity(ServiceEntity serviceEntity) {serviceEntityList.add(serviceEntity);}
    public void removeServiceEntity(ServiceEntity serviceEntity) {serviceEntityList.remove(serviceEntity);}

    public ServiceEntity searchServiceEntity(String name) {
        for (ServiceEntity serviceEntity : this.serviceEntityList) {
            if(serviceEntity.getName().equals(name)) {
                return serviceEntity;
            }
        }
        return null;
    }

    public SystemEntity searchSystemEntity(String name) {
        for (SystemEntity systemEntity : this.systemEntityList) {
            if(systemEntity.getName().equals(name)) {
                return systemEntity;
            }
        }
        return null;
    }


    public void addOrg(Organization organization) {
        orgList.add(organization);
    }

    public void removeOrg(Organization organization) {
        orgList.remove(organization);
    }

    public void addCS(CS cs) {
        csList.add(cs);
    }

    public void removeCS(CS cs) {
        csList.remove(cs);
    }

    public CS searchCS(String name){
        for(CS cs : this.csList){
            if (cs.getName().equals(name)){
                return cs;
            }
        }
        return null;
    }

    public void addEnvironment(Environment environment) {
        this.envList.add(environment);
    }
    public void removeEnvironment(Environment environment) {
        this.envList.remove(environment);
    }

    public void addActiveEnvironment(ActiveEnvElement activeEnvElement) {this.activeEnvElmtList.add(activeEnvElement);}
    public void removeActiveEnvironment(ActiveEnvElement activeEnvElement) {this.activeEnvElmtList.remove(activeEnvElement);}

    public void addPassiveEnvironment(PassiveEnvElement passiveEnvElement) {this.passiveEnvElmtList.add(passiveEnvElement);}
    public void removePassiveEnvironment(PassiveEnvElement passiveEnvElement) {this.passiveEnvElmtList.remove(passiveEnvElement);}

    public Map getMap() {
        return map;
    }

    public ActiveEnvElement searchActiveEnvElement(String name){
        for(Environment environment : this.envList){
            for(ActiveEnvElement activeEnvElement: environment.activeEnvElmtList){
                if (activeEnvElement.getName().equals(name)){
                    return activeEnvElement;
                }
            }
        }
        return null;
    }

    public PassiveEnvElement searchPassiveEnvElement(String name){
        for(Environment environment : this.envList){
            for(PassiveEnvElement passiveEnvElement: environment.passiveEnvElmtList){
                if (passiveEnvElement.getName().equals(name)){
                    return passiveEnvElement;
                }
            }
        }
        return null;
    }

    public void updateMapObjectLocationArrayListHashMap(){
        this.map.clearObjectLocationArrayListHashMap();

        for(CS cs: this.csList){
            this.map.addObjectLocationArrayListHashMap(cs.getLocation(), cs);
        }

        for(Environment environment: this.envList){
            for(PassiveEnvElement passiveEnvElement: environment.passiveEnvElmtList){
                this.map.addObjectLocationArrayListHashMap(passiveEnvElement.getLocation(), passiveEnvElement);
            }
        }

        for (Infrastructure infrastructure : this.infraList) {
            for(SystemEntity systemEntity : infrastructure.systemEntityList) {
                this.map.addObjectLocationArrayListHashMap(systemEntity.getLocation(), systemEntity);
            }
        }
    }
}
