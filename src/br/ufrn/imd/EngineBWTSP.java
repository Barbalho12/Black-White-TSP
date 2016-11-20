package br.ufrn.imd;

public class EngineBWTSP {
	
	private final int INSTANCE_LENGHT;
	private Grafo grafo;
	private Vertice currentTour[];
	private ResultBWTSP result;
	private int costCurrentTour;
	
	public EngineBWTSP(Grafo grafo, int instance_lenght){
		this.INSTANCE_LENGHT = instance_lenght;
		this.result = new ResultBWTSP(instance_lenght);
		this.grafo = grafo;
		this.currentTour = new Vertice[instance_lenght];
		this.costCurrentTour = 0;
	}
	
	public ResultBWTSP calculateBestTour(){
		long executionTime = System.currentTimeMillis();
		calculateBestTour(0, 0);
		executionTime = System.currentTimeMillis() - executionTime;
		result.setExecutionTime(executionTime);
		return result;
	}
	
	private void calculateBestTour(int v, int level){
		
		Vertice.VERTICES_REFS.get(v).setVisited(true);
		
		currentTour[level] = Vertice.VERTICES_REFS.get(v);
		
		if(level == INSTANCE_LENGHT-1){
			costCurrentTour = calculateCost(currentTour);
			if(costCurrentTour < result.getMinCostTour()){
				result.setMinCostTour(costCurrentTour);
				for(int i = 0; i < currentTour.length; i++){
					result.setBestTour(i, currentTour[i]);
				}
			}
		}
		
		for (int i = 0; i < INSTANCE_LENGHT; i++){
			if(!Vertice.VERTICES_REFS.get(i).isVisited()){
				calculateBestTour(i, level+1);
				Vertice.VERTICES_REFS.get(i).setVisited(false);
			}
		}
		
	}

	private int calculateCost(Vertice[] tour) {
		int cost = 0;
		for (int i = 0; i < tour.length; i++){
			cost += grafo.getCostMatrix(tour[i].getId(),(tour[(i+1)% tour.length].getId()));
		}
		return cost;
	}

}
