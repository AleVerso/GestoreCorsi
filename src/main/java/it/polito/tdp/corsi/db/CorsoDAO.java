package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {

		String sql = "SELECT * " + "FROM corso " + "WHERE pd=?";

		List<Corso> result = new ArrayList<Corso>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				result.add(c);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}

		return result;

	}

	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo) {

		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot " + "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd = ? " + "GROUP BY c.codins, c.nome, c.crediti, c.pd "
				+ "ORDER BY tot";

		Map<Corso, Integer> result = new HashMap<Corso, Integer>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);

			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}

		return result;

	}
	
	public List<Studente> getStudentebyCorso(Corso corso){
		
		final String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola= i.matricola "
				+ "AND i.codins = ?";
		
		List<Studente> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Integer matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String CDS = rs.getString("CDS");
				
				Studente s = new Studente(matricola, nome, cognome, CDS);
				result.add(s);
				
			
				

			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
		
		return result;
		
		
		
		
	}

	public boolean esisteCorso(Corso corso) {
		
		final String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE codins=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			if(rs.next()) {	
				rs.close();
				st.close();
				conn.close();
				return true;
			}else{
				rs.close();
				st.close();
				conn.close();
				return false;
			}

			

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
		
	}

}
