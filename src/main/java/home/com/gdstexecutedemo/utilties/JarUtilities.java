package home.com.gdstexecutedemo.utilties;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JarUtilities {
	private static final Logger logger = LoggerFactory.getLogger(JarUtilities.class);
	
	public static ArrayList<String> getClassNamesFromJar(String jarPath) throws Exception {
	    return getClassNamesFromJar(new JarInputStream(JarUtilities.class.getClassLoader().getResourceAsStream(jarPath)));
	}

	public static ArrayList<String> getClassNamesFromJar(JarInputStream jarFile) throws Exception {
	    ArrayList<String> classNames = new ArrayList<>();
	    try {
	        JarEntry jar;
	        while (true) {
	            jar = jarFile.getNextJarEntry();
	            if (jar == null) {
	                break;
	            }
	            //Pick file that has the extension of .class
	            if ((jar.getName().endsWith(".class"))) {
	                String className = jar.getName().replaceAll("/", "\\.");
	                String myClass = className.substring(0, className.lastIndexOf('.'));
	                classNames.add(myClass);
	                
	                //Class clazz = Class.forName(className);
	                //logger.info(clazz.getCanonicalName());
	            }
	        }
	    } catch (Exception e) {
	        throw new Exception("Error while getting class names from jar", e);
	    }
	    return classNames;
	}
	 
	public static Object getObjectFromJar(String jarPath, String className) throws Exception {
		String pathStr = "C:\\Users\\jdugger01\\source\\Spring\\gdst-execute-demo\\src\\main\\resources\\kjars";
		String jarName = "demo-0001-1.0.2-20211222.232048-1.jar";
		
		URL classUrl = new URL("file:///" + pathStr + "//" + jarName);
        URL[] classUrls = { classUrl };
        URLClassLoader ucl = new URLClassLoader(classUrls);
        Class<?> beanClass = ucl.loadClass(className);
        Constructor<?> constructor = beanClass.getConstructor();
        Object beanObj = constructor.newInstance();
        return beanObj;
        
	}	
	
	
}
