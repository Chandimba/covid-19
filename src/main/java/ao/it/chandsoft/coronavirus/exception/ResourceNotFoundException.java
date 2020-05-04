package ao.it.chandsoft.coronavirus.exception;

/**
 * @author Nelson Chandimba da Silva
 */

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("Resource not found");
    }
    
}
