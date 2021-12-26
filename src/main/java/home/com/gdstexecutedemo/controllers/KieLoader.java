package home.com.gdstexecutedemo.controllers;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import home.com.gdstexecutedemo.models.KjarProperties;

public class KieLoader {
	private static final Logger logger = LoggerFactory.getLogger(KieLoader.class);

	private KieServices  kieServices = KieServices.Factory.get();
	public  KieContainer kieContainer = null;

//	public KieLoader(String kjarPath, String version) {
////		KieBuilder kieBuilder = null;
////		try {
////			Path path = Paths.get("c:\\Users\\jdugger01\\source\\Spring\\gdst-execute-demo\\src\\main\\resources\\kjars\\demo-0001-1.0.3-20211224.162409-1.jar");
////			logger.info(Files.exists(path)? "true" : "false");
////			URL            kjarUrl = path.toUri().toURL();
////		    ClassLoader    cl      = Thread.currentThread().getContextClassLoader();
////		    URLClassLoader urlClassLoader = new URLClassLoader( new URL[]{kjarUrl} );
////		    Thread.currentThread().setContextClassLoader( urlClassLoader );
////
////		    KieRepository kieRepository = kieServices.getRepository();
////	        ReleaseId releaseId = getReleaseId(version);
////	        KieModule kieModule = kieRepository.getKieModule( releaseId );
////
////		    //kieBuilder = kieServices.newKieBuilder(getKieFileSystem(kjarPath));
////			//kieBuilder.buildAll();
////			kieContainer = kieServices.newKieContainer(getReleaseId(version));
////			
////			//KieModule kieModule = kieContainer.getKieBase()
////			logger.info(kieContainer.toString());
////		}
////		catch(Exception ex) {
////			logger.error(ex.getLocalizedMessage(), ex);
////		}
//	}

	public KieContainer loadKjarFromClasspath(KjarProperties kjarProps) throws Exception {
		URL resource = KieLoader.class.getResource(kjarProps.getPath());
		File jarFile = Paths.get(resource.toURI()).toFile();
		if (!jarFile.exists()) {
			logger.error("KJar not found: " + kjarProps.getPath());
			throw new Exception("KJar not found: " + kjarProps.getPath());
		}
		
	    ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    URLClassLoader urlClassLoader = new URLClassLoader( new URL[]{resource} );
	    Thread.currentThread().setContextClassLoader( urlClassLoader );

	    try {
	        //KieServices   ks = KieServices.Factory.get();
	        KieRepository kieRepository = kieServices.getRepository();
	        ReleaseId     releaseId     = kieServices.newReleaseId("com.myspace", "demo-0001", kjarProps.getVersion());
	        KieModule     kieModule     = kieRepository.getKieModule( releaseId );
			logger.info(kieModule.getReleaseId().toString());
			KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem(kjarProps.getPath()));
			kieBuilder.buildAll();
			return kieServices.newKieContainer(getReleaseId(kjarProps.getVersion()));
	    } finally {
	        Thread.currentThread().setContextClassLoader( cl );
	    }
	}
	
	
	public KieSession getKieSession() {
		return this.kieContainer.newKieSession();
	}

	public KieFileSystem getKieFileSystem(String kjarPath) {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource(kjarPath));
		return kieFileSystem;
	}
	
	public ReleaseId getReleaseId(String version) {
		return kieServices.newReleaseId("com.myspace", "demo-0001", version);
	}
	
	public List<String> getFactDefinitions(KieContainer kieContainer) {
		List<String> rtnList = new ArrayList<>();
		for (String kieBaseName : kieContainer.getKieBaseNames()) {
			KieBase kieBase = kieContainer.getKieBase(kieBaseName);
			logger.info("+" + kieBaseName);
			for (KiePackage kiePackage : kieBase.getKiePackages()) {
				logger.info("++" + kiePackage.getName());
				for (Rule rule: kiePackage.getRules()) {
					String agendaGroup = getAgendaGroup(rule);
					logger.info("+++" + agendaGroup + ":" +rule.getName());
					rtnList.add(rule.getName());
				}
			}
		}		
		return rtnList;
	}

	public List<String> getRuleDefinitions(KieContainer kieContainer) {
		List<String> rtnList = new ArrayList<>();
		for (String kieBaseName : kieContainer.getKieBaseNames()) {
			KieBase kieBase = kieContainer.getKieBase(kieBaseName);
			logger.info("+" + kieBaseName);
			for (KiePackage kiePackage : kieBase.getKiePackages()) {
				logger.info("++" + kiePackage.getName());
				for (Rule rule: kiePackage.getRules()) {
					String agendaGroup = getAgendaGroup(rule);
					logger.info("+++" + agendaGroup + ":" +rule.getName());
					rtnList.add(rule.getName());
				}
			}
		}		
		return rtnList;
	}

	public String getAgendaGroup(Rule rule) {
		List<Method> ruleMethods = Arrays.asList(rule.getClass().getMethods());
		List<Method> ruleGetMethods = ruleMethods
			 .stream()
			 .filter(f -> f.getName().startsWith("get"))
			 .collect(Collectors.toList());
		
		String activationGroup = "";
		String ruleFlowGroup = "";
		String agendaGroup = "";
		
		for(Method method : ruleGetMethods) {
			//logger.info(method.getName());
			if(method.getName().contains("ActivationGroup")) {
				activationGroup = getMethodValue(rule, method); 
			}
			if(method.getName().contains("AgendaGroup")) {
				agendaGroup = getMethodValue(rule, method); 
			}
			if(method.getName().contains("RuleFlowGroup")) {
				ruleFlowGroup = getMethodValue(rule, method); 
			}
		}
		return agendaGroup;
	}

	private String getMethodValue(Rule rule, Method method) {
		String rtnValue = "";
		try {
			rtnValue = (String) method.invoke(rule); 
		}
		catch(Exception ex){
			logger.error(ex.getLocalizedMessage());
		}
		return rtnValue;
		
	}
}
