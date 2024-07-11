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
import ru.cft.template.util.DefaultResponseBuilder;

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
    public DefaultResponse<?> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(UserNotFoundByPhoneException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleUserNotFoundByPhone(UserNotFoundByPhoneException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleUserNotFound(UserNotFoundException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
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

        return DefaultResponseBuilder.error(
                ValidationMessages.VALIDATION_FAILED,
                HttpStatus.BAD_REQUEST,
                errors
        );
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handlePropertyReference(PropertyReferenceException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleConstraintViolation(jakarta.validation.ConstraintViolationException e) {

        Map<String, List<String>> errors = e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));

        return DefaultResponseBuilder.error(
                ValidationMessages.VALIDATION_FAILED,
                HttpStatus.BAD_REQUEST,
                errors
        );
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DefaultResponse<?> handleBadCredentials(BadCredentialsException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {

        return DefaultResponseBuilder.error(
                ValidationMessages.NOT_READABLE,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DefaultResponse<?> handleForbiddenException() {
        return DefaultResponseBuilder.error(
                ServiceMessages.ACCESS_DENIED_MESSAGE,
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DefaultResponse<?> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return DefaultResponseBuilder.error(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return DefaultResponseBuilder.error(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    // BILL EXCEPTIONS

    @ExceptionHandler(BillAlreadyPaidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleBillAlreadyPaid(BillAlreadyPaidException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillFilterConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleBillFilterConflict(BillFilterConflictException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleBillNotFound(BillNotFoundException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CancelledBillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleCancelledBill(CancelledBillException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotBillOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DefaultResponse<?> handleNotBillOwner(NotBillOwnerException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotBillRecipientException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DefaultResponse<?> handleNotBillRecipient(NotBillRecipientException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotUserBillException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DefaultResponse<?> handleNotUserBill(NotUserBillException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NoUnpaidBillsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleNoUnpaidBills(NoUnpaidBillsException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SameUserBillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleSameUserBill(SameUserBillException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BillNotAllowedToCancelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handlePersonalBillCantBeCancelled(BillNotAllowedToCancelException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // TRANSFER EXCEPTIONS

    @ExceptionHandler(TransferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleTransferNotFound(TransferNotFoundException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotUserTransferException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DefaultResponse<?> handleNotUserTransfer(NotUserTransferException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler(SameUserTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleSameUserTransfer(SameUserTransferException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleNotEnoughMoney(NotEnoughMoneyException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse<?> handleWalletNotFound(WalletNotFoundException exception) {
        return DefaultResponseBuilder.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DefaultResponse<?> handleException(Exception e) {
        log.error("\nVery sad, because I didn't handle it...\nPlease, check:\n");
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));

        return DefaultResponseBuilder.error(
                ServiceMessages.INTERNAL_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
