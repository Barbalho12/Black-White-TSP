

public class Main {
	
	public static void main(String[] args){
		
		int RAND_MIN_PESO = 5; 
		int RAND_MAX_PESO = 50; 
		int MAX_INSTANCE = 13;
		int MAX_SEQUENCE_WHITE = 3; 
		int MAX_SEQUENCE_BLACK = 2;
		
		int [] maxSequence = {MAX_SEQUENCE_WHITE, MAX_SEQUENCE_BLACK};
		
		Grafo grafo = new Grafo();
		generateGrafo(grafo, MAX_INSTANCE, RAND_MIN_PESO, RAND_MAX_PESO, maxSequence);
		grafo.printCostMatrix();
		grafo.printInfoSizes();
	}
	
	
	private static void generateGrafo(Grafo grafo, int MAX_INSTANCE, int RAND_MIN_PESO, int RAND_MAX_PESO, int []maxSequence) {
		RandomInterval randCor = new RandomInterval(Cor.BRANCO, Cor.PRETO);
		RandomInterval randCost = new RandomInterval(RAND_MIN_PESO, RAND_MAX_PESO);
		
//		int currentsColor[] = {0,0};
		for(int i = 0; i < MAX_INSTANCE; i++){
			Cor cor = randCor.randCor();
//			while (currentsColor[cor.ordinal()]+1 > maxSequence[cor.ordinal()]){
//				cor = randCor.randCor();
//			}
//			currentsColor[cor.ordinal()]++;
			
			Vertice newVertice = new Vertice(cor, i);
			grafo.addVertice(newVertice);
			
			for(Vertice v : grafo.getVertices()){
				if(!v.equals(newVertice)){
					newVertice.addAresta(new Aresta(randCost.rand(), v, newVertice));
				}
			}
		}
		grafo.build(); 
	}

}
