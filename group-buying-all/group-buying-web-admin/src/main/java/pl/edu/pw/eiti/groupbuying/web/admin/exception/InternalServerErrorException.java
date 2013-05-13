package pl.edu.pw.eiti.groupbuying.web.admin.exception;

@SuppressWarnings("serial")
public class InternalServerErrorException extends RuntimeException {

	protected String customMessage;

	public InternalServerErrorException(String customMessage) {
		super();
		this.customMessage = customMessage;
	}

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
}