package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static  DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory =new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        ATTENDANCE, CUSTOMER,EMPLOYEE,MENUITEM,ORDERDETILS,ORDERS,PAYMENT,ROOM,SALARY,STOCKITEM,SUPPLIER,USER
    }
    public SuperDAO  getDAO(DAOTypes types){
        switch (types){
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case MENUITEM:
                return new MenuItemDAOImpl();
            case ORDERDETILS:
                return new OrderDetailDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case STOCKITEM:
                return new StockItemDAOImpl();
            case SUPPLIER:
                return  new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
