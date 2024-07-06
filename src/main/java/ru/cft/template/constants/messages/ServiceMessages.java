package ru.cft.template.constants.messages;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class ServiceMessages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages/servicemessage");

    public static final String USER_ALREADY_EXISTS_MESSAGE = bundle.getString("user.already.exists.message");
    public static final String USER_NOT_FOUND_MESSAGE = bundle.getString("user.not.found.message");
    public static final String USER_NOT_FOUND_BY_EMAIL_MESSAGE = bundle.getString("user.not.found.by.email.message");
    public static final String USER_NOT_FOUND_BY_PHONE_MESSAGE = bundle.getString("user.not.found.by.phone.message");
    public static final String USER_SUCCESSFULLY_CREATED = bundle.getString("user.successfully.created");
    public static final String USER_SUCCESSFULLY_UPDATED = bundle.getString("user.successfully.updated");
    public static final String USER_SUCCESSFULLY_RETRIEVED = bundle.getString("user.successfully.retrieved");

    public static final String BAD_CREDENTIALS_MESSAGE = bundle.getString("bad.credentials.message");

    public static final String SESSION_SUCCESSFULLY_CREATED = bundle.getString("session.successfully.created");
    public static final String SESSION_SUCCESSFULLY_LOGOUT = bundle.getString("session.successfully.logout");
    public static final String SESSION_SUCCESSFULLY_RETRIEVED = bundle.getString("session.successfully.retrieved");
    public static final String SESSION_SUCCESSFULLY_REFRESHED = bundle.getString("session.successfully.refreshed");

    public static final String INVALID_REFRESH_TOKEN_MESSAGE = bundle.getString("invalid.refresh.token.message");

    public static final String WALLET_SUCCESSFULLY_RETRIEVED = bundle.getString("wallet.successfully.retrieved");
    public static final String WALLET_LUCKY_MESSAGE = bundle.getString("wallet.lucky.message");
    public static final String WALLET_UNLUCKY_MESSAGE = bundle.getString("wallet.unlucky.message");

    public static final String BILL_SUCCESSFULLY_CREATED = bundle.getString("bill.successfully.created");
    public static final String BILL_SUCCESSFULLY_CANCELED = bundle.getString("bill.successfully.canceled");
    public static final String NOT_BILL_OWNER_MESSAGE = bundle.getString("not.bill.owner.message");
    public static final String BILL_SUCCESSFULLY_PAYED = bundle.getString("bill.successfully.payed");
    public static final String NOT_BILL_RECIPIENT_MESSAGE = bundle.getString("not.bill.recipient.message");
    public static final String BILL_ALREADY_PAID_MESSAGE = bundle.getString("bill.already.paid.message");
    public static final String CANCELLED_BILL_MESSAGE = bundle.getString("cancelled.bill.message");
    public static final String BILL_SUCCESSFULLY_RETRIEVED = bundle.getString("bill.successfully.retrieved");
    public static final String BILL_NOT_FOUND_MESSAGE = bundle.getString("bill.not.found.message");
    public static final String BILL_LIST_SUCCESSFULLY_RETRIEVED = bundle.getString("bill.list.successfully.retrieved");
    public static final String NO_UNPAID_BILLS_MESSAGE = bundle.getString("no.unpaid.bills.message");
    public static final String DEBT_SUCCESSFULLY_RETRIEVED = bundle.getString("debt.successfully.retrieved");
    public static final String SAME_USER_BILL_MESSAGE = bundle.getString("same.user.bill.message");
    public static final String NOT_USER_BILL_MESSAGE = bundle.getString("not.user.bill.message");
    public static final String BILL_FILTER_CONFLICT_MESSAGE = bundle.getString("bill.filter.conflict.message");
    public static final String BILL_NOT_ALLOWED_TO_CANCEL_MESSAGE = bundle.getString("bill.not.allowed.to.cancel.message");

    public static final String NOT_ENOUGH_MONEY_MESSAGE = bundle.getString("not.enough.money.message");
    public static final String WALLET_NOT_FOUND_MESSAGE = bundle.getString("wallet.not.found.message");

    public static final String SAME_USER_TRANSFER_MESSAGE = bundle.getString("same.user.transfer.message");
    public static final String TRANSFER_SUCCESSFULLY_SENT = bundle.getString("transfer.successfully.sent");
    public static final String TRANSFER_SUCCESSFULLY_RETRIEVED = bundle.getString("transfer.successfully.retrieved");
    public static final String TRANSFER_NOT_FOUND_MESSAGE = bundle.getString("transfer.not.found.message");
    public static final String NOT_USER_TRANSFER_MESSAGE = bundle.getString("not.user.transfer.message");
    public static final String TRANSFER_LIST_SUCCESSFULLY_RETRIEVED = bundle.getString("transfer.list.successfully.retrieved");



    public static final String ACCESS_DENIED_MESSAGE = bundle.getString("access.denied.message");
    public static final String INTERNAL_MESSAGE = bundle.getString("internal.message");

}
