package br.ufrn.imd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.model.Aresta;
import br.ufrn.imd.model.Cor;
import br.ufrn.imd.model.Grafo;
import br.ufrn.imd.model.Vertice;

public class Main {
	static Random rand = new Random();
	
	static Vertice[] bestGlobal; 	//Solução final
	static Grafo grafo;			//Grafo analisado
	static Vertice[] S0; 		//Solução inicial
	
	static int sizeBS = 0;
	static Vertice[] bestVS;
	static int min_cost_bestVS = Integer.MAX_VALUE;
	private static int BEST_SOLUCOES_INICIAIS_TEST = 1;
	
	private static long perturbationTime = 0;
	private static long verifyColorTime = 0;
	private static long calcCostTime = 0;

	private static long verifyColorFalseTime =0;
		
	/**
	 * Metaheuristica simulated Annealing
	 * @param T Temperatura inicial
	 * @param maxPetb máximo de pertubações aplicada em uma iteração
	 * @param coefTemp coeficiente de diminuição de temperatura
	 */
	static void simulatedAnnealing(int T, int maxPetb, int coefTemp){
		
		Vertice[] sCurrent = S0.clone();
		int tInit = T;
		
		bestGlobal = S0.clone();
		int costBestGlobal = calcCost(S0);
		int calcSCurrent =  costBestGlobal;
		
		/*Enquanto Temperatura maior que 0*/
		for( ; T > 0; T -= coefTemp){

			/*Enquanto máximo de sucesso ou máximo de pertubação por iteração*/
			for(int i = 0; i < maxPetb; i++){
				
				/*Pertuba a solução corrente*/
				Vertice[] Si = perturbationSimple(sCurrent);
				
				/*Diferença da solução pertubada com a solução corrente*/
				int calcSI = calcCost(Si);
				int Fi = calcSI - calcSCurrent; 
				
				double accept = (((calcCost(sCurrent)*0.001)/Fi) * T/tInit); 
				
				/*Verifica se melhorou ou dependendo da temperatura, aceita-se uma piora*/
				if ((Fi != 0) && ((Fi < 0) || (accept > rand.nextDouble()))){ 
					sCurrent = Si;
					calcSCurrent = calcSI;
					
					//Apesar de aceitação de pioras, matem-se um melhor global
					if(calcSI < costBestGlobal){
						costBestGlobal = calcSI;
						bestGlobal = Si.clone();
					}
				}
			}
		}
	}


	/**
	 * Pertuba uma solução corrente
	 * @param s2 solução corrente
	 * @return outro vetor com o resultado da pertubação
	 */
	
	


	private static Vertice[] perturbationSimple(Vertice[] s2) {
		long executionTime = System.currentTimeMillis();
		
		Vertice[] s = s2.clone();
		
		int a, b;

		while(true){
			
			/*Escolhe dois índices do caminho aleatoriamente*/
			a = rand.nextInt(s.length-1)+1;
			b = rand.nextInt(s.length-1)+1;
			
			/*Se forem difentes*/
			if(a!=b){
				
				/*Troca os vértices de índice a e b*/
				swap(s, a, b);
				
				/*Retorna se a restrição de cor for válida*/
				if (verifyColor(s, s.length-1)){
					perturbationTime += System.currentTimeMillis() - executionTime;
					return s;
				}
			}
			/*Se as condições não forem válidas, continua*/
		}
	}
	
	/**
	 * Pertuba uma solução corrente
	 * @param s2 solução corrente
	 * @return outro vetor com o resultado da pertubação
	 */
	@SuppressWarnings("unused")
	private static Vertice[] perturbationWithRange(Vertice[] s2, double shaking) {
		
		Vertice[] s = s2.clone();
		
		int range = (int) (shaking*(s.length)+1);
		
		int a, b;
		
		while(true){
			
			/*Escolhe dois índices do caminho aleatoriamente dentro do range*/
			a = rand(1, s.length);
			b = randInRange(s.length, range, a);
			
			/*Se forem difentes*/
			if(a != b){
				
				/*Troca os vértices de índice a e b*/
				swap(s, a, b);
				
				/*Retorna se a restrição de cor for válida*/
				if (verifyColor(s, s.length-1)){
					return s;
				}
			}
			/*Se as condições não forem válidas, continua*/
		}
	}


	private static int randInRange(int n, int range, int a) {
		int x = a-range;
		int y = a+range;
		if (x<=1){
			x = 1;
		}
		if (y>n){
			y = n;
		}
		return rand(x,y);
	}


	private static int rand(int min, int max) {
	    return rand.nextInt((max - min)) + min;
	}
	


	private static void swap(Vertice[] s, int chosen, int i) {
		Vertice temp = s[chosen];
		s[chosen] = s[i];
		s[i] = temp;
	}
	
	public static void main(String[] args){

		int TEMP_INIT = 1000;
		int MAX_PERTUB = 100;
		int COEFICIENTE_TEMPERATURA = 1;

		if(args.length >= 1){
			/*Ler o grafo*/
			grafo = readGrafo(args[0]);
			
		}else if(args.length == 5){
			grafo = readGrafo(args[0]);
			TEMP_INIT = Integer.valueOf(args[1]);
			MAX_PERTUB  = Integer.valueOf(args[2]);
			COEFICIENTE_TEMPERATURA = Integer.valueOf(args[3]);
		}else{
			/*Ler o grafo*/
			grafo = readGrafo("instances/intance_19_4_33_3_50.txt");
			MAX_PERTUB  = (int) Math.pow(grafo.getVertices().size(), 2);

		}
		/*variável para Solução inicial*/
		S0 = new Vertice[grafo.getVertices().size()];
		
		/*variável auxiliar solução inicial*/
		bestVS = new Vertice[grafo.getVertices().size()];
		
		/*Quantidade de soluções comparas para definir a solução inicial com menor custo*/
		BEST_SOLUCOES_INICIAIS_TEST = grafo.getVertices().size();
		
		/*Calculo solução inicial*/
		long executionTimeSI = System.currentTimeMillis();
		solucaoInit(0, 0);
		executionTimeSI = System.currentTimeMillis() - executionTimeSI;

		/*Calculo da meta-heuristica*/
		long executionTimeSA = System.currentTimeMillis();
		simulatedAnnealing(TEMP_INIT, MAX_PERTUB, COEFICIENTE_TEMPERATURA);
		executionTimeSA = System.currentTimeMillis() - executionTimeSA;

		/*Exibe os resultados*/
		showResults(executionTimeSI, executionTimeSA);
		
//		showResultsSimple(executionTimeSI, executionTimeSA);
		
	}

	
	/**
	 * Calcula uma solução inicial
	 * @param v indice do vertice do grafo
	 * @param level indice do do tour a ser analizado
	 * @return verdadeiro caso encontre solução
	 */
	private static boolean solucaoInit(int v, int level){
		
		 //Marca o vertice como visitado
		grafo.getVertice(v).setVisited(true);
		
		//Adiciona o vertice no tour
		S0[level] = grafo.getVertice(v); 
		
		//Verifica a quantidade de vertices pretos e brancos
		if (!verifyColor(S0, level))
			return false;
		
		//Se é um tour completo
		if(level == grafo.getVertices().size()-1){
			sizeBS++;
			int current_cost = calcCost(S0);
			if(current_cost < min_cost_bestVS){
				bestVS = S0.clone();
				min_cost_bestVS = current_cost;
			}
			if(sizeBS >= BEST_SOLUCOES_INICIAIS_TEST){
				S0 = bestVS;
				return true;
			}
		}
		for (int i = 0; i < grafo.getVertices().size(); i++){
			
			//Se o vértice não foi visitado, aplica a recursão, mudando o level para o proximo vértice do tour
			if(!grafo.getVertice(i).isVisited()){
				if(solucaoInit(i, level+1) == true){
					return true;
				}else{
					grafo.getVertice(i).setVisited(false); // após visitar seta o vertice não visitado
				}
			}
		}
		return false;
	}

	/**
	 * Verifica se a sequencia de cores está de acordo
	 * @param level passo de construção do tour
	 * @return validade do tour
	 */
	private static boolean verifyColor(Vertice[] currentS, int level) {
		long executionTime = System.currentTimeMillis();
		int p = 0;
		int b = 0;
		for(int i = 0; i <= level; i++){
			if(currentS[i%currentS.length].getCor().equals(Cor.BRANCO)){
				b++;
				p=0;
			}else{
				p++;
				b=0;
			}
			if (p > 1 && grafo.getCostMatrix(currentS[i-1].getId(), currentS[i].getId()) > grafo.getMaxVerticeBlack() 
					|| b > grafo.getMaxVerticeWhite() ){
				verifyColorTime += System.currentTimeMillis() - executionTime;
				return false;
			}
			
		}
		verifyColorFalseTime += System.currentTimeMillis() - executionTime;
		return true;
	}
	
	/**
	 * Calcula o custo de um tour completo
	 * @param tour tour a ser vericado
	 * @return o custo do tour
	 */
	private static int calcCost(Vertice[] tour) {
		long executionTime = System.currentTimeMillis();
		
		int cost = 0;
		for (int i = 0; i < tour.length; i++){
			cost += grafo.getCostMatrix(tour[i].getId(),(tour[(i+1)% tour.length].getId()));
		}
		calcCostTime += System.currentTimeMillis() - executionTime;
		return cost;
	}
	
	/**
	 * Exibe os resultados
	 * @param executionTimeSI Tempo de execução da solução inicial
	 * @param executionTimeSA Tempo de execução do simulatedAnnealing
	 */
	private static void showResults(long executionTimeSI, long executionTimeSA) {
		System.out.println("---------------------------");
		
		System.out.println("Initial Solution: ");
		for (int i = 0; i < S0.length; i++) {
			System.out.print(S0[i].getId()+1+""+S0[i].getCor().name().charAt(0)+" ");
		}
		System.out.print("= "+calcCost(S0));
		System.out.println(" : "+executionTimeSI/1000.0+"s");
		

		System.out.println("Solution: ");
		for (int i = 0; i < bestGlobal.length; i++) {
			System.out.print(bestGlobal[i].getId()+1+""+bestGlobal[i].getCor().name().charAt(0)+" ");
		}
		System.out.print("= "+calcCost(bestGlobal));
		System.out.println(" : "+executionTimeSA/1000.0+"s");
		long totaltime = executionTimeSI+executionTimeSA;
		System.out.println("\nN: "+grafo.getVertices().size()+" Time: "+totaltime/1000.0+" Branco: "+grafo.getMaxVerticeWhite()+" Preto: "+grafo.getMaxVerticeBlack());
	}
	
	/**
	 * Exibe os resultados
	 * @param executionTimeSI Tempo de execução da solução inicial
	 * @param executionTimeSA Tempo de execução do simulatedAnnealing
	 */
	@SuppressWarnings("unused")
	private static void showResultsSimple(long executionTimeSI, long executionTimeSA) {
		
		long totaltime = executionTimeSI+executionTimeSA;
		System.out.println(grafo.getVertices().size()+"\t"+
						   calcCost(S0)+"\t"+
						   calcCost(bestGlobal)+"\t"+
						   executionTimeSI+"\t"+
						   verifyColorTime+"\t"+
						   verifyColorFalseTime+"\t"+
						   calcCostTime+"\t"+
						   perturbationTime+"\t"+
						   totaltime);
	}
	
	/**
	 * Ler as informações do grafo de um arquivo
	 * @param path localizaçaõ do arquivo
	 * @return Grafo lido
	 */
	public static Grafo readGrafo(String path) {
		
		try {
			
			FileReader arq = new FileReader(path);
			BufferedReader lerArq = new BufferedReader(arq);
			List<String> linhas = new ArrayList<>();
			String linha = lerArq.readLine();
			while (linha != null) {
				linhas.add(linha);
				linha = lerArq.readLine();
			}
			arq.close();

			int verticesSize = linhas.get(0).split(" ").length;
			String coresRead[] = linhas.get(0).split(" ");
			
			Grafo grafo = new Grafo();
			
			for (int i = 0; i < verticesSize; i++) {
				Vertice newVertice = new Vertice( Cor.values()[Integer.valueOf(coresRead[i].trim())], i);
				grafo.addVertice(newVertice);
				
				for(Vertice v : grafo.getVertices()){
					if(!v.equals(newVertice)){
						int peso = Integer.valueOf(linhas.get(i+1).trim().split(" ")[v.getId()].trim());
						newVertice.addAresta(new Aresta(peso, v, newVertice));
					}
				}
			}
			grafo.setMaxVerticeWhite(Integer.valueOf(linhas.get(verticesSize+1).trim().split(" ")[0].trim()));
			grafo.setMaxVerticeBlack(Integer.valueOf(linhas.get(verticesSize+1).trim().split(" ")[1].trim()));
			
			grafo.build();
			grafo.setFilePath(path);

			return grafo;

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		return null;
	}
	
}
