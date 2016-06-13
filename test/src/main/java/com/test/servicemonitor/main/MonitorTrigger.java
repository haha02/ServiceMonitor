package com.test.servicemonitor.main;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.util.Assert;

import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystemService;
import com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit;

public class MonitorTrigger implements Trigger {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String systemId;

	private RemoteSystemService remoteSystemService;

	private String triggerId;

	private boolean traminated;

	public MonitorTrigger(String systemId, RemoteSystemService remoteSystemService) {
		super();
		Assert.notNull(systemId, "systemId must not be null");
		Assert.notNull(remoteSystemService, "remoteSystemService must not be null");
		this.systemId = systemId;
		this.remoteSystemService = remoteSystemService;
		this.triggerId = systemId + "@Trigger" + Integer.toHexString(hashCode());
	}

	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		if (traminated) {
			logger.debug("[{}]: terminated.", triggerId);
			return null;
		}

		Date lastSchTime = triggerContext.lastScheduledExecutionTime();
		if (lastSchTime == null) {
			Date now = new Date();
			logger.debug("[{}]: the first scheduled execution time is [{}]", triggerId, now);
			return now;
		}

		Date lastCompTime = triggerContext.lastCompletionTime();

		long diff = getNextScheduleTimeDiff(lastCompTime);
		Date nextSchTime = new Date(lastSchTime.getTime() + diff);

		// switch to the next one if the nextSchTime is before lastCompTime
		while (nextSchTime.before(lastCompTime)) {
			nextSchTime = new Date(nextSchTime.getTime() + diff);
		}

		logger.debug("[{}]: the next scheduled execution time is [{}]", triggerId, nextSchTime);
		return nextSchTime;
	}

	private long getNextScheduleTimeDiff(Date lastSchTime) {
		RemoteSystem rs = remoteSystemService.get(systemId);
		PeriodUnit pu;
		try {
			pu = PeriodUnit.valueOf(rs.getPeriod_unit());
		} catch (Exception e) {
			throw new IllegalStateException("Unsupported REMOTE_SYSTEM > PERIOD_UNIT value "
					+ "(PK=" + rs.getSystem_id() + "): " + rs.getPeriod_unit(), e);
		}

		int period = rs.getCheck_period();// should >= 0
		// may need additional check? in case someone's messing the DB up
		return period * pu.getUnitMiliSec();
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public boolean isTraminated() {
		return traminated;
	}

	public void setTraminated(boolean traminated) {
		this.traminated = traminated;
	}

}
