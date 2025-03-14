# ProjetoPOOJava
#include<stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "pilha.h"


int main() {
	int x;
	int valor;
	int aux;
	int contador =0;
	Stack *vPrincipal = CriaStack(100);
	Stack *val_Par = CriaStack(100);
	Stack *val_Impar = CriaStack(100);


	while(true) {
		printf("Digite um numero \n");
		scanf("%d", & aux);
		if (x==999) {
			break;
		}
		push(vPrincipal, x);
        contador++;
	}   
		while(!isEmpty(vPrincipal)) {
			valor = topo(vPrincipal);
			if(valor % 2 == 0) {
				aux = pop(vPrincipal);
				push(val_Par, aux);
			}
			else {
				aux = pop(vPrincipal);
				push(val_Impar, aux);

			}
		}
		printf("Quantidade de elementos na pilha C) %d \n", sizeElements(vPrincipal));
		printf("Quantidade impar %d", sizeElements(val_Impar));
		printf("Quantidade Par %d", sizeElements(val_Par));
		printf("Quantidade %d", contador);
	}
