
package com.rest.blog.exception;

public class AccessNotPermitedException extends RuntimeException {


private static final long serialVersionUID = 1L;

public AccessNotPermitedException(String message) {
super(message);

}


}
