import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    String SQL;
    Connection connection;
    Product product;

    public Connection connection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mail_order", "root", "******");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void allProducts() {
        try {
            connection();
            SQL = "select pname from parts";
            statement = connection().createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("pname"));
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String customersInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            connection();
            SQL = "SELECT cname, street, phone FROM customers order by cname asc ";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("cname")).append(" || ");
                stringBuilder.append(resultSet.getString("street")).append(" || ");
                stringBuilder.append(resultSet.getString("phone")).append(" || ").append("\n");
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return stringBuilder.toString();
    }

    public String customersInfoPlus() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            connection();
            SQL = "SELECT cname, street, phone, customers.zip, city FROM customers join zipcodes on customers.ZIP = zipcodes.zip order by cname asc";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("cname")).append(" || ");
                stringBuilder.append(resultSet.getString("street")).append(" || ");
                stringBuilder.append(resultSet.getString("phone")).append(" || ");
                stringBuilder.append(resultSet.getString("city")).append(" || ");
                stringBuilder.append(resultSet.getString("zip")).append(" || ").append("\n");
                ;
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return stringBuilder.toString();
    }

    public void specificProduct(int pno) {
        try {
            connection();
            SQL = "SELECT pname FROM parts where pno = ?";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, pno);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("pname"));
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<Product> productsPrice(double price) {
        List<Product> productList = new ArrayList<>();
        try {
            connection();
            String SQL = "SELECT pname, price FROM parts where price >= ?";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setDouble(1, price);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product = new Product(resultSet.getString("pname"), resultSet.getDouble("price"));
                productList.add(product);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return productList;
    }
}

