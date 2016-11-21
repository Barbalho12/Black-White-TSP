package br.ufrn.imd;

import java.util.ArrayList;
import java.util.List;


public class Grafo {
	
	private String filePath;		//Arquivo onde o grafo foi lido
	private List<Vertice> vertices;	//lista de vertices
	private int costMatrix[][];		//Matriz de custo das arestas
	private int maxVerticeWhite; 	//Máximo de vértices brancos
	private int maxVerticeBlack;	//máximo de vértices pretos
	
	public Grafo() {
		this.vertices = new ArrayList<>();
		this.setMaxVerticeWhite(1);			//Se a quantidade máxima não for setada é assumido que as cores alternam
		this.setMaxVerticeBlack(1);
	}
	
	/**
	 * Costrói a matriz de custos e as arestas dos vértices
	 */
	public void build(){
		costMatrix = new int [vertices.size()][vertices.size()];
		for(int i = 0; i < vertices.size(); i++){
			for (Aresta a : vertices.get(i).getArestas()){
				costMatrix[a.getV1().getId()][a.getV2().getId()] = costMatrix[a.getV2().getId()][a.getV1().getId()] = a.getPeso();
			}
		}
	}
	
	/**
	 * Imprime a matriz
	 * @return String correspondente
	 */
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
	
	/**
	 * Imprime as cores dos vértices B = branco, P = preto
	 * @return
	 */
	public String printVerticeColors(){
		String out = "";
		for(Vertice v : vertices){
			out += (v.getCor().ordinal()+" ");
		}
		out += "\n";
		System.out.print(out);
		return out;
	}
	
	/**
	 * Imprime informações de criação de objetos
	 */
	public void printIntroInformations(){
		System.out.println("------------------------------------------------------");
		System.out.println("This is The Black and White Traveling Salesman Problem");
		System.out.println("------------------------------------------------------");
		System.out.println("--> V = "+ getVertices().size());
		System.out.println("--> A = "+ Aresta.COUNTS);
		System.out.println("--> Branco = "+ getMaxVerticeWhite());
		System.out.println("--> Preto = "+ getMaxVerticeBlack());
		System.out.println("\n--> Vertex colors:");
		printVerticeColors();
		System.out.println("\n--> Matrix of edges cost:");
		printCostMatrix();
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
