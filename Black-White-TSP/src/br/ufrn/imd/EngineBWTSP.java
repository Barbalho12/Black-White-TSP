package br.ufrn.imd;

public class EngineBWTSP {

	private Grafo grafo;
	private Vertice currentTour[];
	private ResultBWTSP result;
	private int costCurrentTour;
	private int [] maxSequence; 
	int currentsColor[];
	
	public EngineBWTSP(Grafo grafo, int MAX_SEQUENCE_WHITE, int MAX_SEQUENCE_BLACK){
		this.grafo = grafo;
		this.result = new ResultBWTSP(this.grafo.getVertices().size());
		this.currentTour = new Vertice[this.grafo.getVertices().size()];
		this.costCurrentTour = 0;
		maxSequence = new int [2];
		maxSequence[0] = MAX_SEQUENCE_WHITE;
		maxSequence[1] = MAX_SEQUENCE_BLACK;
		
		currentsColor = new int[2];
		currentsColor[0] = 0;
		currentsColor[1] = 0;
	}
	
	public ResultBWTSP calculateBestTour(){
		long executionTime = System.currentTimeMillis();
		calculateBestTour(0, 0);
		executionTime = System.currentTimeMillis() - executionTime;
		result.setExecutionTime(executionTime);
		return result;
	}
	
	private void calculateBestTour(int v, int level){
		grafo.getVertice(v).setVisited(true);
		
		if(currentsColor[grafo.getVertice(v).getCor().ordinal()]+1 > maxSequence[grafo.getVertice(v).getCor().ordinal()]){
			return;
		}else{
			if (grafo.getVertice(v).getCor() == Cor.BRANCO){
				currentsColor[Cor.BRANCO.ordinal()]++;
				currentsColor[Cor.PRETO.ordinal()] = 0;
			}else{
				currentsColor[Cor.PRETO.ordinal()]++;
				currentsColor[Cor.BRANCO.ordinal()] = 0;
			}
		}
			
		currentTour[level] = grafo.getVertice(v);
		
		if(level == grafo.getVertices().size()-1){
			
			costCurrentTour = calculateCost(currentTour);
			
			if(costCurrentTour < result.getMinCostTour()){
				result.setMinCostTour(costCurrentTour);
				for(int i = 0; i < currentTour.length; i++){
					result.setBestTour(i, currentTour[i]);
				}
			}
		}
		
		for (int i = 0; i < grafo.getVertices().size(); i++){
			if(!grafo.getVertice(i).isVisited()){
				calculateBestTour(i, level+1);
				grafo.getVertice(i).setVisited(false);
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
