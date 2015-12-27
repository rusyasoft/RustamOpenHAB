package kr.ac.konkuk.dmslab.iot.lifxcontroller;

import java.util.ArrayList;

import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTarget.LFXTargetType;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXDeviceReachability;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXFuzzyPowerState;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXPowerState;

public interface ILifxController {

	/*
	public enum LFXPowerState { ON, OFF,}
	public enum LFXTargetType
    {
         BROADCAST, DEVICE, TAG,
    };
    
	public enum LFXDeviceReachability
	{
		REACHABLE,
		UNREACHABLE,
	};
	
	public enum LFXFuzzyPowerState
	{
		OFF,
		ON,
		MIXED,
	};
	*/
	
	public void SetPowerState(LFXPowerState lfxPowerState);
    public LFXPowerState getPowerState();

    public LFXTargetType getTargetType();
    //public ArrayList <LFXTaggedLightCollection> getTaggedCollections();
    
    public String getDeviceID();

    public LFXDeviceReachability getReachability();

    public LFXFuzzyPowerState getFuzzyPowerState();
    


    public void setLabel( String label);

    public void setColor( LFXHSBKColor color);

    public void setColorOverDuration( LFXHSBKColor color, long duration);


    //public static LFXLight lightWithDeviceID( String deviceID, LFXNetworkContext networkContext);


    public ArrayList<String> getTags();


    public void setTags( ArrayList<String> tags);


}
