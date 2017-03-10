package net.evaluation.model.exception;

public class HashEncodingFailedException extends RuntimeException {

	private static final long serialVersionUID = -7449603966848890998L;
	
	public HashEncodingFailedException( Throwable throwable ) {
		super( throwable );
	}
	
}
