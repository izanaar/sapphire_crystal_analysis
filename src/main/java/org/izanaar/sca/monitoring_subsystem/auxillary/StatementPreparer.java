package org.izanaar.sca.monitoring_subsystem.auxillary;

import com.sun.istack.internal.NotNull;

import java.sql.*;

class StatementPreparer {
    private final String ADD_CUSTOMER = "insert into dist_sys_cp.customer(customer.description, customer.adress)\n" +
            "values (?,?);",
            GET_CUSTOMERS = "SELECT * FROM dist_sys_cp.customer;",
            REMOVE_CUSTOMER = "delete from dist_sys_cp.customer where customer.description=?;",
            GET_CUSTOMER_ID = "select id_customer from dist_sys_cp.customer \n" +
            "where description = ?;",
            GET_CUSTOMER_NAME = "select description from dist_sys_cp.customer" +
            " where customer.id_customer = ?;",
            ADD_PRODUCT = "insert into \n" +
            "dist_sys_cp.product(product.shape,product.kind,product.id_customer,product.date)\n" +
            " values (?,?,?,?);",
            GET_PRODUCTS_FULL = "SELECT * FROM dist_sys_cp.product;",
            GET_PRODUCTS = "SELECT kind FROM dist_sys_cp.product\n" +
            "where id_customer = ?;",
            DELETE_PRODUCT = "delete from dist_sys_cp.product where product.kind = ?;",
            SAVE_MEASURE = "INSERT INTO dist_sys_cp.measure (id_product,time) VALUES \n" +
                    "((select product.id_product from dist_sys_cp.product where kind = ?),?);",
            SAVE_MENISCUS_MEASURE = "INSERT INTO dist_sys_cp.menisk(id_measure,height,x_menisk,y_top_menisk,y_bot_menisk)\n" +
                    "VALUES ((SELECT max(measure.id_measure) FROM dist_sys_cp.measure\n" +
                    "WHERE measure.id_product = (SELECT product.id_product FROM dist_sys_cp.product\n" +
                    "WHERE product.kind = ?)),?,?,?,?);",
            SAVE_DEVIATION_MEASURE = "INSERT INTO dist_sys_cp.deviation" +
                    "(id_measure,deviation,y_cryst,x_cryst_left,x_cryst_right,y_shaper,x_shaper_left,x_shaper_right)\n" +
                    "VALUES ((SELECT max(measure.id_measure) FROM dist_sys_cp.measure\n" +
                    "WHERE measure.id_product = (SELECT product.id_product FROM dist_sys_cp.product\n" +
                    "WHERE product.kind = ?)),?,?,?,?,?,?,?);",
            SELECT_DEVIATION_MEASURE_SIMPLE = "select distinct deviation.id_measure, time, deviation \n" +
                    "from dist_sys_cp.measure, deviation\n" +
                    "where measure.id_product = (select id_product from dist_sys_cp.product \n" +
                    "\t\t\t\t\t\t\twhere product.kind = ?) and deviation.id_measure = measure.id_measure\n" +
                    "group by time asc;",
            SELECT_DEVIATION_MEASURE_TOTAL = "SELECT DISTINCT deviation.id_measure, time, deviation, y_cryst, x_cryst_left," +
                    " x_cryst_right, y_shaper, x_shaper_left, x_shaper_right \n" +
                    "FROM dist_sys_cp.measure, deviation\n" +
                    "WHERE measure.id_product = (SELECT id_product FROM dist_sys_cp.product \n" +
                    "\t\t\t\t\t\t\tWHERE product.kind = ?) and deviation.id_measure = measure.id_measure\n" +
                    "GROUP BY time asc;",
            SELECT_MENISCUS_MEASURE_SIMPLE = "SELECT DISTINCT menisk.id_measure, time, height\n" +
                    "FROM dist_sys_cp.measure, menisk\n" +
                    "WHERE measure.id_product = (SELECT id_product FROM dist_sys_cp.product \n" +
                    "\t\t\t\t\t\t\tWHERE product.kind = ?)  and menisk.id_measure = measure.id_measure\n" +
                    "GROUP BY time asc;",
            SELECT_MENISCUS_MEASURE_TOTAL = "SELECT DISTINCT menisk.id_measure, time, height, x_menisk, y_top_menisk," +
                    "y_bot_menisk \n" +
                    "FROM dist_sys_cp.measure, menisk\n" +
                    "WHERE measure.id_product = (SELECT id_product FROM dist_sys_cp.product \n" +
                    "\t\t\t\t\t\t\tWHERE product.kind = ?) and menisk.id_measure = measure.id_measure\n" +
                    "GROUP BY time asc;",
            SELECT_ALL_MEASURES_SIMPLE = "SELECT DISTINCT measure.id_measure, time, height, deviation \n" +
                    "FROM dist_sys_cp.measure, menisk, deviation\n" +
                    "WHERE measure.id_product = (SELECT id_product FROM dist_sys_cp.product \n" +
                    "\t\t\t\t\t\t\tWHERE product.kind = ?)  and menisk.id_measure = measure.id_measure" +
                    " and deviation.id_measure = measure.id_measure\n" +
                    "GROUP BY time asc;",
            DELETE_MEASURES = "";


    public void addCustomer(@NotNull String name, String adress, Connection connection) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, name);
            statement.setString(2, adress);
            statement.execute();
    }

    public ResultSet getCustomers(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMERS);
        return statement.executeQuery();
    }

    public void deleteCustomer(Connection connection, String name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(REMOVE_CUSTOMER);
        statement.setString(1,name);
        statement.execute();
    }

    public int getCustomerID (Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_ID);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next())
            return result.getInt(1);
        return 0;
    }

    public String getCustomerName(Connection connection, int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_NAME);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            return resultSet.getString(1);
        return "unknown.";
    }

    public void addProduct(Connection connection,@NotNull String kind,
                           String shape, String customerName) throws SQLException {
        int customerID = getCustomerID(connection,customerName);
        PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT);
        statement.setString(1,shape);
        statement.setString(2,kind);
        statement.setInt(3,customerID);
        statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        statement.execute();
    }

    public ResultSet getProductsFull(Connection connection) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS_FULL);
        return statement.executeQuery();
    }

    public ResultSet getProducts(Connection connection, String customer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS);
        statement.setInt(1,getCustomerID(connection,customer));
        return statement.executeQuery();
    }

    public void deleteProduct(Connection connection, String shape) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT);
        statement.setString(1,shape);
        statement.execute();
    }

    public void saveMeasure(Connection connection, String product, Measure measure) throws SQLException {
        PreparedStatement measureStatement = connection.prepareStatement(SAVE_MEASURE);
        measureStatement.setString(1,product);
        measureStatement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
        measureStatement.execute();

        PreparedStatement meniscusStatement = connection.prepareStatement(SAVE_MENISCUS_MEASURE);
        meniscusStatement.setString(1,product);
        meniscusStatement.setInt(2,measure.getMenHeight());
        meniscusStatement.setInt(3,measure.getxMenisk());
        meniscusStatement.setInt(4,measure.getyTopMenisk());
        meniscusStatement.setInt(5,measure.getyBotMenisk());
        meniscusStatement.execute();


        PreparedStatement deviationStatement = connection.prepareStatement(SAVE_DEVIATION_MEASURE);
        deviationStatement.setString(1,product);
        deviationStatement.setInt(2,measure.getDeviation());
        deviationStatement.setInt(3,measure.getyCryst());
        deviationStatement.setInt(4,measure.getxCrystLeft());
        deviationStatement.setInt(5,measure.getxCrystRight());
        deviationStatement.setInt(6,measure.getyShaper());
        deviationStatement.setInt(7,measure.getxShaperLeft());
        deviationStatement.setInt(8,measure.getxShaperRignt());
        deviationStatement.execute();
    }

    public ResultSet getMeniscusSimple(Connection connection, String product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_MENISCUS_MEASURE_SIMPLE);
        statement.setString(1, product);
        return statement.executeQuery();
    }

    public ResultSet getMeniscusTotal(Connection connection, String product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_MENISCUS_MEASURE_TOTAL);
        statement.setString(1, product);
        return statement.executeQuery();
    }

    public ResultSet getDeviationSimple(Connection connection, String product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_DEVIATION_MEASURE_SIMPLE);
        statement.setString(1, product);
        return statement.executeQuery();
    }

    public ResultSet getDeviationTotal(Connection connection, String product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_DEVIATION_MEASURE_TOTAL);
        statement.setString(1, product);
        return statement.executeQuery();
    }

    public ResultSet getAllSimple(Connection connection, String product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MEASURES_SIMPLE);
        statement.setString(1, product);
        return statement.executeQuery();
    }

    public boolean deleteAllMeasures(Connection connection, String product) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(DELETE_MEASURES);
        statement.setString(1,product);
        return statement.execute();
    }
}
