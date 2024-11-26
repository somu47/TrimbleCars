package com.example.TrimbleCars.Exception;



public class CustomerNotFoundException extends RuntimeException {
	 @java.io.Serial
	    static final long serialVersionUID = -7034897190745766939L;
    public CustomerNotFoundException(String message) {
        super(message);
    }
}

