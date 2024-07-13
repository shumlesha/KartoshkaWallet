package ru.cft.template.exception;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.cft.template.constants.messages.ServiceMessages;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.api.ErrorResponse;
import ru.cft.template.exception.bill.*;
import ru.cft.template.exception.token.InvalidRefreshTokenException;
import ru.cft.template.exception.transfer.NotUserTransferException;
import ru.cft.template.exception.transfer.SameUserTransferException;
import ru.cft.template.exception.transfer.TransferNotFoundException;
import ru.cft.template.exception.user.UserAlreadyExistsException;
import ru.cft.template.exception.user.UserNotFoundByPhoneException;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.exception.wallet.NotEnoughMoneyException;
import ru.cft.template.exception.wallet.WalletNotFoundException;
import ru.cft.template.util.ResponseBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // USER, AUTH - EXCEPTIONS

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(UserNotFoundByPhoneException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundByPhone(UserNotFoundByPhoneException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));


        Map<String, List<String>> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        if (errors.isEmpty()) {
            errors = Map.of(Objects.requireNonNull(e.getBindingResult().getTarget()).getClass().getSimpleName(),
                    e.getBindingResult()
                            .getGlobalErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList());
        }

        return ResponseBuilder.error(
                ValidationMessages.VALIDATION_FAILED,
                HttpStatus.BAD_REQUEST,
                errors
        );
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePropertyReference(PropertyReferenceException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(jakarta.validation.ConstraintViolationException e) {

        Map<String, List<String>> errors = e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));

        return ResponseBuilder.error(
                ValidationMessages.VALIDATION_FAILED,
                HttpStatus.BAD_REQUEST,
                errors
        );
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentials(BadCredentialsException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {

        return ResponseBuilder.error(
                ValidationMessages.NOT_READABLE,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException() {
        return ResponseBuilder.error(
                ServiceMessages.ACCESS_DENIED_MESSAGE,
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return ResponseBuilder.error(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseBuilder.error(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    // BILL EXCEPTIONS

    @ExceptionHandler(BillAlreadyPaidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBillAlreadyPaid(BillAlreadyPaidException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillFilterConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBillFilterConflict(BillFilterConflictException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBillNotFound(BillNotFoundException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CancelledBillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCancelledBill(CancelledBillException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotBillOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotBillOwner(NotBillOwnerException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotBillRecipientException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotBillRecipient(NotBillRecipientException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotUserBillException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotUserBill(NotUserBillException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NoUnpaidBillsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoUnpaidBills(NoUnpaidBillsException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SameUserBillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSameUserBill(SameUserBillException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillNotAllowedToCancelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePersonalBillCantBeCancelled(BillNotAllowedToCancelException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // TRANSFER EXCEPTIONS

    @ExceptionHandler(TransferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTransferNotFound(TransferNotFoundException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotUserTransferException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotUserTransfer(NotUserTransferException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler(SameUserTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSameUserTransfer(SameUserTransferException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotEnoughMoney(NotEnoughMoneyException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFound(WalletNotFoundException exception) {
        return ResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("\nVery sad, because I didn't handle it...\nPlease, check:\n");
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));

        return ResponseBuilder.error(
                ServiceMessages.INTERNAL_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
