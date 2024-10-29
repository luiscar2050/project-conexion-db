import java.sql.*;
import java.util.Scanner;

public class ConexionMySQL {

    private static Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/project";
        String usuario = "root";
        String contraseña = "";
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    public static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario )
            throws SQLException {
        String sql = "INSERT INTO empleado (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    public static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM empleado";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Cargo: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    public static void actualizarEmpleado(Connection conexion, int id, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "UPDATE empleado SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    public static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM empleado WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }

    public static void main(String[] args) {
        try (Connection conexion = establecerConexion();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conexión establecida con éxito.");
            boolean salir = false;

            while (!salir) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Insertar empleado");
                System.out.println("2. Consultar empleados");
                System.out.println("3. Actualizar empleado");
                System.out.println("4. Eliminar empleado");
                System.out.println("5. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese el cargo: ");
                        String cargo = scanner.nextLine();
                        System.out.print("Ingrese el salario: ");
                        double salario = scanner.nextDouble();
                        insertarEmpleado(conexion, nombre, cargo, salario);
                        break;

                    case 2:
                        consultarEmpleados(conexion);
                        break;

                    case 3:
                        System.out.print("Ingrese el ID del empleado a actualizar: ");
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Ingrese el nuevo cargo: ");
                        String nuevoCargo = scanner.nextLine();
                        System.out.print("Ingrese el nuevo salario: ");
                        double nuevoSalario = scanner.nextDouble();
                        actualizarEmpleado(conexion, idActualizar, nuevoNombre, nuevoCargo, nuevoSalario);
                        break;

                    case 4:
                        System.out.print("Ingrese el ID del empleado a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        eliminarEmpleado(conexion, idEliminar);
                        break;

                    case 5:
                        salir = true;
                        System.out.println("Saliendo del programa.");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la conexión o en las operaciones.");
        }
    }
}