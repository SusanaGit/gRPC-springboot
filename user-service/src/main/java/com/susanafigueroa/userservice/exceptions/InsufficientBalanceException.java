package com.susanafigueroa.userservice.exceptions;

public class InsufficientBalanceException extends RuntimeException {

    private static final String MESSAGE = "User [id=%] does not have enough fund to complete the transaction.";

    public InsufficientBalanceException(Integer userId) {
        super(MESSAGE.formatted(userId));
    }

}
