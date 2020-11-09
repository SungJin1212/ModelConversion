package kr.ac.kaist.se.model.behv;

import CodeGeneration.DataObject.GeoModelDataObject.LocDimensionVar;
import kr.ac.kaist.se.model.abst.cap._SimAction_;

import java.util.ArrayList;

public class MoveAction extends _SimAction_ {

    ArrayList<LocDimensionVar> allowedMovDims;

    public MoveAction(ArrayList<LocDimensionVar> allowedMovDims) {
        this.allowedMovDims = allowedMovDims;
        //diff
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
