/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.rustamlifx.internal;

import java.util.Dictionary;
import java.util.Map;

import kr.ac.konkuk.dmslab.iot.lifxcontroller.LFXTypes.LFXPowerState;

import org.openhab.binding.rustamlifx.rustamlifxBindingProvider;

import org.apache.commons.lang.StringUtils;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implement this class if you are going create an actively polling service
 * like querying a Website/Device.
 * 
 * @author RustamRakhimov
 * @since 1.0.0
 */
public class rustamlifxBinding extends AbstractActiveBinding<rustamlifxBindingProvider> implements ManagedService {

	private static final Logger logger = 
		LoggerFactory.getLogger(rustamlifxBinding.class);
	
	private LifxNonStaticStarter lifxNonStaticStarter;

	/**
	 * The BundleContext. This is only valid when the bundle is ACTIVE. It is set in the activate()
	 * method and must not be accessed anymore once the deactivate() method was called or before activate()
	 * was called.
	 */
	private BundleContext bundleContext;

	
	/** 
	 * the refresh interval which is used to poll values from the rustamlifx
	 * server (optional, defaults to 60000ms)
	 */
	private long refreshInterval = 60000;
	
	
	public rustamlifxBinding() {
	}
		
	
	/**
	 * Called by the SCR to activate the component with its configuration read from CAS
	 * 
	 * @param bundleContext BundleContext of the Bundle that defines this component
	 * @param configuration Configuration properties for this component obtained from the ConfigAdmin service
	 */
	public void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
		this.bundleContext = bundleContext;

		// the configuration is guaranteed not to be null, because the component definition has the
		// configuration-policy set to require. If set to 'optional' then the configuration may be null
		
			
		// to override the default refresh interval one has to add a 
		// parameter to openhab.cfg like <bindingName>:refresh=<intervalInMs>
		String refreshIntervalString = (String) configuration.get("refresh");
		if (StringUtils.isNotBlank(refreshIntervalString)) {
			refreshInterval = Long.parseLong(refreshIntervalString);
		}

		// read further config parameters here ...

		setProperlyConfigured(true);
		
		/// initialize LifxNonstaticStarter
		lifxNonStaticStarter = new LifxNonStaticStarter();
		lifxNonStaticStarter.startTheLifx();
		
		if (lifxNonStaticStarter == null)
		{
			System.out.println("LifxBindingBundle >>> LifxController failed to start !!!");
		}
		else
		{
			System.out.println("LifxBindingBundle >>> LifxBundle started !!!");
			lifxNonStaticStarter.SetPowerState(LFXPowerState.ON);
		}
	}
	
	/**
	 * Called by the SCR when the configuration of a binding has been changed through the ConfigAdmin service.
	 * @param configuration Updated configuration properties
	 */
	public void modified(final Map<String, Object> configuration) {
		// update the internal configuration accordingly
	}
	
	/**
	 * Called by the SCR to deactivate the component when either the configuration is removed or
	 * mandatory references are no longer satisfied or the component has simply been stopped.
	 * @param reason Reason code for the deactivation:<br>
	 * <ul>
	 * <li> 0 – Unspecified
     * <li> 1 – The component was disabled
     * <li> 2 – A reference became unsatisfied
     * <li> 3 – A configuration was changed
     * <li> 4 – A configuration was deleted
     * <li> 5 – The component was disposed
     * <li> 6 – The bundle was stopped
     * </ul>
	 */
	public void deactivate(final int reason) {
		this.bundleContext = null;
		// deallocate resources here that are no longer needed and 
		// should be reset when activating this binding again
		
		
		if (lifxNonStaticStarter == null)
		{
			System.out.println("LifxBindingBundle >>> LifxController is unavailable !!!");
		}
		else
		{
			lifxNonStaticStarter.SetPowerState(LFXPowerState.OFF);
		}
	}

	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected long getRefreshInterval() {
		return refreshInterval;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected String getName() {
		return "rustamlifx Refresh Service";
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void execute() {
		// the frequently executed code (polling) goes here ...
		logger.debug("execute() method is called!");
		if (lifxNonStaticStarter != null)
		{
			logger.debug("execute(): lifxStarter does exist!");
		}
		else
		{
			logger.debug("execute(): lifxStarter does not exist!");
		}
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
		// the code being executed when a command was sent on the openHAB
		// event bus goes here. This method is only called if one of the 
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveCommand({},{}) is called!", itemName, command);
		
		logger.debug("Lifx binding received command '" + command
				+ "' for item '" + itemName + "'");

		if (lifxNonStaticStarter != null) {
			//computeCommandForItemOnBridge(command, itemName, lifxNonStaticStarter);
			if (command instanceof OnOffType) {
				logger.debug("Lifx received OnOffType command, value = "+ command);
				if (OnOffType.ON.equals(command))
					lifxNonStaticStarter.SetPowerState(LFXPowerState.ON);
				if (OnOffType.OFF.equals(command))
					lifxNonStaticStarter.SetPowerState(LFXPowerState.OFF);
			}
			
		} else {
			logger.warn("Lifx binding skipped command because no Lifx bulb is connected.");
		}
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		// the code being executed when a state was sent on the openHAB
		// event bus goes here. This method is only called if one of the 
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveUpdate({},{}) is called!", itemName, newState);
	}


	@Override
	public void updated(Dictionary config)
			throws ConfigurationException {
		if (config != null)
		{
			
		}
		
	}

}
