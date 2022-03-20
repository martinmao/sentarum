package io.scleropages.sentarum.bc.alipay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class AlipayMessage {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

	private final Map<String, String> params;

	public AlipayMessage(final Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> rawData() {
		return Collections.unmodifiableMap(params);
	}

	public String get(String key) {
		return params.get(key);
	}

	public String getAppId() {
		return params.get("app_id");
	}

	public String getCharset() {
		return params.get("charset");
	}

	public String getKey() {
		return params.get("key");
	}

	public String getNotifyId() {
		return params.get("notify_id");
	}

	public Date getNotifyTime() {
		String dateText = params.get("notify_time");
		try {
			return StringUtils.hasText(dateText) ? DateUtils.parseDate(dateText, DEFAULT_DATE_FORMAT) : null;
		} catch (ParseException e) {
			throw new IllegalArgumentException("invalid date format: " + dateText, e);
		}
	}

	public String getNotifyType() {
		return params.get("notify_type");
	}

	public String getSign() {
		return params.get("sign");
	}

	public String getSignType() {
		return params.get("sign_type");
	}

	public String getVersion() {
		return params.get("version");
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean details) {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
		sb.append("notifyId", getNotifyId());
		sb.append("notifyType", getNotifyType());
		sb.append("appId", getAppId());
		sb.append("notifyTime", getNotifyTime());
		sb.append("version", getVersion());
		sb.append("charset", getCharset());
		sb.append("key", getKey());
		if (details)
			sb.append("sign", getSignType() + ":" + getSign());
		return sb.toString();
	}
}
