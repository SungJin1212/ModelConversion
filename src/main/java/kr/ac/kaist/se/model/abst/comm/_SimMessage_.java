package kr.ac.kaist.se.model.abst.comm;

import kr.ac.kaist.se.intf.Transmittable;
import kr.ac.kaist.se.model.abst.data._SimData_;
import kr.ac.kaist.se.model.strc.InformationData;

import java.net.ProtocolException;
import java.util.ArrayList;

abstract public class _SimMessage_ implements Transmittable {
    protected String msgId;

    //TODO: other properties of a message
    protected String msgTag; //or EnumMsgTag

    protected EnumMsgType msgType;

    //TODO: properties for communication
    protected String senderId;
    protected String receiverId;

    protected ArrayList<InformationData> msgDataList; //contents

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public EnumMsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(EnumMsgType msgType) {
        this.msgType = msgType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public ArrayList<InformationData> getMsgDataList() {
        return msgDataList;
    }

    public void setMsgDataList(ArrayList<InformationData> msgDataList) {
        this.msgDataList = msgDataList;
    }
}
