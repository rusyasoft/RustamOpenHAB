package org.openhab.binding.rustamlifx.internal;

import java.util.ArrayList;

import kr.ac.konkuk.dmslab.iot.lifxcontroller.ILifxController;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXHSBKColor;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTarget;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTarget.LFXTargetType;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXDeviceReachability;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXFuzzyPowerState;
import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXPowerState;


import lifx.java.android.light.LFXLight;
import lifx.java.android.network_context.LFXNetworkContext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
//import org.osgi.util.tracker.ServiceTracker;

public class LifxControllerActivator implements BundleActivator, ILifxController {

	private static BundleContext context;
	private ServiceRegistration registration;
	//variables for registering Communication Service
	//private ServiceTracker lifxControllerServiceTracker;
	
	private LifxNonStaticStarter lifxNonStaticStarter;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		LifxControllerActivator.context = bundleContext;

		//UCRManagerActivity registers itself as a UCRManagerService
		registration = context.registerService(ILifxController.class.getName(), this, null);
		
		/* turn off for a while */
		lifxNonStaticStarter = new LifxNonStaticStarter();
		lifxNonStaticStarter.startTheLifx();
		
		
		System.out.println("LifxController>>> LifxController Service Provider is Running !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		LifxControllerActivator.context = null;
		
		/*
		context.ungetService((ServiceReference<?>) registration);
		System.out.println("LifxController>>> LifxController Service Provider is Stopped !");
		*/
	}
	
	//TODO: later one it should be specific light extraction
	private LFXLight getSingleLight()
	{
		LFXNetworkContext networkContext = lifxNonStaticStarter.getLFXNetworkContext();
		if (networkContext != null)
		{
			ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
			System.out.println("allLights.size  = " + allLights.size() );
			if (allLights.size()> 0)
				return allLights.get(0);
		}
		return null;
	}

	@Override
	public void SetPowerState(LFXPowerState lfxPowerState) {

		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setPowerState(lfxPowerState);
		}
		
	}

	@Override
	public LFXPowerState getPowerState() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getPowerState();
		}
		return null;
	}

	@Override
	public LFXTargetType getTargetType() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getTargetType();
		}
		return null;
	}

	@Override
	public String getDeviceID() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getDeviceID();
		}
		return null;
	}

	@Override
	public LFXDeviceReachability getReachability() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getReachability();
		}
		return null;
	}

	@Override
	public LFXFuzzyPowerState getFuzzyPowerState() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getFuzzyPowerState();
		}
		return null;
	}

	@Override
	public void setLabel(String label) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setLabel(label);
		}
	}

	@Override
	public void setColor(LFXHSBKColor color) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setColor(color);
		}
	}

	@Override
	public void setColorOverDuration(LFXHSBKColor color, long duration) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setColorOverDuration(color, duration);
		}
	}

	@Override
	public ArrayList<String> getTags() {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			return light.getTags();
		}
		return null;
	}

	@Override
	public void setTags(ArrayList<String> tags) {
		LFXLight light = getSingleLight();
		if (light != null)
		{	
			light.setTags(tags);
		}
	}

}
