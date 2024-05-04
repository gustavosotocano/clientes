package com.gml.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFoundException extends RuntimeException {
public String code;
public String  message;
 public ResourceNotFoundException(String code,String message) {
     super();
     this.message=message;
     this.code = code;
 }




}