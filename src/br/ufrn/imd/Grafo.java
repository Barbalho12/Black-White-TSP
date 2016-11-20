package br.ufrn.imd;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
	
	List<Vertice> vertices;

	public Grafo() {
		this.vertices = new ArrayList<>();
	}
	
	public Grafo(List<Vertice> vertices) {
		this.vertices = vertices;
	}
	
	public void addVertice(Vertice vertice){
		vertices.add(vertice);
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

}
