


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
		verify();
		executionTime = System.currentTimeMillis() - executionTime;
		result.setExecutionTime(executionTime);
		return result;
	}
	
	private void verify() throws NullPointerException{
		for(int i = 0; i < result.getBestTour().length; i++){
			if (result.getBestTour()[i].equals(null)){
				new NullPointerException();
			}
		}
	}
	
	private boolean verifyColor(Vertice currentTour[], int level) {
		int p = 0;
		int b = 0;
		for(int i = 0; i <= level; i++){
			if(currentTour[i].getCor().equals(Cor.BRANCO)){
				b++;
				p=0;
			}else{
				p++;
				b=0;
			}
			if(p > maxSequence[1] || b > maxSequence[0])
				return false;
		}
		return true;
	}

	private void calculateBestTour(int v, int level){
		
		grafo.getVertice(v).setVisited(true);

		currentTour[level] = grafo.getVertice(v);
		
		//Verifica se o custo do atual caminho ultrapassou o melhor existente
		if (!verifyCurrentCost(level))
			return;
		
		//Verifica a quantidade de vertices pretos e brancos
		if (!verifyColor(currentTour, level))
			return;
		
		if(level == grafo.getVertices().size()-1){
			
			costCurrentTour = calculateCost(currentTour);
			
			if(costCurrentTour > result.getMinCostTour())
				return;
			
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

	private boolean verifyCurrentCost(int level) {
		int cost = 0;
		for (int i = 0; i < level; i++){
			cost += grafo.getCostMatrix(currentTour[i].getId(),(currentTour[(i+1)% currentTour.length].getId()));
			if(cost > result.getMinCostTour())
				return false;
		}
		return true;
	}

	private int calculateCost(Vertice[] tour) {
		int cost = 0;
		for (int i = 0; i < tour.length; i++){
			cost += grafo.getCostMatrix(tour[i].getId(),(tour[(i+1)% tour.length].getId()));
		}
		return cost;
	}

}
