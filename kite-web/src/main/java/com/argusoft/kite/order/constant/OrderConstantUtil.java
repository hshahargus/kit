package com.argusoft.kite.order.constant;

/**
 *
 * @author
 */
public class OrderConstantUtil {

    public static class OrderParameter {

        public static final String TRADING_SYMBOL = "tradingsymbol";
        public static final String EXCHANGE = "exchange";
        public static final String TRANSACTION_TYPE = "transaction_type";
        public static final String ORDER_TYPE = "order_type";
        public static final String QUANTITY = "quantity";
        public static final String PRODUCT = "product";
        public static final String PRICE = "price";
        public static final String TRIGGER_PRICE = "trigger_price";
        public static final String DISCLOSED_QUANTITY = "disclosed_quantity";
        public static final String VALIDITY = "validity";
        public static final String SQUAREOFF = "squareoff";
        public static final String STOPLOSS = "stoploss";
        public static final String TRAILING_STOPLOSS = "trailing_stoploss";
    }

    public static class OrderStatus {

        public static final String OPEN = "OPEN";
        public static final String COMPLETE = "COMPLETE";
        public static final String CANCELLED = "CANCELLED";
        public static final String REJECTED = "REJECTED";
        public static final String PUT_ORDER_REQUEST_RECEIVED = "PUT ORDER REQUEST RECEIVED";//Order request has been received by the backend
        public static final String VALIDATION_PENDING = "VALIDATION PENDING";//Order pending validation by the RMS (Risk Management System)
        public static final String OPEN_PENDING = "OPEN PENDING";//Order is pending registration at the exchange
        public static final String MODIFY_VALIDATION_PENDING = "MODIFY VALIDATION PENDING";//Order's modification values are pending validation by the RMS
        public static final String MODIFY_PENDING = "MODIFY PENDING";//Order's modification values are pending registration at the exchange
        public static final String CANCEL_PENDING = "CANCEL PENDING";//Order's cancellation request is pending registration at the exchange
        public static final String AMO_REQ_RECEIVED = "AMO REQ RECEIVED";//Same as PUT ORDER REQUEST RECEIVED, but for AMOs
    }

    public enum variety {
        REGULAR("regular") //Regular order
        , AMO("amo")//After Market Order
        , BO("bo")//Bracket Order
        , CO("co")//Cover Order
        ;
        private final String value;

        private variety(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderType {
        MARKET("MARKET"), LIMIT("LIMIT"), SL("SL"), SLM("SL-M");

        private final String value;

        private OrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Product {
        CNC("CNC"), NRML("NRML"), MIS("MIS");

        private final String value;

        private Product(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum validity {
        DAY("DAY"), IOC("IOC");

        private final String value;

        private validity(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TransactionType {
        BUY("BUY"), SELL("SELL");

        private final String value;

        private TransactionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
