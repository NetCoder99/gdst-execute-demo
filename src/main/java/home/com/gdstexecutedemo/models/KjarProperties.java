package home.com.gdstexecutedemo.models;

import java.time.LocalDateTime;

public class KjarProperties {
	private String path;
	private String version;
	private LocalDateTime update_dtm;
	
	public KjarProperties() {}

	public KjarProperties(String path, String version) {
		this.path = path;
		this.version = version;
	}

	public KjarProperties(String path, String version, LocalDateTime update_dtm) {
		this.path = path;
		this.version = version;
		this.update_dtm = update_dtm;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getUpdate_dtm() {
		return update_dtm;
	}

	public void setUpdate_dtm(LocalDateTime update_dtm) {
		this.update_dtm = update_dtm;
	}
	
}
