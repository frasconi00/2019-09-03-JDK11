package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	
	private Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(Double C) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(C));
		
		System.out.println("vertici: "+grafo.vertexSet().size());
		
		for(Adiacenza a : dao.getAdiacenze(C)) {
			Graphs.addEdgeWithVertices(grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
		}
		
		System.out.println("archi: "+grafo.edgeSet().size());
		
	}
	
	public int nVertici() {
		System.out.println("metodo nVertici:"+this.grafo.vertexSet().size());
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else
			return true;
	}
	
	public List<Adiacenza> doCorrelate(String tipo1) {
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		for(DefaultWeightedEdge e : grafo.edgesOf(tipo1)) {
			Adiacenza a = new Adiacenza(tipo1, Graphs.getOppositeVertex(grafo, e, tipo1), (int)grafo.getEdgeWeight(e));
			result.add(a);
		}
		
		return result;
	}
	
	public List<String> getVertici() {
		List<String> vertici = new ArrayList<String>(grafo.vertexSet());
		
//		Collections.sort(vertici);
		
		return vertici;
	}
	
}
