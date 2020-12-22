package br.com.johnatanbrayan.web.rest.errors;

public class GeralException {

    public static class GeralExceptionException extends RuntimeException {
        
        public GeralExceptionException(String message) {
            super(message);
        }
    }
}
