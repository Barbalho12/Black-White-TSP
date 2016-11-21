# Black-White-TSP
This is The Black and White Traveling Salesman Problem


## Execução
Executando Instancia salva no diretório 'instancia' no arquivo 'intance_5_3_4_3_40.txt'

```sh
java -jar Black-White-TSP.jar instancia/intance_5_3_4_3_40.txt
```

## Gerando instancias
Para gerar instancias basta passar, como parâmetro, o tamanho máximo da instâncias, dessa forma o programa irá gerar instancias apartir de uma de tamanho 4, aumentando iterativamente até o numero estabelecido, as demais variaveis são aleatórias (com excessão de RAND_MAX_PESO):

* Numero maximo de vertices branco em sequencia: MAX_SEQUENCE_WHITE:  2~5;
* Numero maximo de vertices preto em sequencia: MAX_SEQUENCE_BLACK:  2~5;
* Peso minimo de aresta: RAND_MIN_PESO:  2~5;
* peso maximo de aresta: RAND_MAX_PESO = 40;

Gerando 10 Instâncias de tamanhos i (4 <= i <= 14):
	
```sh
java -jar Black-White-TSP-gen.jar 14
```

Também é possivel criar uma única instancia definindo todos os cinco atributos na devida ordem como parametro:
	
```sh
java -jar Black-White-TSP-gen.jar a b c d e
```
	
* a = Tamanho da Instância
* b = MAX_SEQUENCE_WHITE
* c = MAX_SEQUENCE_BLACK
* d = RAND_MIN_PESO
* e = RAND_MAX_PESO

Gerando 1 Instância de tamanhos 10, com 3 no máximo 3 vértices brancos e 2 pretos em sequência, e custo das arestas variando entre 5 e 50:
 
```sh
java -jar Black-White-TSP-gen.jar 10 3 2 5 50
```

## Autor
Felipe Barbalho Rocha


