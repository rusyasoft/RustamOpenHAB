package org.openhab.binding.rustamlifx.internal;

import java.util.ArrayList;

import kr.ac.konkuk.dmslab.iot.lifxcontroller.ILifxController;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXHSBKColor;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTarget.LFXTargetType;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXDeviceReachability;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXFuzzyPowerState;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXPowerState;
import kr.dms.iot.lifx.desktop.imitating.Context;
import lifx.java.android.client.LFXClient;
import lifx.java.android.light.LFXLight;
import lifx.java.android.light.LFXLight.LFXLightListener;
import lifx.java.android.light.LFXLightCollection;
import lifx.java.android.light.LFXLightCollection.LFXLightCollectionListener;
import lifx.java.android.light.LFXTaggedLightCollection;
import lifx.java.android.network_context.LFXNetworkContext;
import lifx.java.android.network_context.LFXNetworkContext.LFXNetworkContextListener;

public class LifxNonStaticStarter implements LFXLightListener, LFXLightCollectionListener, LFXNetworkContextListener, ILifxController  {
	
	private LFXNetworkContext networkContext;
	private Context imitationContext;
	

	public int startTheLifx()
	{
		imitationContext = new Context();
		networkContext = LFXClient.getSharedInstance(imitationContext ).getLocalNetworkContext();
		networkContext.connect();
		
		// from power activity
		networkContext.addNetworkContextListener( this);
		networkContext.getAllLightsCollection().addLightCollectionListener( this);
		
		/*
		ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("allLights.size  = " + allLights.size() );
		*/
		return 0;
	}

	@Override
	public void networkContextDidConnect(LFXNetworkContext networkContext) {
		// TODO Auto-generated method stub
		System.out.println("networkContextDidConnect was Triggered");
	}

	@Override
	public void networkContextDidDisconnect(LFXNetworkContext networkContext) {
		// TODO Auto-generated method stub
		System.out.println("networkContextDidDisconnect was Triggered");
	}

	@Override
	public void networkContextDidAddTaggedLightCollection(
			LFXNetworkContext networkContext,
			LFXTaggedLightCollection collection) {
		// TODO Auto-generated method stub
		
		System.out.println("networkContextDidAddTaggedLightCollection was Triggered");
	}

	@Override
	public void networkContextDidRemoveTaggedLightCollection(
			LFXNetworkContext networkContext,
			LFXTaggedLightCollection collection) {
		// TODO Auto-generated method stub
		System.out.println("networkContextDidRemoveTaggedLightCollection was Triggered");
	}

	@Override
	public void lightCollectionDidAddLight(LFXLightCollection lightCollection,
			LFXLight light) {
		// TODO Auto-generated method stub
		System.out.println("lightCollectionDidAddLight was Triggered");
		/*
		ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
		System.out.println("allLights.size  = " + allLights.size() );
		allLights.get(0).setPowerState(LFXPowerState.ON);
		
		LFXHSBKColor color = LFXHSBKColor.getColor( (float) (0.05 * 360), 1.0f, 0.2f, 3500);
		allLights.get(0).setColor(color);
		*/
		
	}
	
	public LFXNetworkContext getLFXNetworkContext()
	{
		return networkContext;
	}
	
	public void turnItOFF()
	{
		ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
		System.out.println("allLights.size  = " + allLights.size() );
		allLights.get(0).setPowerState(LFXPowerState.OFF);
	}

	@Override
	public void lightCollectionDidRemoveLight(
			LFXLightCollection lightCollection, LFXLight light) {
		// TODO Auto-generated method stub
		System.out.println("lightCollectionDidRemoveLight was Triggered");
	}

	@Override
	public void lightCollectionDidChangeLabel(
			LFXLightCollection lightCollection, String label) {
		// TODO Auto-generated method stub
		System.out.println("lightCollectionDidChangeLabel was Triggered");
	}

	@Override
	public void lightCollectionDidChangeColor(
			LFXLightCollection lightCollection, LFXHSBKColor color) {
		// TODO Auto-generated method stub
		System.out.println("lightCollectionDidChangeColor was Triggered");
	}

	@Override
	public void lightCollectionDidChangeFuzzyPowerState(
			LFXLightCollection lightCollection,
			LFXFuzzyPowerState fuzzyPowerState) {
		// TODO Auto-generated method stub
		System.out.println("lightCollectionDidChangeFuzzyPowerState was Triggered");
	}

	@Override
	public void lightDidChangeLabel(LFXLight light, String label) {
		// TODO Auto-generated method stub
		System.out.println("lightDidChangeLabel was Triggered");
	}

	@Override
	public void lightDidChangeColor(LFXLight light, LFXHSBKColor color) {
		// TODO Auto-generated method stub
		System.out.println("lightDidChangeColor was Triggered");
	}

	@Override
	public void lightDidChangePowerState(LFXLight light,
			LFXPowerState powerState) {
		// TODO Auto-generated method stub
		System.out.println("lightDidChangePowerState was Triggered");
	}
	
	///// ILifxController implementations
	//TODO: later one it should be specific light extraction
	private LFXLight getSingleLight()
	{
		LFXNetworkContext networkContext = this.getLFXNetworkContext();
		if (networkContext != null)
		{
			ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
			System.out.println("allLights.size  = " + allLights.size() );
			if (allLights.size()> 0)
				return allLights.get(0);
		}
		return null;
	}

		
	public void SetPowerState(LFXPowerState lfxPowerState) {

		LFXLight light = getSingleLight();
		if (light != null)
		{
			System.out.println("LifxBindingBundle >>> Light Was Found");
			light.setPowerState(lfxPowerState);
		}
		else
			System.out.println("LifxBindingBundle >>> No Light Was Found");
		
	}

	
	public LFXPowerState getPowerState() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getPowerState();
		}
		return null;
	}

	
	public LFXTargetType getTargetType() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getTargetType();
		}
		return null;
	}

	
	public String getDeviceID() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getDeviceID();
		}
		return null;
	}

	
	public LFXDeviceReachability getReachability() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getReachability();
		}
		return null;
	}

	
	public LFXFuzzyPowerState getFuzzyPowerState() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getFuzzyPowerState();
		}
		return null;
	}

	
	public void setLabel(String label) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setLabel(label);
		}
	}

	
	public void setColor(LFXHSBKColor color) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setColor(color);
		}
	}

	
	public void setColorOverDuration(LFXHSBKColor color, long duration) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setColorOverDuration(color, duration);
		}
	}

	
	public ArrayList<String> getTags() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getTags();
		}
		return null;
	}

	
	public void setTags(ArrayList<String> tags) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setTags(tags);
		}
	}

}
