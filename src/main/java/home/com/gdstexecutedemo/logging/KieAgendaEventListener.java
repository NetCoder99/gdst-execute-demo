package home.com.gdstexecutedemo.logging;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieAgendaEventListener implements AgendaEventListener  {
	private static final Logger logger = LoggerFactory.getLogger(ProcessEventListener.class);

	@Override
	public void matchCreated(MatchCreatedEvent event) {
		logger.info("MatchCreatedEvent");
	}

	@Override
	public void matchCancelled(MatchCancelledEvent event) {
		logger.info("matchCancelled");
	}

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		logger.info("beforeMatchFired");
		logger.info(event.getMatch().getRule().getName());
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		logger.info("afterMatchFired");
		logger.info(event.getMatch().getRule().getName());
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		logger.info("agendaGroupPopped");
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		logger.info("agendaGroupPushed");
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		logger.info("beforeRuleFlowGroupActivated");
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		logger.info("afterRuleFlowGroupActivated");
	}

	@Override
	public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
		logger.info("beforeRuleFlowGroupDeactivated");
	}

	@Override
	public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
		logger.info("afterRuleFlowGroupDeactivated");
	}

}
