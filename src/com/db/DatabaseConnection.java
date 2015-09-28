package com.db;

import com.dto.Car;
import com.dto.Client;
import com.dto.Order;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/coursedb";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static Client findClient(String firstName, String lastName) {

    	Client client = new Client();
    	String query = String.format("select * from users where firstName = '%s' and lastName = '%s'", firstName, lastName);

        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                client.setClientId(rs.getInt(1));
                client.setFirstName(rs.getString(2));
                client.setLastName(rs.getString(3));
                client.setDateOfBirth(new java.util.Date(rs.getDate(4).getTime()));
                client.setAddress(rs.getString(5));
                client.setPhone(rs.getString(6));
                client.setEmail(rs.getString(7));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
		return client;
	}

    public static ArrayList<Client> getListFromDb() {

        ArrayList<Client> list = new ArrayList<>();
        Client client;
        String query = "select * from users";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                client = new Client();
                client.setClientId(rs.getInt(1));
                client.setFirstName(rs.getString(2));
                client.setLastName(rs.getString(3));
                client.setDateOfBirth(new java.util.Date(rs.getDate(4).getTime()));
                client.setAddress(rs.getString(5));
                client.setPhone(rs.getString(6));
                client.setEmail(rs.getString(7));
                list.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return list;
    }

    public static ArrayList<Car> getCars(int clientId) {

        ArrayList<Car> list = new ArrayList<>();
        Car car;
        String query = String.format("select * from cars where clientID = '%s'", clientId);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                car = new Car();
                car.setCarId(rs.getInt(1));
                car.setClientId(rs.getInt(2));
                car.setMake(rs.getString(3));
                car.setModel(rs.getString(4));
                car.setYear(String.valueOf(rs.getInt(5)));
                car.setVIN(rs.getString(6));
                list.add(car);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return list;
    }

    public static ArrayList<Order> getOrders(int carId) {

        ArrayList<Order> list = new ArrayList<>();
        Order order;
        String query = String.format("select * from orders where carID = '%s'", carId);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt(1));
                order.setCarId(rs.getInt(2));
                order.setDate(new java.util.Date(rs.getDate(3).getTime()));
                order.setCost(Float.toString(rs.getFloat(4)));
                order.setStatus(rs.getString(5));
                order.setDescription(rs.getString(6));
                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return list;
    }

    public static void addClient(Client client) {

        int id = DatabaseConnection.maxId("clientID") + 1;
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        String address = client.getAddress();
        String phone = client.getPhone();
        String email = client.getEmail();
        Date date = new Date(client.getDateOfBirth().getTime());

        String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, email);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void updateClient(Client client) {

        int clientId = client.getClientId();
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        String address = client.getAddress();
        String phone = client.getPhone();
        String email = client.getEmail();
        Date date = new Date(client.getDateOfBirth().getTime());

        String query = "UPDATE users SET firstName = ?, lastName = ?, dateOfBirth = ?, address = ?," +
                " phone = ?, email = ? WHERE clientID = ?";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, email);
            preparedStatement.setInt(7, clientId);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void addCar(Car car) {

        int carId = DatabaseConnection.maxId("carID") + 1;
        int clientId = car.getClientId();
        String make = car.getMake();
        String model = car.getModel();
        String year = car.getYear();
        String VIN = car.getVIN();

        String query = "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, carId);
            preparedStatement.setInt(2, clientId);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setString(5, year);
            preparedStatement.setString(6, VIN);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void updateCar(Car car) {

        int carId = car.getCarId();
        String make = car.getMake();
        String model = car.getModel();
        String year = car.getYear();
        String VIN = car.getVIN();

        String query = "UPDATE cars SET make = ?, model = ?, year = ?," +
                " vin = ? WHERE carID = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, VIN);
            preparedStatement.setInt(5, carId);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void deleteCar(int carId) {

        String query = "DELETE FROM cars WHERE carID = ?";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, carId);
            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void addOrder(Order order) {

        int orderId = DatabaseConnection.maxId("orderID") + 1;
        int carId = order.getCarId();
        String status = order.getStatus();
        String description = order.getDescription();
        String cost = order.getCost();
        Date date = new Date(order.getDate().getTime());

        String query = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, carId);
            preparedStatement.setDate(3, date);
            preparedStatement.setFloat(4, Float.parseFloat(cost));
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, description);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void updateOrder(Order order) {

        int orderId = order.getOrderId();
        String status = order.getStatus();
        String description = order.getDescription();
        String cost = order.getCost();

        String query = "UPDATE orders SET orderStatus = ?, orderCost = ?, description = ? " +
                "WHERE orderId = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setFloat(2, Float.parseFloat(cost));
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, orderId);

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static void deleteOrder(int orderId) {

        String query = "DELETE FROM orders WHERE orderID = ?";

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }

    public static int maxId(String value) {

        String choice = "";
        String table = "";

        switch (value) {
            case "clientID": {
                choice = value;
                table = "users";
                break;
            }
            case "carID": {
                choice = value;
                table = "cars";
                break;
            }
            case "orderID": {
                choice = value;
                table = "orders";
                break;
            }
        }

        String query = String.format("SELECT MAX(%s) FROM %s", choice, table);
        int id = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { stmt.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return id;
    }
}
