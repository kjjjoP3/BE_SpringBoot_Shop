package edu.poly.springshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryExcepion  extends  RuntimeException{

    public CategoryExcepion(String message) {
        super(message);
    }
}
