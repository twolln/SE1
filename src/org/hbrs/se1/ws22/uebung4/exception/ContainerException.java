package org.hbrs.se1.ws22.uebung4.exception;

public class ContainerException extends Exception {
	
	private String modus;
	private Integer id;
	private ExceptionType type;
	
	public ContainerException(ExceptionType type  ) {
		this.type = type;
	}
 
	@Override
	public void printStackTrace() {
		
		if ( this.type == ExceptionType.DuplicateEmployee) {
			
			System.out.println("Das Member-Objekt mit der ID " + this.id + " ist bereits vorhanden!");
			
		} else if ( this.type == ExceptionType.InfoCastException ) {
			
		    System.out.println("Das Member-Objekt mit der ID " + this.id + " konnte nicht gecastet werden!");
			
		} 
	} 

	public void addID(Integer id) {
		this.id = id;
	}
	
	public enum ExceptionType {
		InfoCastException, DuplicateEmployee
	}


}
