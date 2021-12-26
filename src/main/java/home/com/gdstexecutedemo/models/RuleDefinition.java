package home.com.gdstexecutedemo.models;

public class RuleDefinition {
	private String kieBase;
	private String kiePackage;
	private String ruleName;
	private String agendeGroupName;
	private String ruleFlowGroupName;
	private String activationGroupName;

	public RuleDefinition() {}
	
	public RuleDefinition(String kieBase, String kiePackage, String ruleName, String agendeGroupName,
			String ruleFlowGroupName, String activationGroupName) {
		super();
		this.kieBase = kieBase;
		this.kiePackage = kiePackage;
		this.ruleName = ruleName;
		this.agendeGroupName = agendeGroupName;
		this.ruleFlowGroupName = ruleFlowGroupName;
		this.activationGroupName = activationGroupName;
	}

	public String getKieBase() {
		return kieBase;
	}

	public void setKieBase(String kieBase) {
		this.kieBase = kieBase;
	}

	public String getKiePackage() {
		return kiePackage;
	}

	public void setKiePackage(String kiePackage) {
		this.kiePackage = kiePackage;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getAgendeGroupName() {
		return agendeGroupName;
	}

	public void setAgendeGroupName(String agendeGroupName) {
		this.agendeGroupName = agendeGroupName;
	}

	public String getRuleFlowGroupName() {
		return ruleFlowGroupName;
	}

	public void setRuleFlowGroupName(String ruleFlowGroupName) {
		this.ruleFlowGroupName = ruleFlowGroupName;
	}

	public String getActivationGroupName() {
		return activationGroupName;
	}

	public void setActivationGroupName(String activationGroupName) {
		this.activationGroupName = activationGroupName;
	}
	
}
