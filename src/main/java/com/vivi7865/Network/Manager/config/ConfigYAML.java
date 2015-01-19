package com.vivi7865.Network.Manager.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.vivi7865.Network.Manager.NetworkManager;

public class ConfigYAML {

	Yaml yaml;
	File file = new File("config.yml");
	NetworkManager manager;
	
	private Map<String, Object> config;
	
	public ConfigYAML(NetworkManager manager) {
		this.manager = manager;		 
	}
	
	@SuppressWarnings("unchecked")
	public boolean load() {
		
		try {
			file.createNewFile();
					
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(options);
					
			config = (Map<String, Object>) yaml.load(new FileInputStream(file));
			
			if (config == null) config = new HashMap<String, Object>();
								
		} catch (IOException e) {
			manager.getLogger().severe(e.getMessage());
			return false;
		}
		return true;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T get(String path, T defaultValue, Map map) {
		
		if (path.indexOf(".") != -1) {
						
			String[] paths = path.split("\\.");
			Map tempMap = (Map) map.get(paths[0]);
			
			if (tempMap == null) {
				tempMap = new HashMap<>();
				map.put(paths[0], tempMap);
			}
			
			return get(paths[1], defaultValue, tempMap);
			
		}
		
		Object value = map.get(path);
		
		if (value == null && defaultValue != null) {
			value = defaultValue;
			map.put(path, value);
			saveConfig();
		}
		
		return (T) value;
		
	}
	
	public <T> T get(String path, T defaultValue) {
		return get(path, defaultValue, config);
	}
	
	public <T> T get(String path) {
		return get(path, null, config);
	}
	
	public int getInt(String path, int defaultValue) {
		return get(path, defaultValue);
	}
	
	public String getString(String path, String defaultValue) {
		return get(path, defaultValue);
	}
	
	public Boolean getBool(String path, Boolean defaultValue) {
		return get(path, defaultValue);
	}
	
	public void saveConfig() {
		
		try {
			FileWriter writer = new FileWriter(file);
			yaml.dump(config, writer);
		} catch (IOException e) {
			manager.getLogger().severe(e.getMessage());
		}
		
	}
	
}
