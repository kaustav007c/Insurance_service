package com.cts.insurance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.insurance.dto.ErrorInfo;
import com.cts.insurance.exception.DifferentQueryParam;
import com.cts.insurance.exception.DuplicateEntry;
import com.cts.insurance.exception.MissingQueryParam;
import com.cts.insurance.exception.NoContentFound;
import com.cts.insurance.exception.NoInsuranceForCustomerId;


@RestControllerAdvice
public class GlobalErrorHandler {

private ResponseEntity<ErrorInfo> error(final Exception exception, String code, String description){
	return new ResponseEntity<ErrorInfo>(new ErrorInfo(exception, code, description), HttpStatus.BAD_REQUEST);
}


@ExceptionHandler(NoInsuranceForCustomerId.class)
public ResponseEntity<ErrorInfo> noInsuranceForCustomerIdError(NoInsuranceForCustomerId ex){
return error(ex,"INS_ERR_002","No insurance details found for given customer ID");
}

@ExceptionHandler(MissingQueryParam.class)
public ResponseEntity<ErrorInfo> missingQueryParamhandleCustomerError(MissingQueryParam ex){
return error(ex,"INS_ERR_003","Mandatory parameters missing");
}

@ExceptionHandler(DifferentQueryParam.class)
public ResponseEntity<ErrorInfo> differentQueryParamError(DifferentQueryParam ex){
return error(ex,"INS_ERR_004","Query Parameters mismatch");
}

@ExceptionHandler(DuplicateEntry.class)
public ResponseEntity<ErrorInfo> duplicateEntryError(DuplicateEntry ex){
return error(ex,"INS_ERR_005","Found Duplicate Entry");
}

@ExceptionHandler(NoContentFound.class)
public ResponseEntity<ErrorInfo> noContentFoundError(NoContentFound ex){
return error(ex,"INS_ERR_000","No Content Found");
}

}