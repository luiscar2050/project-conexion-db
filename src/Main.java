import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/project",
                    "root",
                    ""
            );
            System.out.println("Genial, nos conectamos");

            // realizamos una consulta a la base de datos
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("SELECT * FROM employees");

            // iteramos los resultados para imprimir en consola.
            while (myRes.next()){
                System.out.println(myRes.getString("first_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }
    }
}