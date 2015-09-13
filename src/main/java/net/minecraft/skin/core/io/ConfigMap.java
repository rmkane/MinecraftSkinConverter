package net.minecraft.skin.core.io;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.skin.core.util.FileUtils;

public class ConfigMap {
	private Map<String, String> map;
	
	public ConfigMap(String configDirectory, String... names) {
		map = new HashMap<String, String>();
		
		for (String name : names) {
			map.put(name, FileUtils.toPath(configDirectory, name + ".conf"));
		}
	}
	
	public ConfigMap(String configDirectory) {
		this(configDirectory, "reader", "writer1", "writer2");
	}
	
	public String getConfig(String name) {
		return map.get(name);
	}
}
