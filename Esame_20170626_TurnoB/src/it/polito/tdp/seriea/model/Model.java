package it.polito.tdp.seriea.model;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SimpleWeightedGraph<Season, ArcoPersonalizzato> grafo;

	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(ArcoPersonalizzato.class);
		SerieADAO dao = new SerieADAO();
		dao.popolaGrafo(grafo);
	}

}
