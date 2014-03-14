package bradesco.tf;

public class UnsuccessfulException extends Exception {
	private static final long serialVersionUID = -2886722290057247569L;

	private Object data; 
	
	public UnsuccessfulException(){
		
	}
	
	public UnsuccessfulException(final String message){
		super(message);
	}
	
	public UnsuccessfulException(final Throwable cause){
		super(cause);
	}
	
	public UnsuccessfulException(final String message, final Throwable cause){
		super(message, cause);
	}

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

}
