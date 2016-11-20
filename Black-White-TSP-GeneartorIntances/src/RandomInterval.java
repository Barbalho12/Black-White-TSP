

import java.util.Random;

public class RandomInterval {
	private int MIN;
	private int MAX;
	private Random RAND;
	
	public RandomInterval(int min, int max){
		RAND = new Random(); 
		MIN = min;
		MAX = max;
	}
	
	public RandomInterval(Cor min, Cor max){
		RAND = new Random(); 
		MIN = min.ordinal();
		MAX = max.ordinal();
	}
	
	public int rand(){
		int n = RAND.nextInt(MAX+MIN+1) + MIN;
		return n;
	}
	
	public Cor randCor(){
		 return Cor.values()[rand()];
	}

}
