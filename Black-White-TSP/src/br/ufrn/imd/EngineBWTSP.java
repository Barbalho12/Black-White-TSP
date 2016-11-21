package br.ufrn.imd;


public class EngineBWTSP {

	private Grafo grafo;			//Grafo
	private Vertice currentTour[];	//Tour em análise
	private ResultBWTSP result;		//Resultado do melhor tour, com seu custo e o tempo de execução
	private int costCurrentTour;	//Custo do tour em construção
	private int [] maxSequence; 	//Quantidade máxima de brancos [0], e quantidade máxima de pretos [1]
	
	/**
	 * Construtor
	 * @param grafo Grafo analisado
	 * @param MAX_SEQUENCE_WHITE Máximo  de vértices Brancos em sequência
	 * @param MAX_SEQUENCE_BLACK Máximo  de vértices Pretos em sequência
	 */
	public EngineBWTSP(Grafo grafo, int MAX_SEQUENCE_WHITE, int MAX_COST_BLACK){
		this.grafo = grafo;
		this.result = new ResultBWTSP(this.grafo.getVertices().size());
		this.currentTour = new Vertice[this.grafo.getVertices().size()];
		this.costCurrentTour = 0;
		maxSequence = new int [2];
		maxSequence[0] = MAX_SEQUENCE_WHITE;
		maxSequence[1] = MAX_COST_BLACK;
	}
	
	/**
	 * Calcula o melhor tour e retorna um objeto ResultBWTSP
	 * @return ResultBWTSP com melhor tour e o tempo de execução
	 */
	public ResultBWTSP calculateBestTour(){
		long executionTime = System.currentTimeMillis();
		calculateBestTour(0, 0);
		verify();
		executionTime = System.currentTimeMillis() - executionTime;
		result.setExecutionTime(executionTime);
		return result;
	}
	
	/**
	 * Verifica se foi possivel encontrar resultado
	 * @throws NullPointerException
	 */
	private void verify() throws NullPointerException{
		for(int i = 0; i < result.getBestTour().length; i++){
			if (result.getBestTour()[i].equals(null)){
				new NullPointerException();
			}
		}
	}
	
	/**
	 * Verifica se a sequencia de cores está de acordo
	 * @param currentTour tour em construção
	 * @param level passo de construção do tour
	 * @return validade do tour
	 */
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
			
			if (p > 1 && grafo.getCostMatrix(currentTour[i-1].getId(), currentTour[i].getId()) > maxSequence[1]) return false;
			if(b > maxSequence[0]) return false;
			
//			if(p > maxSequence[1] || b > maxSequence[0])
//				return false;
		}
		return true;
	}

	/**
	 * Calcula o melhor tour, recusivamente para cada level do tamanho do tour, 
	 * e armazena o resultado em result.
	 * @param v vértice visitado pelo tour
	 * @param level índice do tour
	 */
	private void calculateBestTour(int v, int level){
		
		grafo.getVertice(v).setVisited(true); //Marca o vertice como visitado

		currentTour[level] = grafo.getVertice(v); //Adiciona o vertice no tour
		
		//Verifica se o custo do atual caminho ultrapassou o melhor existente
		if (!verifyCurrentCost(level))
			return;
		
		//Verifica a quantidade de vertices pretos e brancos
		if (!verifyColor(currentTour, level))
			return;
		
		//Se é um tour completo
		if(level == grafo.getVertices().size()-1){
			
			//Calcula o custo do tour
			costCurrentTour = calculateCost(currentTour);
			
			//verifica se ultrapassou o minimo custo ja encontrado
			if(costCurrentTour >= result.getMinCostTour()){
				return;
			}else{
				//O melhor resultado é adicionado ao resultado
				result.setMinCostTour(costCurrentTour);
				result.setBestTour(currentTour);
			}
		}
		
		
		for (int i = 0; i < grafo.getVertices().size(); i++){
			
			//Se o vértice não foi visitado, aplica a recursão, mudando o level para o proximo vértice do tour
			if(!grafo.getVertice(i).isVisited()){
				calculateBestTour(i, level+1);
				grafo.getVertice(i).setVisited(false); // após visitar seta o vertice não visitado
			}
		}
	}

	
	/**
	 * Verifica se o custo de um tour em construção está abixo ou igual ao menor 
	 * tour completo
	 * @param level
	 * @return verdadeiro, se o custo de um tour em construção está abixo ou igual ao menor tour completo
	 */
	private boolean verifyCurrentCost(int level) {
		int cost = 0;
		for (int i = 0; i < level; i++){
			cost += grafo.getCostMatrix(currentTour[i].getId(),(currentTour[(i+1)% currentTour.length].getId()));
			if(cost > result.getMinCostTour())
				return false;
		}
		return true;
	}

	/**
	 * Calcula o custo de um tour completo
	 * @param tour tour a ser vericado
	 * @return o custo do tour
	 */
	private int calculateCost(Vertice[] tour) {
		int cost = 0;
		for (int i = 0; i < tour.length; i++){
			cost += grafo.getCostMatrix(tour[i].getId(),(tour[(i+1)% tour.length].getId()));
		}
		return cost;
	}

}
