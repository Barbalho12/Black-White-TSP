# Black-White-TSP
This is The Black and White Traveling Salesman Problem

## Orientação
Os programas para execução estão no diretório 'dist', além deles também há uma pasta com um conjunto de arquivos contendo as instâncias. O código fonte desses programas estão nos diretórios raiz do projeto.

* Black-White-TSP: Executa uma instancia passada como parâmetro (descrito a seguir);
* Black-White-TSP-GeneartorIntances: Cria instancias (descrito a seguir);
* Black-White-TSP-heuristic: Executa uma instancia passada como parâmetro (descrito a seguir);

## Execução
Executando Instância salva no diretório 'instances' no arquivo 'intance_5_3_4_3_40.txt'

### Algoritmo Exato

```sh
java -jar Black-White-TSP.jar instances/intance_5_3_4_3_40.txt
```

### Algoritmo Heurístico

```sh
java -jar Black-White-TSP-heuristic.jar instances/intance_5_3_4_3_40.txt
```

## Gerando instâncias
Para gerar instâncias, basta passar como parâmetro, o tamanho máximo das instâncias, dessa forma o programa irá gerar instâncias a partir de uma de tamanho 4, aumentando iterativamente até o numero estabelecido. As demais variáveis são aleatórias (com exceção de RAND_MAX_PESO):

* Numero máximo de vértices branco em sequencia: MAX_SEQUENCE_WHITE: 2~5;
* Numero máximo de vértices preto em sequencia: MAX_SEQUENCE_BLACK: 2~5;
* Peso mínimo de aresta: RAND_MIN_PESO: 2~5;
* peso máximo de aresta: RAND_MAX_PESO = 40;


Gerando 10 Instâncias de tamanhos i (4 <= i <= 14):
	
```sh
java -jar Black-White-TSP-gen.jar 14
```

Também é possível criar uma única instância definindo todos os cinco atributos na devida ordem como parâmetro:
	
```sh
java -jar Black-White-TSP-gen.jar a b c d e
```
	
* a = Tamanho da Instância
* b = MAX_SEQUENCE_WHITE
* c = MAX_SEQUENCE_BLACK
* d = RAND_MIN_PESO
* e = RAND_MAX_PESO

Gerando uma Instância de tamanhos 10, com 3 no máximo 3 vértices brancos e 2 pretos em sequência, e custo das arestas variando entre 5 e 50:
 
```sh
java -jar Black-White-TSP-gen.jar 10 3 2 5 50
```

## Autor
Felipe Barbalho Rocha


