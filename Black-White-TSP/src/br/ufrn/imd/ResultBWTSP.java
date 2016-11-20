package br.ufrn.imd;


public class ResultBWTSP {
	
	private Vertice bestTour[];
	private int minCostTour;
	private long executionTime;
	
	public ResultBWTSP(int instance_lenght){
		this.minCostTour = Integer.MAX_VALUE;
		this.bestTour = new Vertice[instance_lenght];
		this.executionTime = 0;
	}
	
	public String print() {
		String out = "";
		for(int i = 0; i < bestTour.length; i++){
			out += (bestTour[i].getId()+1 +""+ bestTour[i].getCor().toString().charAt(0));
			if(i < bestTour.length-1)
				out+=" ";
		}
		out+=(" = "+minCostTour);
		System.out.println(out);
		return out;
	}
	
	public void printTime(){
		System.out.println("Execution Time: "+(executionTime/1000.0)+" seconds");
	}

	public Vertice[] getBestTour() {
		return bestTour;
	}

	public void setBestTour(int i, Vertice bestTourVertice) {
		this.bestTour[i] = bestTourVertice;
	}
	
	public void setBestTour(Vertice[] bestTour) {
		this.bestTour = bestTour;
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