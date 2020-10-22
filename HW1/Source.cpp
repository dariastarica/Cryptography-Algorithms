//Criptanaliza Vigenere
#define _CRT_SECURE_NO_WARNINGS
#include "functii.h"
#include <string.h>
#include <stdio.h>
#include <fstream>
#include <iostream>
#define MAX_LENGHT 30000

using namespace std;
ifstream fin("cripttext.txt");
ofstream fout("plaintext.txt");

char input[MAX_LENGHT], plaintext[MAX_LENGHT]="", cript[MAX_LENGHT];
char comanda[30], cheie[MAX_LENGHT];
int k[MAX_LENGHT];

int main()
{
	cout << "Comanda: ";
	cin >> comanda;
	while (fin >> input)
	{
		filtrare(input);
		strcat(cript, input);
	}
	if (strcmp(comanda, "decode") == 0)
	{
		int m = lungimea_cheii(cript);
		cheia(m,cript,k);
		fout << " cheia " << m << '\n';
		//fout << k;
	}
	else if (strcmp(comanda, "encode") == 0)
	{
		cout << "Cheia: ";
		cin >> cheie;
		filtrare(cheie);
		int n = strlen(cript);
		int m = strlen(cheie);
		for (int i = 0; i < n; i++)
		{
			
			int ch=((cript[i]) + (cheie[i % m])) % 26;
			plaintext[i] = ch + 'A';
		}
		fout << plaintext;
	}
}