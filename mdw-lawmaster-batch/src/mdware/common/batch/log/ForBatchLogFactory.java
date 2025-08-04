package mdware.common.batch.log;


import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class ForBatchLogFactory implements LoggerFactory {

	public ForBatchLogFactory() {
	}

	public Logger makeNewLoggerInstance(String name) {
		return new CategoryForBatchLog(name);
	}
}
