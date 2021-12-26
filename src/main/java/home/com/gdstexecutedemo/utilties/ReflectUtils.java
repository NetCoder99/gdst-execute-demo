package home.com.gdstexecutedemo.utilties;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtils {
	private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);
	
	public static String findGetMethods(Object tmpObj) {
		List<Method> ruleMethods = Arrays.asList(tmpObj.getClass().getMethods());
		List<Method> ruleGetMethods = ruleMethods
			 .stream()
			 .filter(f -> f.getName().startsWith("get"))
			 .collect(Collectors.toList());
		
		for(Method method : ruleGetMethods) {
			logger.info(method.getName());
		}
		return "test";
	}

}
