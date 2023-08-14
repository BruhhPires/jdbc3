package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();  // CONECTA O BANDCO DE DADOS
			st = conn.prepareStatement( // INCLUI INFORMAÇÕES AO BANCO DE DADOS
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " // DEVE SER NOMEADO CADA VALOR CONFORME TABELA
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", // CADA '?' É UM CAMPO ONDE PODERÁ SER INCLUIDO A INFORMAÇÃO
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("15/03/1989").getTime())); // DEVE SER INSTANCIADO O FORMATO DATA DO SQL
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {                   // SE ROW MAIOR QUE ZERO
				ResultSet rs = st.getGeneratedKeys(); // SERÁ INSTANCIADO NO METODO RESULTSET A INFORMAÇÃO DO VALOR DO CAMPO
				while(rs.next()) {                    // SE A LINHA NÃO FOR NULA
					int id = rs.getInt(1);            // SERÁ ATRIBUIDO O VALOR INTEIRO DO CAMPO A VARIAVEL ID
					System.out.println("Done! ID = " + id);
				}
			}
			else {
				System.out.println("No Rown affected!");
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(st);
			
		}

	}

}
