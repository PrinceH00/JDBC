import java.sql.*;

public class main {

    public static void main(String[] args) {
        Database database = new Database();

        System.out.println();
        database.allProducts();

        System.out.println();
        database.specificProduct(10506);

        System.out.println();
        System.out.println(database.productsPrice(20));

        System.out.println();
        System.out.println(database.customersInfo());

        System.out.println(database.customersInfoPlus());

    }
}
