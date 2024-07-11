package ru.cft.template.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Endpoints {
    public static final String BASE_URL = "/api/v1";
    public static final String ID = "/{id}";

    public static final String USERS_URL = BASE_URL + "/users";

    public static final String SESSIONS_URL = BASE_URL + "/sessions";
    public static final String LOGOUT = "/logout";
    public static final String REFRESH = "/refresh";

    public static final String WALLETS_URL = BASE_URL + "/wallets";
    public static final String HESOYAM = "/hesoyam";

    public static final String BILLS_URL = BASE_URL + "/bills";
    public static final String BILLS_CANCEL = "/{id}/cancel";
    public static final String BILLS_PAY = "/{id}/pay";

    public static final String BILLS_OLDEST_UNPAID = "/oldest-unpaid";
    public static final String BILLS_TOTAL_DEBT = "/total-debt";

    public static final String TRANSFERS_URL = BASE_URL + "/transfers";

}
