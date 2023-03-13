package edu.poly.springshop.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CategoryExcepion.class)
    public final  ResponseEntity<Object> handleCategoryException (CategoryExcepion ex, WebRequest request){
            CategoryExceptionResponse exceptionResponse = new CategoryExceptionResponse(ex.getMessage());

            return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(FileNotFoundException.class)
    public final  ResponseEntity<Object> handleFileNotFoundException (FileNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(FileStorageException.class)
    public final  ResponseEntity<Object> handleFileStorageException (FileStorageException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ManufacturerException.class)
    public final  ResponseEntity<Object> handleFileStorageException (ManufacturerException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ProductExcepion.class)
    public final  ResponseEntity<Object> handleFileStorageException (ProductExcepion ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
}
