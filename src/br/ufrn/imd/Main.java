package br.ufrn.imd;
import java.util.Random;

public class Main {
	
	static final Random RAND = new Random(); 
	static final int RAND_MIN_PESO = 5; 
	static final int RAND_MAX_PESO = 50; 
	
	/**
	 * @return Retorna uma cor aleatória (Preto ou Branco)
	 */
	static Cor randCor(){
		int n = RAND.nextInt(2);
		if (n == 0) {
			return Cor.BRANCO;
		} else {
			return Cor.PRETO;
		}
	}
	
	static int rand(int min, int max){
		int n = RAND.nextInt(max+min+1) + min;
		return n;
	}
	
	static int rand(){
		
		return rand(RAND_MIN_PESO, RAND_MAX_PESO);
	}
	
	
	static final int MAX_INSTANCE = 12;
	static //http://homepages.dcc.ufmg.br/~nivio/cursos/pa03/tp2/tp22/tp22.html
	Vertice ciclo[] = new Vertice[MAX_INSTANCE];
	static Vertice melhorciclo[]= new Vertice[MAX_INSTANCE];
	static int comprimento = 0;
	static int minimo = 9999999;
	
	static void otimo(int v, int nivel){
		Vertice.VERTICES_REFS.get(v).setVisited(true);
		ciclo[nivel] = Vertice.VERTICES_REFS.get(v);
		if(nivel == MAX_INSTANCE-1){
//			System.out.print("AEWW -> "+ ciclo[0].getId() + "," + ciclo[1].getId() + "," + ciclo[2].getId() + "," + ciclo[3].getId());
			comprimento = custo(ciclo);
//			System.out.println(" -> "+comprimento);
			if(comprimento < minimo){
				minimo = comprimento;
				for(int i = 0; i < ciclo.length; i++){
					melhorciclo[i] = ciclo[i];
				}
			}
		}
		for (int i = 0; i < MAX_INSTANCE; i++){
			if(!Vertice.VERTICES_REFS.get(i).isVisited()){
				otimo(i, nivel+1);
				Vertice.VERTICES_REFS.get(i).setVisited(false);
			}
		}
	}
	
	static int pesos [][] = new int [MAX_INSTANCE][MAX_INSTANCE];
	
	public static void preenche(){
		for(int i = 0; i < MAX_INSTANCE; i++){
			for (Aresta a : Vertice.VERTICES_REFS.get(i).getArestas()){
				pesos[a.getV1().getId()][a.getV2().getId()] = pesos[a.getV2().getId()][a.getV1().getId()] = a.getPeso();
			}
		}
	}

	static int custcalc;
	private static int custo(Vertice[] ciclo2) {
//		System.out.println();
		custcalc = 0;
		for (int i = 0; i < MAX_INSTANCE; i++){
			custcalc +=    pesos[ciclo2[i].getId()][(ciclo2[(i+1)%MAX_INSTANCE].getId())];
//			System.out.println(pesos[ciclo2[i].getId()][(ciclo2[(i+1)%MAX_INSTANCE].getId())]);
		}
		return custcalc;
	}

	
	static int s = 1;
	
	
	public static void main(String[] args) {
		System.out.println("------------------------------------------------------");
		System.out.println("This is The Black and White Traveling Salesman Problem");
		System.out.println("------------------------------------------------------");
		System.out.println("--> N = "+ MAX_INSTANCE);
		System.out.println("------------------------------------------------------");
		System.out.println("\nMatrix of edges cost\n");
		
		Grafo grafo = new Grafo();

		//Cria vértices do grafo
		for(int i = 0; i < MAX_INSTANCE; i++){
			Vertice newVertice = new Vertice(randCor(), i);
			grafo.addVertice(newVertice);
			
			for(Vertice v : grafo.getVertices()){
				if(!v.equals(newVertice)){
//					newVertice.addAresta(new Aresta(s++, v, newVertice));
					newVertice.addAresta(new Aresta(rand(), v, newVertice));
				}
			}
		}
		preenche();
		otimo(0, 0);
		for(int i = 0; i < MAX_INSTANCE; i++){
			for(int j = 0; j < MAX_INSTANCE; j++){
				System.out.print(pesos[i][j]+" ");
			}
			System.out.println("");
		}
		
//		for(Vertice vertice : grafo.getVertices()){
////			System.out.println(vertice.getId() + ", " + vertice.getCor());
//			System.out.println(vertice.toString());
//		}
		
		System.out.println("---------------------------------------");
		System.out.println("Quantidade de Arestas: " + Aresta.COUNTS);
		System.out.println("Quantidade de Vértices: " + Vertice.COUNTS);
		System.out.println("---------------------------------------");
		
		
		System.out.print("Best tour: ");
		for(int i = 0; i < melhorciclo.length; i++){
			System.out.print(melhorciclo[i].getId()+1);
			if(i < melhorciclo.length-1)
				System.out.print(" - ");
		}
		System.out.println(" ---> "+minimo);
		
	}

}
