package home.com.gdstexecutedemo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import home.com.gdstexecutedemo.logging.KieAgendaEventListener;
import home.com.gdstexecutedemo.logging.ProcessEventListener;
import home.com.gdstexecutedemo.models.KjarProperties;
import home.com.gdstexecutedemo.utilties.JarUtilities;
import home.com.gdstexecutedemo.utilties.JsonUtilities;

import com.myspace.demo_facts.BaseFact;

//import com.myspace.demo_0001.BaseFact;

@RestController
@RequestMapping("/")
public class HomeController<T> {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	HashMap<String, KjarProperties> kJarVersions = new HashMap<String, KjarProperties>(); 
	
	public HomeController() {
		kJarVersions.put("1.1.0-SNAPSHOT", new KjarProperties("/kjars/demo-0001-1.1.0-20211226.182721-1.jar", "1.1.0-SNAPSHOT" ));
		kJarVersions.put("1.1.1-SNAPSHOT", new KjarProperties("/kjars/demo-0001-1.1.1-20211226.191257-1.jar", "1.1.1-SNAPSHOT" ));
		
	}
	//String kjarPath = "kjars/demo-0001-1.0.2-20211222.232048-1.jar";
	//String kjarVers = "1.0.2-SNAPSHOT";

	String kjarPath = "/kjars/demo-0001-1.1.0-20211226.182721-1.jar";
	String kjarVers = "1.1.0-SNAPSHOT";
	
	//String kjarPath_1_1_1 = "/kjars/demo-0001-1.1.1-20211226.191257-1.jar";
	//String kjarVers_1_1_1 = "1.1.1-SNAPSHOT";

	//"/kjars/demo-0001-1.0.0-20211222.190331-1.jar";
	//"/kjars/demo-0001-1.0.1-20211222.200144-1.jar";
	//"/kjars/demo-0001-1.0.2-20211222.232048-1.jar";
	//"/kjars/demo-0001-1.0.3-20211224.162409-1.jar";
	//"/kjars/demo-0001-1.1.0-20211226.182721-1.jar";
	//"/kjars/demo-0001-1.1.1-20211226.191257-1.jar";

	private int verCounter = 0;

	@RequestMapping(value="/version", method = { RequestMethod.GET, RequestMethod.POST })
	public String version() {
		String version = "Version: 1.0.2::" + verCounter++;
		logger.info(version);
		return version;
	}

	@RequestMapping(value="/getRules", method = { RequestMethod.GET })
	public List<String> getRules(@RequestHeader MultiValueMap<String, String> headers) throws Exception {
		KieLoader    kieLoader     = new KieLoader();
		KieContainer kieContainer  = kieLoader.loadKjarFromClasspath(kJarVersions.get("1.1.0-SNAPSHOT"));
		List<String> rulesList     = kieLoader.getRuleDefinitions(kieContainer);
		return rulesList;
	}

//	@RequestMapping(value="/getFacts", method = { RequestMethod.GET, RequestMethod.POST })
//	public List<String> getFacts() {
//		KieLoader gDSTLoader;
//		try {
//			gDSTLoader = new KieLoader(kjarPath, kjarVers);
//		} catch (Exception ex) {
//			logger.error(ex.getLocalizedMessage(), ex);
//			throw ex;
//		}
//		List<String> rulesList = gDSTLoader.getFactDefinitions(gDSTLoader.kieContainer);
//		return rulesList;
//	}

	@RequestMapping(value="/getClasses", method = { RequestMethod.GET, RequestMethod.POST })
	public List<String> getClasses() throws Exception {
		return JarUtilities.getClassNamesFromJar(kjarPath);
	}
	
	@RequestMapping(value="/fireRules", method = { RequestMethod.POST })
	public String fireRules() throws Exception {
		KieLoader    kieLoader     = new KieLoader();
		List<BaseFact> objList2 = getObjectsBasedOnDep();

		KieContainer kieContainer1  = kieLoader.loadKjarFromClasspath(kJarVersions.get("1.1.0-SNAPSHOT"));
		KieSession   kieSession1    = kieContainer1.newKieSession();
		LoadAndFire(kieSession1, (List<T>) objList2);
		
		KieContainer kieContainer2  = kieLoader.loadKjarFromClasspath(kJarVersions.get("1.1.1-SNAPSHOT"));
		KieSession   kieSession2    = kieContainer2.newKieSession();
		LoadAndFire(kieSession2, (List<T>) objList2);
		
		return null;
	}
	

	private void LoadAndFire(KieSession kieSession, List<T> objList) throws Exception {
		//KieSession kieSession = gDSTLoader.getKieSession();
		kieSession.addEventListener(new ProcessEventListener());
		kieSession.addEventListener(new KieAgendaEventListener());
		//kieSession.getAgenda().getAgendaGroup("demo-gdst").setFocus();

		List<FactHandle> factHandles = new ArrayList<>();
		for(Object obj : objList) {
			factHandles.add(kieSession.insert(obj));
		}
		
		int rulesFired = kieSession.fireAllRules();
		int factCount = (int) kieSession.getFactCount();
		logger.info("rulesFired:" + rulesFired + " on " + factCount + " facts.");

		for(FactHandle factHandle : factHandles) {
			Object factAfter = kieSession.getObject(factHandle);
			logger.info(JsonUtilities.SerializeToJson(factAfter));
		}
	}	

	private List<BaseFact> getObjectsBasedOnDep() throws Exception {
		JsonUtilities<BaseFact> jsonUtil = new JsonUtilities<>();
		List<BaseFact> rtnList = new ArrayList<>();
		rtnList.add((BaseFact) jsonUtil.CreateObjFromJson(testJson01(), new BaseFact().getClass()));
		rtnList.add((BaseFact) jsonUtil.CreateObjFromJson(testJson02(), new BaseFact().getClass()));
		rtnList.add((BaseFact) jsonUtil.CreateObjFromJson(testJson03(), new BaseFact().getClass()));
		rtnList.add((BaseFact) jsonUtil.CreateObjFromJson(testJson04(), new BaseFact().getClass()));
		return rtnList;
	}

//	private List<Object> getObjectsBasedOnJar() throws Exception {
//		JsonUtilities<Object> jsonUtil = new JsonUtilities<>();
//		List<Object> rtnList = new ArrayList<>();
//		String baseFactName = "com.myspace.demo_0001.BaseFact";
//		Object tmpObj       = JarUtilities.getObjectFromJar(kjarPath, baseFactName);
//		rtnList.add(jsonUtil.CreateObjFromJson(testJson01(), tmpObj.getClass()));
//		rtnList.add(jsonUtil.CreateObjFromJson(testJson02(), tmpObj.getClass()));
//		return rtnList;
//	}
//
//	private String testJson1() {
//		return "{\"input_field\":\"John\", \"reponse_cd\":\"test response\"}";
//	} 
//	private String testJson2() {
//		return "{\"input_field\":null, \"reponse_cd\":\"null response\"}";
//	} 
//	private String testJson3() {
//		return "{\"input_field\":\"Mary\", \"reponse_cd\":\"\"}";
//	} 
//
	private String testJson01() {
		return "{\"input_field\":\"George\", \"reponse_cd\":\"\"}";
	} 
	private String testJson02() {
		return "{\"input_field\":\"Harry\", \"reponse_cd\":\"\"}";
	} 
	private String testJson03() {
		return "{\"input_field\":\"Mary\", \"reponse_cd\":\"\"}";
	} 
	private String testJson04() {
		return "{\"input_field\":\"Nancy\", \"reponse_cd\":\"\"}";
	} 

}
