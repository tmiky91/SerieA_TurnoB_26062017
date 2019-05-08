package it.polito.tdp.seriea.model;

import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ArcoPersonalizzato extends DefaultWeightedEdge{
	
	private Set<Team> teams; //Il set è una lista che non ammette duplicati, lo creo dell'oggetto di cui voglio eliminarli

	// implicitamente aggiorna peso perchè in questo caso il peso è dato dal numero di squadre comuni in 2 stagioni
	public void addTeam(Team t1) {
		teams.add(t1);
	}
	
	public double getPeso( ) {
		return teams.size();
	}

}
