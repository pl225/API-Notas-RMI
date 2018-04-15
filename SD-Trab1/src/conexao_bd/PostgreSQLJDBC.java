package conexao_bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

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
					+ " FROM trab1.historico WHERE matricula='" + matricula + "'");
			
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
			results = stmt.executeQuery("SELECT AVG(nota) AS CR, COUNT(*) AS T "
					+ "FROM trab1.historico WHERE matricula='" + matricula + "'");
			
			if (results.next() && results.getInt("T") > 0){
				return results.getFloat("CR");
			} else {
				throw new Exception("Não existe nenhuma nota correspondente aos dados informados.");
			}
			
		} finally {
			desconectar();
		}		
	}
	
	public static boolean cadastrarNota(String matricula, String codDisciplina, float nota) throws Exception{
  		
		conectar();
        
		try {
			
			try {
           
	           String sql = "INSERT INTO trab1.historico (matricula, cod_disciplina, nota) VALUES (?,?,?)";
	
	           PreparedStatement ps = conn.prepareStatement(sql);
	           ps.setString(1, matricula);
	           ps.setString(2, codDisciplina);
	           ps.setFloat(3, nota);
	           ps.execute();
	           ps.close();
	           
			} catch (PSQLException e) {
				String sql = "UPDATE trab1.historico SET nota = ? WHERE cod_disciplina = ? AND matricula = ?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
		        ps.setString(3, matricula);
		        ps.setString(2, codDisciplina);
		        ps.setFloat(1, nota);
		        ps.execute();
		        ps.close();
		        
			}
			
			return true;
           
        } catch (Exception e) {
           throw new Exception("Ocorreu um erro durante a transação.");
        } finally {
        	desconectar();
        }
    }

	
}