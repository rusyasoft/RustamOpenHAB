# RustamOpenHAB

This repository contains my public OpenHAB contributions


## How to Make it Work after:

1. Just drop into addons folder after building jar bundle
2. The following line should be added at items file
	
	Switch LifxToggle_1   "lifx bulb"   (Switching) {rustamlifx="1"}
	
3. The following line should be added at sitemap file

	Switch         item=LifxToggle_1       label="LifxBedroom"
	
The above example lines for items and sitemap files shows quick example code

