package com.example.TrimbleCars.Exception;


public class CarNotFoundException extends RuntimeException {
	 @java.io.Serial
	    static final long serialVersionUID = -7034897190745766939L;
    public CarNotFoundException(String message) {
        super(message);
    }
}
