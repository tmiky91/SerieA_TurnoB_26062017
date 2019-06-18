package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.SquadreComuni;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons(Map<Integer, Season> idMap) {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMap.containsKey(res.getInt("season"))) {
					result.add(new Season(res.getInt("season"), res.getString("description")));
					idMap.put(res.getInt("season"), new Season(res.getInt("season"), res.getString("description")));
				}else {
					result.add(idMap.get(res.getInt("season")));
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
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

	public List<SquadreComuni> getSquadreComuni(Map<Integer, Season> idMap) {
		final String sql=	"select m1.Season as s1, m2.Season as s2, count(distinct m1.HomeTeam) as peso " + 
							"from matches as m1, matches as m2 " + 
							"where m1.Season > m2.Season " + 
							"and (m1.HomeTeam=m2.HomeTeam or m1.HomeTeam=m2.AwayTeam) " + 
							"group by s1, s2";
		List<SquadreComuni> result = new LinkedList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Season s1 = idMap.get(res.getInt("s1"));
				Season s2 = idMap.get(res.getInt("s2"));
				if(s1!=null && s2!=null) {
					SquadreComuni sc = new SquadreComuni(s1, s2, res.getDouble("peso"));
					result.add(sc);
				}
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
