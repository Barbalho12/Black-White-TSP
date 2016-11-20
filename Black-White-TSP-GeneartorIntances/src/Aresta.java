
import java.util.ArrayList;
import java.util.List;

public class Aresta {
	static List<Aresta> ARESTAS_REFS = new ArrayList<>();
	static int COUNTS = 0;
	
	private int peso;
	private Vertice v1; 
	private Vertice v2;
	
	public Aresta(int peso, Vertice v1, Vertice v2) {
		
		this.peso = peso;
		this.v1 = v1;
		this.v2 = v2;
		
		COUNTS++;
		ARESTAS_REFS.add(this);
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Vertice getV1() {
		return v1;
	}

	public void setV1(Vertice v1) {
		this.v1 = v1;
	}

	public Vertice getV2() {
		return v2;
	}

	public void setV2(Vertice v2) {
		this.v2 = v2;
	}

	@Override
	public String toString() {
		return "{" + v1.getId()+" --"+peso+"-- "+v2.getId() +"}";
	} 
	
}
