package it.polito.tdp.seriea.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private Map<Integer, Season> idMap;
	private SimpleWeightedGraph<Season, DefaultWeightedEdge> grafo;
	private SerieADAO dao = new SerieADAO();
	
	public Model() {
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listSeasons(idMap);
	}

	public String creaGrafo() {
		String risultato="";
		List<SquadreComuni> squadreComuni = dao.getSquadreComuni(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		for(SquadreComuni sc: squadreComuni) {
			DefaultWeightedEdge edge = grafo.getEdge(sc.getS1(), sc.getS2());
			if(edge==null) {
				Graphs.addEdgeWithVertices(grafo, sc.getS1(), sc.getS2(), sc.getPeso());
			}
		}
		risultato+="Grafo creato! Vertici: "+grafo.vertexSet().size()+" Archi: "+grafo.edgeSet().size()+"\n";
		return risultato;
	}

	public List<Season> getAllSeasons() {
		return dao.listSeasons(idMap);
	}

	public String getStagioniConSquadreComuni(Season s) {
		String risultato="";
		List<Season> vicini = Graphs.neighborListOf(grafo, s);
		Collections.sort(vicini);
		for(Season s1: vicini) {
			DefaultWeightedEdge edge = grafo.getEdge(s, s1);
			risultato+=s1.getSeason()+" Squadre Comuni: "+grafo.getEdgeWeight(edge)+"\n";
		}
		return risultato;
	}

	public List<Season> getVertici() {
		List<Season> vertici = new LinkedList<>();
		for(Season s: grafo.vertexSet()) {
			vertici.add(s);
		}
		return vertici;
	}
	


}
