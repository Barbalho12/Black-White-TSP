import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	private static final int LENGHT_ARGS = 5; 		//Argumentos para criação de uma instancia
	private static final int LENGHT_ARGS_N_INSTANCIAS = 1; 		//Argumentos para criação de N instancias 4~N
	private static final int NO_ARGS_N_INSTANCES = 12; 		//Quantidade de instancias padrão 4~12 
	private static final int MAX_TENTATIVAS = 100; 	//Maximo de tentativas para geração de uma instancia
	private static final String PATH_INSTANCE = "instances/";
	
	private static int MAX_INSTANCE = 4;			//Quantidade de vertices do grafo
	private static int MAX_SEQUENCE_WHITE = 1;  	//Numero maximo de vertices branco em sequencia
	private static int MAX_COST_BLACK = 1; 		//custo maximo de vertices preto em sequencia
	private static int RAND_MIN_PESO = 2; 			//Peso minimo de aresta
	private static int RAND_MAX_PESO = 10; 			//peso maximo de aresta
	
	private static String namesFiles;
	
	public static void main(String[] args){
		namesFiles = "";
		
		if (args.length == LENGHT_ARGS){
			generateInstance(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]),Integer.valueOf(args[4]));
		}else {
			int n = NO_ARGS_N_INSTANCES;
			if (args.length == LENGHT_ARGS_N_INSTANCIAS)
				n = Integer.valueOf(args[0]);
			RandomInterval rand = new RandomInterval(2, 5);
			RandomInterval randCostBlack = new RandomInterval(5, 50);
			for(int i = 4; i <= n; i++){
				generateInstance(i, rand.rand(), randCostBlack.rand(), rand.rand(), 50);
			}
		}
		writeFile(PATH_INSTANCE+"names_instances.txt", namesFiles);
		System.out.println("----END----");
	}
	
	public static void generateInstance(int max_instance, int max_sequence_white, int max_cost_black, int rand_min_peso, int rand_max_peso){

		MAX_INSTANCE = Integer.valueOf(max_instance);
		MAX_SEQUENCE_WHITE = Integer.valueOf(max_sequence_white); 
		MAX_COST_BLACK = Integer.valueOf(max_cost_black);
		RAND_MIN_PESO = Integer.valueOf(rand_min_peso); 
		RAND_MAX_PESO = Integer.valueOf(rand_max_peso); 

		
		Grafo grafo = null;
		ResultBWTSP result = null;

		int tentativas = 0;
		
		while(tentativas < MAX_TENTATIVAS){
			tentativas++;
			try{
				grafo = generateGrafo(MAX_INSTANCE, RAND_MIN_PESO, RAND_MAX_PESO);
				EngineBWTSP engineBWTSP= new EngineBWTSP(grafo, MAX_SEQUENCE_WHITE, MAX_COST_BLACK);
				result = engineBWTSP.calculateBestTour();
				break;
			}catch (Exception e) {

			}
		}
		
		String out = "";
		out += grafo.printVerticeColors();
		out += grafo.printCostMatrix();
		out += MAX_SEQUENCE_WHITE + " " + MAX_COST_BLACK+"\n";
		out += result.print();
		System.out.println(MAX_SEQUENCE_WHITE + " " + MAX_COST_BLACK);
		System.out.println("---------");
		
		System.out.println(tentativas);
		System.out.println("---------");
		
		String nameFile = "intance_"+MAX_INSTANCE+"_"+MAX_SEQUENCE_WHITE+"_"+MAX_COST_BLACK+"_"+RAND_MIN_PESO+"_"+RAND_MAX_PESO+".txt";
		
		writeFile(PATH_INSTANCE+nameFile, out);
		namesFiles += nameFile + "\n";
		result.printTime();
	}
	
	
	private static Grafo generateGrafo(int MAX_INSTANCE, int RAND_MIN_PESO, int RAND_MAX_PESO) {
		RandomInterval randCor = new RandomInterval(Cor.BRANCO, Cor.PRETO);
		RandomInterval rand = new RandomInterval(RAND_MIN_PESO, RAND_MAX_PESO);
		Grafo grafo = new Grafo();
		for(int i = 0; i < MAX_INSTANCE; i++){
			
			Vertice newVertice = new Vertice( randCor.randCor(), i);
			grafo.addVertice(newVertice);
			
			for(Vertice v : grafo.getVertices()){
				if(!v.equals(newVertice)){
					newVertice.addAresta(new Aresta(rand.rand(), v, newVertice));
				}
			}
		}
		grafo.build(); 
		return grafo;
	}
	
	static void writeFile(String path, String content){
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
