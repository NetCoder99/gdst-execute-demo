package home.com.gdstexecutedemo.logging;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEventListener extends DefaultProcessEventListener {
	private static final Logger logger = LoggerFactory.getLogger(ProcessEventListener.class);
	
    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        logger.info("After node left {}", event.getNodeInstance().getNodeName());
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.info("After node triggered {}", event.getNodeInstance().getNodeName());
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        logger.info("Before node left {}", event.getNodeInstance().getNodeName());
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        logger.info("Before node triggered {}", event.getNodeInstance().getNodeName());
    }
}
