package com.test.servicemonitor.web.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit;

/**
 * 
 * Form bean of {@link RemoteSystem} CRUD pages
 *
 */
public class RemoteSystemForm {

	@NotBlank
	private String system_id;
	@NotBlank
	private String checker_type;
	@NotBlank
	private String connection_string;
	private String hints;
	@NotNull
	private Integer check_period;
	@NotNull
	private PeriodUnit period_unit;
	private Boolean disabled;

	private List<String> supportedCheckerTypes;

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getChecker_type() {
		return checker_type;
	}

	public void setChecker_type(String checker_type) {
		this.checker_type = checker_type;
	}

	public String getConnection_string() {
		return connection_string;
	}

	public void setConnection_string(String connection_string) {
		this.connection_string = connection_string;
	}

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	public Integer getCheck_period() {
		return check_period;
	}

	public void setCheck_period(Integer check_period) {
		this.check_period = check_period;
	}

	public PeriodUnit getPeriod_unit() {
		return period_unit;
	}

	public void setPeriod_unit(PeriodUnit period_unit) {
		this.period_unit = period_unit;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public List<String> getSupportedCheckerTypes() {
		return supportedCheckerTypes;
	}

	public void setSupportedCheckerTypes(List<String> supportedCheckerTypes) {
		this.supportedCheckerTypes = supportedCheckerTypes;
	}

	public RemoteSystem toRemoteSystem() {
		RemoteSystem remoteSystem = new RemoteSystem();
		remoteSystem.setSystem_id(system_id);
		remoteSystem.setChecker_type(checker_type);
		remoteSystem.setConnection_string(connection_string);
		remoteSystem.setHints(hints);
		remoteSystem.setCheck_period(check_period);
		remoteSystem.setPeriod_unit(period_unit);
		remoteSystem.setDisabled(disabled);
		return remoteSystem;
	}

	public void fromRemoteSystem(RemoteSystem remoteSystem) {
		Assert.notNull(remoteSystem);
		this.system_id = remoteSystem.getSystem_id();
		this.checker_type = remoteSystem.getChecker_type();
		this.connection_string = remoteSystem.getConnection_string();
		this.hints = remoteSystem.getHints();
		this.check_period = remoteSystem.getCheck_period();
		this.period_unit = remoteSystem.getPeriod_unit();
		this.disabled = remoteSystem.getDisabled();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemoteSystemForm [system_id=");
		builder.append(system_id);
		builder.append(", checker_type=");
		builder.append(checker_type);
		builder.append(", connection_string=");
		builder.append(connection_string);
		builder.append(", hints=");
		builder.append(hints);
		builder.append(", check_period=");
		builder.append(check_period);
		builder.append(", period_unit=");
		builder.append(period_unit);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append(", supportedCheckerTypes=");
		builder.append(supportedCheckerTypes);
		builder.append("]");
		return builder.toString();
	}

}
