import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		System.out.println("Ispolniteli");
		JDBC Audioteca = new JDBC();
		Audioteca.outputIspolniteli(Audioteca.getIspolniteli());
		

	}

}
