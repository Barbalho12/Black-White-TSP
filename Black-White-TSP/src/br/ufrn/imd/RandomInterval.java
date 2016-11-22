package br.ufrn.imd;

import java.util.Random;

import br.ufrn.imd.model.Cor;

/**
 * Classe para geração de números aleatórios
 *
 */
public class RandomInterval {
	private int MIN;
	private int MAX;
	public static final Random RAND = new Random(); 
	
	public RandomInterval(int min, int max){
		MIN = min;
		MAX = max;
	}
	
	public RandomInterval(Cor min, Cor max){
		MIN = min.ordinal();
		MAX = max.ordinal();
	}
	
	public int rand(){
		int n = RAND.nextInt(MAX - MIN+1) + MIN;
		return n;
	}
	
	public Cor randCor(){
		 return Cor.values()[rand()];
	}

}
