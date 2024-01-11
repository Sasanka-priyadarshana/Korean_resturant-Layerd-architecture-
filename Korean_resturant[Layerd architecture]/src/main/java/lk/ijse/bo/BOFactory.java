package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {

    }
    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes {
        ATTENDANCE, CUSTOMER, EMPLOYEE, MENUITEM, ORDERDETILS, ORDERS, PAYMENT, ROOM, SALARY, STOCKITEM, SUPPLIER, USER,PLACEORDER,PLACEPAYMENT

    }
    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {

            case ATTENDANCE:
                return new AttendanceBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case MENUITEM:
                return new MenuItemBOImpl();
            case ORDERDETILS:
                return new OrderDetailBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case STOCKITEM:
                return new StockItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case USER:
                return new UserBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case PLACEPAYMENT:
                return new PlacePaymentBOImpl();
            default:
                return null;
        }
    }

}
