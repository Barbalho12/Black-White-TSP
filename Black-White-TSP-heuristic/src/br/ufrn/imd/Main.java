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
	
	static Vertice[] soulucao;
	static Grafo grafo;
	static Vertice[] S0;
	
	static int sizeBS = 0;
	static Vertice[] bestVS;
	static int min_cost_bestVS = Integer.MAX_VALUE;
	private static int BEST_SOLUCOES_INICIAIS_TEST = 1;
	
	/**
	 * metaheuristica simulated Annealing
	 * @param S0 Solução inicial
	 * @param tempInit Temperatura inicial
	 * @param maxIteration máximo de iteração no loop em quanto a temperatura é diferente de zero
	 * @param maxPertub máximo de pertubações aplicada em uma iteração
	 * @param goodSuccess quantidade de sucessos desejados
	 * @param maxSuccessByIt
	 * @param coeficienteTemperatura
	 */
	static void simulatedAnnealing(Vertice[] S0, int tempInit, int maxIteration, int maxPertub, int goodSuccess, int maxSuccessByIt, int coeficienteTemperatura){
		
		soulucao = S0.clone();
		int T = tempInit;
		int j = 1;
		int nSucesso;
		
		/*Executa em quanto não tiver sucessos ou o numeros maximo de iterações não for atingido*/
		do{
			int i = 1;
			nSucesso = 0;
			
			/*perturbação em uma iteração*/
			do{
				
				/*Pertuba a solução*/
				Vertice[] Si = perturbation(soulucao);
				
				/*Diferença da solução pertubada com a solução corrente*/
				int Fi = calculateCost(Si) - calculateCost(soulucao); 
	
				if ((Fi <= 0) || (((-Fi)/T) > rand.nextDouble())){ /*Verifica se melhorou ou dependendo da temperatura, aceita-se uma piora*/
					soulucao = Si;
					nSucesso++;
				}
				i++;
				
			}while(!((nSucesso >= maxSuccessByIt) || (i > maxPertub)));
			
			T-=coeficienteTemperatura; /*Diminuição da temperatura*/

			j++; /*contador iterações*/
			
		}while(((nSucesso < goodSuccess) || (j > maxIteration)) && T>0);
	}


	/**
	 * Pertuba uma solução corrente
	 * @param s2 solução corrente
	 * @return outro vetor com o resultado da pertubação
	 */
	private static Vertice[] perturbation(Vertice[] s2) {
		Vertice[] s = s2.clone();
		int a, b;
		Vertice temp;

		while(true){
			a = rand.nextInt(s.length-1)+1;
			b = rand.nextInt(s.length-1)+1;
			
			if(a!=b){
				temp = s[a];
				s[a] = s[b];
				s[b] = temp;
				
				if (verifyColor(s, s.length-1)){
					return s;
				}else{
					continue;
				}
			}else{
				continue;
			}
		}
	}
	
	public static void main(String[] args){

		int TEMP_INIT = 1000;
		int MAX_ITERATION = 10000;
		int MAX_PERTUB = 10;
		int GOOD_SUCCESS  = 1000;
		int MAX_SUCCESS_BY_IT = 1000;
		int COEFICIENTE_TEMPERATURA = 1;

		if(args.length >= 1){
			/*Ler o grafo*/
			grafo = readGrafo(args[0]);
			
		}else if(args.length == 7){
			grafo = readGrafo(args[0]);
			TEMP_INIT = Integer.valueOf(args[1]);
			MAX_ITERATION = Integer.valueOf(args[2]);
			MAX_PERTUB  = Integer.valueOf(args[3]);
			GOOD_SUCCESS  = Integer.valueOf(args[4]);
			MAX_SUCCESS_BY_IT = Integer.valueOf(args[5]);
			COEFICIENTE_TEMPERATURA = Integer.valueOf(args[6]);

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
		simulatedAnnealing(S0, TEMP_INIT, MAX_ITERATION, MAX_PERTUB, GOOD_SUCCESS, MAX_SUCCESS_BY_IT, COEFICIENTE_TEMPERATURA);
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
		
		grafo.getVertice(v).setVisited(true); //Marca o vertice como visitado
		
		S0[level] = grafo.getVertice(v); //Adiciona o vertice no tour
		
		//Verifica a quantidade de vertices pretos e brancos
		if (!verifyColor(S0, level))
			return false;
		
		//Se é um tour completo
		if(level == grafo.getVertices().size()-1){
			sizeBS++;
			int current_cost = calculateCost(S0);
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
			if (p > 1 && grafo.getCostMatrix(currentS[i-1].getId(), currentS[i].getId()) > grafo.getMaxVerticeBlack()) return false;
			if(b > grafo.getMaxVerticeWhite()) return false;
		}
		return true;
	}
	
	/**
	 * Calcula o custo de um tour completo
	 * @param tour tour a ser vericado
	 * @return o custo do tour
	 */
	private static int calculateCost(Vertice[] tour) {
		int cost = 0;
		for (int i = 0; i < tour.length; i++){
			cost += grafo.getCostMatrix(tour[i].getId(),(tour[(i+1)% tour.length].getId()));
		}
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
		System.out.print("= "+calculateCost(S0));
		System.out.println(" : "+executionTimeSI/1000.0+"s");
		

		System.out.println("Solution: ");
		for (int i = 0; i < soulucao.length; i++) {
			System.out.print(soulucao[i].getId()+1+""+soulucao[i].getCor().name().charAt(0)+" ");
		}
		System.out.print("= "+calculateCost(soulucao));
		System.out.println(" : "+executionTimeSA/1000.0+"s");
		long totaltime = executionTimeSI+executionTimeSA;
		System.out.println("\nN: "+grafo.getVertices().size()+" Time: "+totaltime/1000.0+" Branco: "+grafo.getMaxVerticeWhite()+" Preto: "+grafo.getMaxVerticeBlack());
	}
	
//	/**
//	 * Exibe os resultados
//	 * @param executionTimeSI Tempo de execução da solução inicial
//	 * @param executionTimeSA Tempo de execução do simulatedAnnealing
//	 */
//	private static void showResultsSimple(long executionTimeSI, long executionTimeSA) {
//		
//		long totaltime = executionTimeSI+executionTimeSA;
//		
//		System.out.println(grafo.getVertices().size()+"\t"+
//						   calculateCost(S0)+"\t"+
//						   calculateCost(soulucao)+"\t"+
//						   executionTimeSI+"\t"+
//						   executionTimeSA+"\t"+
//						   totaltime);
//	}
	
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
