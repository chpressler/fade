package com.github.chpressler.fade;

import java.util.ResourceBundle;

public class ResourceHandler {
	
	private static volatile ResourceHandler instance = null;
	
	private ResourceBundle strings;
	
	private ResourceBundle icons;
	
	private ResourceHandler() {
		strings = ResourceBundle.getBundle("strings.Strings");
		icons = ResourceBundle.getBundle("icons.Icons");
	}
	
	public static synchronized ResourceHandler getInstance() {
		if(instance == null) {
			synchronized(ResourceHandler.class) {
				if(instance == null) {
					instance = new ResourceHandler();
				}
			}
		}
		return instance;
	}

	public ResourceBundle getStrings() {
		return strings;
	}

	public ResourceBundle getIcons() {
		return icons;
	}

}