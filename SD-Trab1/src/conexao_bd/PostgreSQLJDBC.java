package conexao_bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
			results = stmt.executeQuery("SELECT Nota "
					+ "FROM trab1.historico WHERE matricula='" + matricula + "' "
							+ "AND cod_disciplina='" + codDisciplina+"'");
			
			if (results.next()) {
				return results.getFloat("Nota");
			} else {
				throw new Exception("Não existe nenhuma nota correspondente aos dados informados");
			}
			
		} finally {
			desconectar();
		}
		
	}
	
	public static ArrayList<Float> consultarNotas(String matricula) throws Exception{
		conectar();
		
		try {
			
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT nota AS Nota"
					+ "FROM trab1.historico WHERE matricula='" + matricula + "'");
			
			ArrayList<Float> notas = new ArrayList<>(); 
			
			while (results.next()){
				notas.add(results.getFloat("Nota"));
			}
			
			if (!notas.isEmpty()) {
				return notas;
			
			} else {
				throw new Exception("Não existe nenhuma nota correspondente aos dados informados.");
			}
			
		} finally {
			desconectar();
		}		
	}
	
	public static float consultarCR(String matricula) throws Exception{
		conectar();
		
		try {
			
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT AVG(nota) AS CR "
					+ "FROM trab1.historico WHERE matricula='" + matricula + "'");
			
			if (results.next()){
				return results.getFloat("CR");
			} else {
				throw new Exception("Não existe nenhuma nota correspondente aos dados informados.");
			}
			
		} finally {
			desconectar();
		}		
	}

	
}