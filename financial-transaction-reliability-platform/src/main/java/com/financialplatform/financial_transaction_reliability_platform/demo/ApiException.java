package com.financialplatform.financial_transaction_reliability_platform.demo;

public class ApiException extends RuntimeException {
    private final int status; private final String code;
    public ApiException(int status, String code, String message) { super(message); this.status = status; this.code = code; }
    public int status() { return status; } public String code() { return code; }
}
