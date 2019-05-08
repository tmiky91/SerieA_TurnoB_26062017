package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.model.ArcoPersonalizzato;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import javafx.scene.shape.Arc;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void popolaGrafo(SimpleWeightedGraph<Season, ArcoPersonalizzato> grafo) {
		// QUERY GENERICA SE NON CONOSCO IL CALCIO (Non completa manca peso e duplicati)
		String sql = "select m1.Season as s1, m1.HomeTeam as ht1, m1.AwayTeam as at1, m2.Season as s2, m2.HomeTeam as ht2, m2.AwayTeam as at2\r\n"
				+ "from matches as m1, matches as m2\r\n" + "where m1.Season!=m2.Season\r\n"
				+ "and (m1.HomeTeam=m2.HomeTeam or m1.AwayTeam=m2.AwayTeam or m1.AwayTeam=m2.HomeTeam or m1.HomeTeam=m2.AwayTeam)\r\n"
				+ "\r\n";

		// QUERY SE CONOSCO IL CALCIO
		// String sql = "select m1.Season, m2.Season\r\n" +
		// "from matches as m1, matches as m2\r\n" +
		// "group by m1.Season, m2.Season";

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Season s1 = new Season(res.getInt("s1"));
				Season s2 = new Season(res.getInt("s2"));

				Team t1 = new Team(res.getString("ht1"));
				Team t2 = new Team(res.getString("ht2"));
				Team t3 = new Team(res.getString("at1"));
				Team t4 = new Team(res.getString("at2"));
				Team teamComune = null;

				if (t1.equals(t2)) {
					teamComune = t1;
				}
				if (t2.equals(t3)) {
					teamComune = t2;
				}
				if (t3.equals(t4)) {
					teamComune = t3;
				}
				if (t1.equals(t4)) {
					teamComune = t1;
				}
				if (!grafo.containsVertex(s1)) {
					grafo.addVertex(s1);
				}
				if (!grafo.containsVertex(s2)) {
					grafo.addVertex(s2);
				}
				if (!grafo.containsEdge(s1, s2) && !grafo.containsEdge(s2, s1)) {
					ArcoPersonalizzato arco = grafo.addEdge(s1, s2);
					arco.addTeam(teamComune);
				}
				else {
					ArcoPersonalizzato arco = grafo.getEdge(s1, s2);
					arco.addTeam(teamComune);
				}

			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}
