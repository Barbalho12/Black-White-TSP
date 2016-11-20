import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Main {
	
	private static final int LENGHT_ARGS = 5; 		//Argumentos para criação
	private static final int MAX_TENTATIVAS = 100; 	//Maximo de tentativas para geração de uma instancia
	private static final String PATH_INSTANCE = "instances/";
	
	private static int MAX_INSTANCE = 4;			//Quantidade de vertices do grafo
	private static int MAX_SEQUENCE_WHITE = 1;  	//Numero maximo de vertices branco em sequencia
	private static int MAX_SEQUENCE_BLACK = 1; 		//Numero maximo de vertices preto em sequencia
	private static int RAND_MIN_PESO = 2; 			//Peso minimo de aresta
	private static int RAND_MAX_PESO = 10; 			//peso maximo de aresta
	
	public static void main(String[] args){
		
		if (args.length == LENGHT_ARGS){
			MAX_INSTANCE = Integer.valueOf(args[0]);
			MAX_SEQUENCE_WHITE = Integer.valueOf(args[1]); 
			MAX_SEQUENCE_BLACK = Integer.valueOf(args[2]);
			RAND_MIN_PESO = Integer.valueOf(args[3]); 
			RAND_MAX_PESO = Integer.valueOf(args[4]); 
		}else{
			MAX_INSTANCE = 4;
			MAX_SEQUENCE_WHITE = 1; 
			MAX_SEQUENCE_BLACK = 1;
			RAND_MIN_PESO = 2; 
			RAND_MAX_PESO = 10;
		}
		
		Grafo grafo = null;
		ResultBWTSP result = null;

		int tentativas = 0;
		
		while(tentativas < MAX_TENTATIVAS){
			tentativas++;
			try{
				grafo = generateGrafo(MAX_INSTANCE, RAND_MIN_PESO, RAND_MAX_PESO);
				EngineBWTSP engineBWTSP= new EngineBWTSP(grafo, MAX_SEQUENCE_WHITE, MAX_SEQUENCE_BLACK);
				result = engineBWTSP.calculateBestTour();
				break;
			}catch (Exception e) {

			}
		}
		
		String out = "";
		out += grafo.printVerticeColors();
		out += grafo.printCostMatrix();
		out += MAX_SEQUENCE_WHITE + " " + MAX_SEQUENCE_BLACK+"\n";
		out += result.print();
		System.out.println(MAX_SEQUENCE_WHITE + " " + MAX_SEQUENCE_BLACK);
		System.out.println("---------");
		
		System.out.println(tentativas);
		System.out.println("---------");
		
		String nameFile = "intance_"+MAX_INSTANCE+"_"+MAX_SEQUENCE_WHITE+"_"+MAX_SEQUENCE_BLACK+"_"+RAND_MIN_PESO+"_"+RAND_MAX_PESO+".txt";
		writeFile(PATH_INSTANCE+nameFile, out);
		
	}
	
	
	private static Grafo generateGrafo(int MAX_INSTANCE, int RAND_MIN_PESO, int RAND_MAX_PESO) {
		RandomInterval randCor = new RandomInterval(Cor.BRANCO, Cor.PRETO);
		RandomInterval randCost = new RandomInterval(RAND_MIN_PESO, RAND_MAX_PESO);
		Grafo grafo = new Grafo();
		for(int i = 0; i < MAX_INSTANCE; i++){
			
			Vertice newVertice = new Vertice( randCor.randCor(), i);
			grafo.addVertice(newVertice);
			
			for(Vertice v : grafo.getVertices()){
				if(!v.equals(newVertice)){
					newVertice.addAresta(new Aresta(randCost.rand(), v, newVertice));
				}
			}
		}
		grafo.build(); 
		return grafo;
	}
	
	static void writeFile(String path, String content){
		Writer arquivo;
		try {
			arquivo = new BufferedWriter(new FileWriter(path, false));
			arquivo.append(content);
			arquivo.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
