package br.ufrn.imd;
import java.util.ArrayList;
import java.util.List;

public class Vertice {
	static List<Vertice> VERTICES_REFS = new ArrayList<>();
	static int COUNTS = 0;
	
	private Cor cor;
	private int id;
	private boolean visited;
	private int visites;
	List<Aresta> arestas;
	
	public Vertice(Cor cor, int id) {
		this.cor = cor;
		this.id = id;
		this.visited = false;
		this.visites = 0;
		this.arestas = new ArrayList<>();
		
		COUNTS++;
		VERTICES_REFS.add(this);
	}
	
	public Vertice(Cor cor, int id, boolean visited, int visites, List<Aresta> arestas) {
		this.cor = cor;
		this.id = id;
		this.visited = visited;
		this.visites = visites;
		this.arestas = arestas;
		
		COUNTS++;
		VERTICES_REFS.add(this);
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getVisites() {
		return visites;
	}

	public void setVisites(int visites) {
		this.visites = visites;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}
	
	public void addAresta(Aresta aresta){
		if (!arestas.contains(aresta)){
			arestas.add(aresta);
		}
		if(!aresta.getV1().equals(this) && !aresta.getV1().getArestas().contains(aresta)){
			aresta.getV1().addAresta(aresta);
		}else if (!aresta.getV2().getArestas().contains(aresta)){
			aresta.getV2().addAresta(aresta);
		}
		
	}
	
	public void addVisit(){
		this.visites++;
	}

	@Override
	public String toString() {
		return "Vertice { "+id+", " + cor + ", visites = " + visites + ", arestas = "+ arestas + " }";
	}
	
	
	

}
