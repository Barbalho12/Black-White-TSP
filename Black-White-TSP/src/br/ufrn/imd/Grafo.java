package br.ufrn.imd;

import java.util.ArrayList;
import java.util.List;


public class Grafo {
	
	private String filePath;
	private List<Vertice> vertices;
	private int costMatrix[][];
	private int maxVerticeWhite; 
	private int maxVerticeBlack;
	
	public Grafo() {
		this.vertices = new ArrayList<>();
		this.setMaxVerticeWhite(1);
		this.setMaxVerticeBlack(1);
	}
	
	public void build(){
		costMatrix = new int [vertices.size()][vertices.size()];
		for(int i = 0; i < vertices.size(); i++){
			for (Aresta a : vertices.get(i).getArestas()){
				costMatrix[a.getV1().getId()][a.getV2().getId()] = costMatrix[a.getV2().getId()][a.getV1().getId()] = a.getPeso();
			}
		}
	}
	
	public String printCostMatrix(){
		String out = "";
		for(int i = 0; i < vertices.size(); i++){
			for(int j = 0; j < vertices.size(); j++){
				out+=this.getCostMatrix(i,j)+" ";
			}
			out += "\n";
		}
		System.out.print(out);
		return out;
	}
	
	public String printVerticeColors(){
		String out = "";
		for(Vertice v : vertices){
			out += (v.getCor().ordinal()+" ");
		}
		out += "\n";
		System.out.print(out);
		return out;
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

	public Vertice getVertice(int i) {
		return vertices.get(i);
	}
	
	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	public int getMaxVerticeWhite() {
		return maxVerticeWhite;
	}

	public void setMaxVerticeWhite(int maxVerticeWhite) {
		this.maxVerticeWhite = maxVerticeWhite;
	}

	public int getMaxVerticeBlack() {
		return maxVerticeBlack;
	}

	public void setMaxVerticeBlack(int maxVerticeBlack) {
		this.maxVerticeBlack = maxVerticeBlack;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
