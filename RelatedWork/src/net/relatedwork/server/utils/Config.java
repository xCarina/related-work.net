package net.relatedwork.server.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * This is an interface class to the Config file for this project. For each
 * class field one java property must be defined in config.txt. The fields will
 * be automatically filled!
 * 
 * Allowed Types are String, int, boolean, String[] and long[] where arrays are
 * defined by semicolon-separated Strings like "array=a;b;c" boolen fields are
 * initialized with true or false
 * 
 * lines starting with # will be ignored and can serve as comments
 * 
 * @author Jonas Kunze, Rene Pickhardt
 * 
 */
public class Config extends Properties {
	public String neo4jDbPath; 
	public int pageRankIterations;
	public boolean createSearchIndex;
	private static final long serialVersionUID = -4439565094382127683L;

	// by Heinrich
	public String autoCompleteFile;

	static Config instance = null;

	public Config() {
		// config.txt should lie in package root folder i.e. RelatedWork in this case
		String file = "/home/heinrich/Desktop/eclipse_related-work/RelatedWork/config.txt";
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(file));
			this.load(stream);
			stream.close();
		} catch (IOException e) {
			System.out.println("the file: " + file + " does not exist in you current project folder. make sure it exists and has entries according to the class in CalculateMetricsOnPaperGraph/src/utils/Config.java\n\n one such entry coudld be:\n neo4jDbPath = /var/lib/datasets/... \n\n the file will respect comments starting with a #.");
			e.printStackTrace();
			System.exit(1);
		}
		try {
			this.initialize();
		} catch (IllegalArgumentException e) {
			System.out.println("the file: " + file + " does not have the argument you where just accessing. make sure all public fields of this class in CalculateMetricsOnPaperGraph/src/utils/Config.java are entries in that file one such entry coudld be:\n neo4jDbPath = /var/lib/datasets/... \n\n the file will respect comments starting with a #.");
			System.exit(1);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills all fields with the data defined in the config file.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void initialize() throws IllegalArgumentException,
			IllegalAccessException {
		Field[] fields = this.getClass().getFields();
		for (Field f : fields) {
			if (this.getProperty(f.getName()) == null) {
				System.err.print("Property '" + f.getName()
						+ "' not defined in config file");
			}
			if (f.getType().equals(String.class)) {
				f.set(this, this.getProperty(f.getName()));
			} else if (f.getType().equals(long.class)) {
				f.setLong(this, Long.valueOf(this.getProperty(f.getName())));
			} else if (f.getType().equals(int.class)) {
				f.setInt(this, Integer.valueOf(this.getProperty(f.getName())));
			} else if (f.getType().equals(boolean.class)) {
				f.setBoolean(this,
						Boolean.valueOf(this.getProperty(f.getName())));
			} else if (f.getType().equals(String[].class)) {
				f.set(this, this.getProperty(f.getName()).split(";"));
			} else if (f.getType().equals(int[].class)) {
				String[] tmp = this.getProperty(f.getName()).split(";");
				int[] ints = new int[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					ints[i] = Integer.parseInt(tmp[i]);
				}
				f.set(this, ints);
			} else if (f.getType().equals(long[].class)) {
				String[] tmp = this.getProperty(f.getName()).split(";");
				long[] longs = new long[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					longs[i] = Long.parseLong(tmp[i]);
				}
				f.set(this, longs);
			}
		}
	}

	public static Config get() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
}