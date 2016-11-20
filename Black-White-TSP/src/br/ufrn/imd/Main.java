package br.ufrn.imd;

public class Main {
	
	public static void main(String[] args) {
		
		int RAND_MIN_PESO = 5; 
		int RAND_MAX_PESO = 50; 
		int MAX_INSTANCE = 15;
		int MAX_SEQUENCE_WHITE = 1; 
		int MAX_SEQUENCE_BLACK = 2;
		
		Grafo grafo = new Grafo();
		
		printIntroInformations(MAX_INSTANCE);

		//TODO deverá ler de arquivo
		generateGrafo(grafo, MAX_INSTANCE, RAND_MIN_PESO, RAND_MAX_PESO);

		grafo.printCostMatrix();
		
		grafo.printInfoSizes();
		
		EngineBWTSP engineBWTSP= new EngineBWTSP(grafo, MAX_SEQUENCE_WHITE, MAX_SEQUENCE_BLACK);
		
		ResultBWTSP result = engineBWTSP.calculateBestTour();
		
		result.print();
		
	}

	private static void generateGrafo(Grafo grafo, int MAX_INSTANCE, int RAND_MIN_PESO, int RAND_MAX_PESO) {
		RandomInterval randCor = new RandomInterval(Cor.BRANCO, Cor.PRETO);
		RandomInterval randCost = new RandomInterval(RAND_MIN_PESO, RAND_MAX_PESO);
		
		for(int i = 0; i < MAX_INSTANCE; i++){
			Vertice newVertice = new Vertice(randCor.randCor(), i);
			grafo.addVertice(newVertice);
			
			for(Vertice v : grafo.getVertices()){
				if(!v.equals(newVertice)){
					newVertice.addAresta(new Aresta(randCost.rand(), v, newVertice));
				}
			}
		}
		grafo.build(); 
	}

	private static void printIntroInformations(int MAX_INSTANCE) {
		System.out.println("------------------------------------------------------");
		System.out.println("This is The Black and White Traveling Salesman Problem");
		System.out.println("------------------------------------------------------");
		System.out.println("--> N = "+ MAX_INSTANCE);
		System.out.println("------------------------------------------------------");
		System.out.println("\nMatrix of edges cost\n");
	}

}
