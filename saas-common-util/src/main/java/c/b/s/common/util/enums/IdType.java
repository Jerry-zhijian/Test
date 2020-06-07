package c.b.s.common.util.enums;

public enum IdType {
	PID("01", "身份证"), USER_ID("02", "用户ID");

	IdType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	private String type;
	private String description;

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}
}
