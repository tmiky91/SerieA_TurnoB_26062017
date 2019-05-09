package it.polito.tdp.seriea.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SimpleWeightedGraph<Season, ArcoPersonalizzato> grafo;

	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(ArcoPersonalizzato.class);
		SerieADAO dao = new SerieADAO();
		dao.popolaGrafo(grafo);
		
		//System.out.println(grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi");
	}

	public static List<Season> getAllSeasons() {
		SerieADAO dao = new SerieADAO();
		return dao.listSeasons();
	}

	public String getViciniEPeso(Season s) {
		String risultato="";
		grafo = new SimpleWeightedGraph<>(ArcoPersonalizzato.class);
		SerieADAO dao = new SerieADAO();
		dao.popolaGrafo(grafo);
		List<Season> vicini = new LinkedList<>(Graphs.neighborListOf(grafo, s));
		Collections.sort(vicini);
		for(Season s1: vicini) {
			ArcoPersonalizzato arco = grafo.getEdge(s, s1);
			risultato+=s1.getSeason()+" Peso: "+arco.getPeso()+"\n";
		}
		return risultato;
	}

}
