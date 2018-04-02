package conexao_bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/sd";
	private static final String USER = "pl225";
	private static final String PASSWORD = "Pl2252122*";
	
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet results;
	
	
	private static void conectar() throws Exception {
          	  
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(URL, USER, PASSWORD);	
		
	}
	
	private static void desconectar () throws SQLException {
		if (stmt != null) stmt.close();
		if (results != null) results.close();
		if (conn != null) conn.close();
	}	
	
	public static float consultarNota(String matricula, String codDisciplina) throws Exception {
		conectar();
		
		try {
			
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT nota "
					+ "FROM trab1.historico WHERE matricula='" + matricula + "' "
							+ "AND cod_disciplina='" + codDisciplina+"'");
			
			if (results.next()) {
				return results.getFloat("nota");
			} else {
				throw new Exception("Não existe nenhuma nota correspondente aos dados informados");
			}
			
		} finally {
			desconectar();
		}
		
	}
	
}