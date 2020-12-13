import java.sql.*;

public class JDBC {
	Connection conn;
	Statement st;
	ResultSet rs;
	public JDBC() throws SQLException {
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/audioteca?useSSL=false", "root", "");
		st = conn.createStatement();
	}
	
	public Ispolnitel[] getIspolniteli() throws SQLException {
		Ispolnitel[] isp;
		rs = st.executeQuery("select count(*) from Ispolnitel");
		rs.next();
		int n = rs.getInt(1);
		isp = new Ispolnitel[n];
		rs = st.executeQuery("select * from Ispolnitel");
		int i = 0;
		while(rs.next()) {
			isp[i] = new Ispolnitel();
			isp[i].Cod_ispolnitelia = rs.getInt(1); 
			isp[i].FIO_ispolnitelia = rs.getString(2); 
			isp[i].Grajdanstvo = rs.getString(3);
			isp[i].Pol = rs.getString(4);
			isp[i].Data_rojdenia = rs.getDate(5);
			i++;
		}
		return isp;
	}
	public void outputIspolniteli(Ispolnitel [] isp) {
		for(Ispolnitel i : isp) {
			for(int j = 0; j < 20; j++) System.out.print("=");
			System.out.println();
			System.out.println("Cod_ispolnitelia: " + i.Cod_ispolnitelia);
			System.out.println("FIO_ispolnitelia: " + i.FIO_ispolnitelia);
			System.out.println("Grajdanstvo: " + i.Grajdanstvo);
			System.out.println("Pol:" + i.Pol);
			System.out.println("Data_rojdenia: " + i.Data_rojdenia);
			for(int j = 0; j < 20; j++) System.out.print("=");
		}
	}
}
