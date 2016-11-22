package br.ufrn.imd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.model.Aresta;
import br.ufrn.imd.model.Cor;
import br.ufrn.imd.model.Grafo;
import br.ufrn.imd.model.Vertice;

public class Main {
	
	public static void main(String[] args) {
		
		Grafo grafo = null;
		if (args.length == 1){
			//Lêr Grafo do arquivo passado como parametro
			grafo = readGrafo(args[0]);
		}else{
			System.err.println("File not exists or not correct!");
			System.exit(0);
		}
		
		grafo.printIntroInformations();
		
		EngineBWTSP engineBWTSP= new EngineBWTSP(grafo, grafo.getMaxVerticeWhite(), grafo.getMaxVerticeBlack());
		
		ResultBWTSP result = engineBWTSP.calculateBestTour();
		
		printSolution(result);
	}
	
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

	private static void printSolution(ResultBWTSP result) {
		System.out.println("\n-------------------------solution-----------------------------");
		result.print();
		result.printTime();
	}
	
//	private static Grafo generateGrafo() {
//		int RAND_MIN_PESO = 2; 
//		int RAND_MAX_PESO = 10; 
//		int MAX_INSTANCE = 4;
//		RandomInterval randCor = new RandomInterval(Cor.BRANCO, Cor.PRETO);
//		RandomInterval randCost = new RandomInterval(RAND_MIN_PESO, RAND_MAX_PESO);
//		Grafo grafo = new Grafo();
//		for(int i = 0; i < MAX_INSTANCE; i++){
//			
//			Vertice newVertice = new Vertice( randCor.randCor(), i);
//			grafo.addVertice(newVertice);
//			
//			for(Vertice v : grafo.getVertices()){
//				if(!v.equals(newVertice)){
//					newVertice.addAresta(new Aresta(randCost.rand(), v, newVertice));
//				}
//			}
//		}
//		grafo.build(); 
//		return grafo;
//	}

}
