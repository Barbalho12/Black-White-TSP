package br.ufrn.imd;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
	
	private List<Vertice> vertices;
	private int costMatrix[][];
	
	public Grafo() {
		this.vertices = new ArrayList<>();
	}
	
	public void build(){
		costMatrix = new int [vertices.size()][vertices.size()];
		for(int i = 0; i < vertices.size(); i++){
			for (Aresta a : vertices.get(i).getArestas()){
				costMatrix[a.getV1().getId()][a.getV2().getId()] = costMatrix[a.getV2().getId()][a.getV1().getId()] = a.getPeso();
			}
		}
	}
	
	public void printCostMatrix(){
		for(int i = 0; i < vertices.size(); i++){
			for(int j = 0; j < vertices.size(); j++){
				
				System.out.print(this.getCostMatrix(i,j)+" ");
			}
			System.out.println("");
		}
	}
	
	public void printInfoSizes(){
		System.out.println("---------------------------------------");
		System.out.println("Quantidade de Arestas: " + Aresta.COUNTS);
		System.out.println("Quantidade de Vértices: " + Vertice.COUNTS);
		System.out.println("---------------------------------------");
	}
	
	
	public int[][] getCostMatrix() {
		return costMatrix;
	}

	public int getCostMatrix(int i, int j) {
		return costMatrix[i][j];
	}
	
	public void setCostMatrix(int i, int j, int costElement) {
		costMatrix[i][j] = costElement;
	}
	
	public void setCostMatrix(int[][] costMatrix) {
		this.costMatrix = costMatrix;
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
