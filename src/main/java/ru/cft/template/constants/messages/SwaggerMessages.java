package ru.cft.template.constants.messages;


import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class SwaggerMessages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages/swaggermessage");

    public static final String SWAGGER_API_TITLE = bundle.getString("swagger.api.title");
    public static final String SWAGGER_API_DESCRIPTION = bundle.getString("swagger.api.description");
    public static final String API_VERSION_FOR_SWAGGER = bundle.getString("swagger.api.version");

    public static final String USERS_TAG = "${users.tag}";
    public static final String USERS_CREATE_SUMMARY = "${users.create.summary}";
    public static final String USERS_CREATE_DESCRIPTION = "${users.create.description}";
    public static final String USERS_UPDATE_SUMMARY = "${users.update.summary}";
    public static final String USERS_UPDATE_DESCRIPTION = "${users.update.description}";
    public static final String USERS_GET_SUMMARY = "${users.get.summary}";
    public static final String USERS_GET_DESCRIPTION = "${users.get.description}";

    public static final String SESSIONS_TAG = "${sessions.tag}";
    public static final String SESSIONS_CREATE_SUMMARY = "${sessions.create.summary}";
    public static final String SESSIONS_CREATE_DESCRIPTION = "${sessions.create.description}";
    public static final String SESSIONS_LOGOUT_SUMMARY = "${sessions.logout.summary}";
    public static final String SESSIONS_LOGOUT_DESCRIPTION = "${sessions.logout.description}";
    public static final String SESSIONS_GET_SUMMARY = "${sessions.get.summary}";
    public static final String SESSIONS_GET_DESCRIPTION = "${sessions.get.description}";
    public static final String SESSIONS_REFRESH_SUMMARY = "${sessions.refresh.summary}";
    public static final String SESSIONS_REFRESH_DESCRIPTION = "${sessions.refresh.description}";

    public static final String WALLETS_TAG = "${wallets.tag}";
    public static final String WALLETS_GET_SUMMARY = "${wallets.get.summary}";
    public static final String WALLETS_GET_DESCRIPTION = "${wallets.get.description}";
    public static final String WALLETS_HESOYAM_SUMMARY = "${wallets.hesoyam.summary}";
    public static final String WALLETS_HESOYAM_DESCRIPTION = "${wallets.hesoyam.description}";



    public static final String BILLS_TAG = "${bills.tag}";
    public static final String BILLS_TAG_DESCRIPTION = "${bills.tag.description}";
    public static final String BILLS_CREATE_SUMMARY = "${bills.create.summary}";
    public static final String BILLS_CREATE_DESCRIPTION = "${bills.create.description}";
    public static final String BILLS_CANCEL_SUMMARY = "${bills.cancel.summary}";
    public static final String BILLS_CANCEL_DESCRIPTION = "${bills.cancel.description}";
    public static final String BILLS_PAY_SUMMARY = "${bills.pay.summary}";
    public static final String BILLS_PAY_DESCRIPTION = "${bills.pay.description}";

    public static final String BILLS_GET_OWN_SUMMARY = "${bills.get.own.summary}";
    public static final String BILLS_GET_OWN_DESCRIPTION = "${bills.get.own.description}";
    public static final String BILLS_GET_ALL_OWN_SUMMARY = "${bills.get.all.own.summary}";
    public static final String BILLS_GET_ALL_OWN_DESCRIPTION = "${bills.get.all.own.description}";
    public static final String BILLS_GET_OLDEST_UNPAID_SUMMARY = "${bills.get.oldest.unpaid.summary}";
    public static final String BILLS_GET_OLDEST_UNPAID_DESCRIPTION = "${bills.get.oldest.unpaid.description}";
    public static final String BILLS_GET_TOTAL_DEBT_SUMMARY = "${bills.get.total.debt.summary}";
    public static final String BILLS_GET_TOTAL_DEBT_DESCRIPTION = "${bills.get.total.debt.description}";

    public static final String TRANSFERS_TAG = "${transfers.tag}";
    public static final String TRANSFERS_TAG_DESCRIPTION = "${transfers.tag.description}";
    public static final String TRANSFERS_SEND_SUMMARY = "${transfers.send.summary}";
    public static final String TRANSFERS_SEND_DESCRIPTION = "${transfers.send.description}";
    public static final String TRANSFERS_GET_OWN_SUMMARY = "${transfers.get.own.summary}";
    public static final String TRANSFERS_GET_OWN_DESCRIPTION = "${transfers.get.own.description}";
    public static final String TRANSFERS_GET_ALL_OWN_SUMMARY = "${transfers.get.all.own.summary}";
    public static final String TRANSFERS_GET_ALL_OWN_DESCRIPTION = "${transfers.get.all.own.description}";


}
