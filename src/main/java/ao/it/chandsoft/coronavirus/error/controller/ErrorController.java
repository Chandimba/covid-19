package ao.it.chandsoft.coronavirus.error.controller;

import ao.it.chandsoft.coronavirus.exception.CoronavirusException;
import ao.it.chandsoft.coronavirus.model.error.ErrorResponse;
import ao.it.chandsoft.coronavirus.exception.ResourceNotFoundException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Nelson Chandimba da Silva
 */

@RestController
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CoronavirusException.class)
    public ResponseEntity<ErrorResponse> handle(CoronavirusException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(PATH)
    @ResponseBody
    public Object error(WebRequest request, HttpServletResponse response) {
        switch(response.getStatus()) {
            case 404: return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource not found");
            default: return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error server. Please, try again later!");
        }
        
    }
    
    @Override
    public String getErrorPath() {
        return PATH;
    }
    
}
