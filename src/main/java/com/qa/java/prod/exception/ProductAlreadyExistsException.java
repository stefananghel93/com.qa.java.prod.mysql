package com.qa.java.prod.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product Already Exists with these details")

public class ProductAlreadyExistsException extends Exception {

}
