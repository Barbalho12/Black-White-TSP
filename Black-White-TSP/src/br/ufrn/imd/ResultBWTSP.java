package br.ufrn.imd;



public class ResultBWTSP {
	
	private Vertice bestTour[];		//Melhor tour
	private int minCostTour;		//custo do tour
	private long executionTime;		//Tempo de execução
	
	public ResultBWTSP(int instance_lenght){
		this.minCostTour = Integer.MAX_VALUE;
		this.bestTour = new Vertice[instance_lenght];
		this.executionTime = 0;
	}
	
	/**
	 * Imprime a melhor solução
	 * @return retorna a String correspondente da impressão
	 */
	public String getPrint() {
		String out = "";
		for(int i = 0; i < bestTour.length; i++){
			out += (bestTour[i].getId()+1 +""+ bestTour[i].getCor().toString().charAt(0));
			if(i < bestTour.length-1)
				out+=" ";
		}
		out+=(" = "+minCostTour);
		return out;
	}
	
	/**
	 * Imprime a melhor solução
	 * @return retorna a String correspondente da impressão
	 */
	public void print() {
		System.out.println(getPrint());
	}
	
	
	
	/**
	 * Imprime o tempo de execução
	 */
	public void printTime(){
		System.out.println("Execution Time: "+(executionTime/1000.0)+" seconds");
	}

	public Vertice[] getBestTour() {
		return bestTour;
	}

	public void setBestTour(Vertice bestTourVertice[]) {
		this.bestTour = bestTourVertice.clone();
	}
	
	public void setBestTour(int i, Vertice bestTourVertice) {
		this.bestTour[i] = bestTourVertice;
	}

	public int getMinCostTour() {
		return minCostTour;
	}

	public void setMinCostTour(int minCostTour) {
		this.minCostTour = minCostTour;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public long getExecutionTime() {
		return executionTime;
	}

}
