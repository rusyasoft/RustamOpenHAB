/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.rustamlifx.internal;

import org.openhab.binding.rustamlifx.internal.rustamlifxGenericBindingProvider;
import org.openhab.binding.rustamlifx.internal.LifxBindingConfig;
import org.openhab.binding.rustamlifx.internal.LifxBindingConfig.BindingType;
import org.openhab.binding.rustamlifx.rustamlifxBindingProvider;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.ColorItem;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is responsible for parsing the binding configuration.
 * 
 * @author RustamRakhimov
 * @since 1.0.0
 */
public class rustamlifxGenericBindingProvider extends AbstractGenericBindingProvider implements rustamlifxBindingProvider {

	static final Logger logger = LoggerFactory
			.getLogger(rustamlifxGenericBindingProvider.class);
	
	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "rustamlifx";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		if (!(item instanceof SwitchItem || item instanceof DimmerItem)) {
			throw new BindingConfigParseException("item '" + item.getName()
					+ "' is of type '" + item.getClass().getSimpleName()
					+ "', only Switch- and DimmerItems are allowed - please check your *.items configuration");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);
		rustamlifxBindingConfig config = new rustamlifxBindingConfig();
		
		//parse bindingconfig here ...
		
		//rustamchange//orig//addBindingConfig(item, config);
		//rustamchange// 
		try {

			if (bindingConfig != null) {

				String[] configParts = bindingConfig.split(";");

				if (item instanceof ColorItem) {
					BindingConfig lifxBindingConfig = (BindingConfig) new LifxBindingConfig(
							configParts[0], BindingType.rgb.name(), null);
					addBindingConfig(item, lifxBindingConfig);
				} else if (item instanceof DimmerItem) {
					BindingConfig lifxBindingConfig = (BindingConfig) new LifxBindingConfig(
							configParts[0], configParts.length < 2 ? null
									: configParts[1],
							configParts.length < 3 ? null : configParts[2]);
					addBindingConfig(item, lifxBindingConfig);
				} else if (item instanceof SwitchItem) {
					BindingConfig lifxBindingConfig = (BindingConfig) new LifxBindingConfig(
							configParts[0], BindingType.switching.name(), null);
					addBindingConfig(item, lifxBindingConfig);
				}

			} else {
				logger.warn("bindingConfig is NULL (item=" + item
						+ ") -> processing bindingConfig aborted!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.warn("bindingConfig is invalid (item=" + item
					+ ") -> processing bindingConfig aborted!");
		}
	}
	
	
	/**
	 * This is a helper class holding binding specific configuration details
	 * 
	 * @author RustamRakhimov
	 * @since 1.0.0
	 */
	class rustamlifxBindingConfig implements BindingConfig {
		// put member fields here which holds the parsed values
	}
	
	
}
