package tech.hariprasath.batchdemo.advicer;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleException(ExpiredJwtException ex) {
        ProblemDetail errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Your token has expired");
        errorResponse.setProperty("success", false);
        errorResponse.setProperty("reason", ex.getMessage());
        errorResponse.setProperty("message", "Session expired, Login to continue");
        return errorResponse;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleException(ResponseStatusException ex) {
        ProblemDetail errorResponse = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), ex.getReason());
        errorResponse.setProperty("success", false);
        errorResponse.setProperty("message", ex.getReason());
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        ProblemDetail errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        errorResponse.setProperty("success", false);
        return errorResponse;
    }
}
