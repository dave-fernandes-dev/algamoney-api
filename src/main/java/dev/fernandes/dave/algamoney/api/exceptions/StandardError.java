package dev.fernandes.dave.algamoney.api.exceptions;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private OffsetDateTime offSetDateTime = OffsetDateTime.now();
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardError() {
		super();
	}

	public StandardError(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public OffsetDateTime getOffSetDateTime() {
		return offSetDateTime;
	}

	public void setOffSetDateTime(OffsetDateTime offSetDateTime) {
		this.offSetDateTime = offSetDateTime;
	}

}
