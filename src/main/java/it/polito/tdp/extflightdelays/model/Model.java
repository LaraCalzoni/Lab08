package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;


public class Model {
	
	Graph<Airport, DefaultWeightedEdge> grafo;
	Map<Integer, Airport> idMap;
	ExtFlightDelaysDAO dao;
	List<Flight> voli;
	
	public Model() {
		idMap = new HashMap<Integer, Airport>();
		dao = new ExtFlightDelaysDAO();
		voli = dao.loadAllFlights();
	}
	
	
	public void creaGrafo(int distanza) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);	
		dao.loadAllAirports(idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for(Adiacenza a : dao.getVoli()) {
			Airport a1 = this.cercaAirport(a.getId1());
			Airport a2 = this.cercaAirport(a.getId2());
			Graphs.addEdge(this.grafo, a1, a2, a.getDistanza());
		}
		System.out.println("GRAFO CREATO!");
		System.out.println("# vertici: "+this.grafo.vertexSet().size());
		System.out.println("# archi: "+this.grafo.edgeSet().size());
	}
	
	private Airport cercaAirport(int id){
		for(Airport a : idMap.values()) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	
}
