package edu.poly.springshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductExcepion extends  RuntimeException{

    public ProductExcepion(String message) {
        super(message);
    }
}
