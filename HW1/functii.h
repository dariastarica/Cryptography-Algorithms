#pragma once
#include <string.h>
#include <stdio.h>
#include <iostream>
#define MAX_LG 30000
using namespace std;

void filtrare(char* string)
{
	int n;
	n = strlen(string);
	
	for (int i = 0; i < n; i++)
	{
		if (string[i] >= 'a' && string[i] <= 'z')
		{
			string[i] = string[i] - 32;
		}
		else if (string[i] >= 'A' && string[i] <= 'Z')
		{
			continue;
		}
		else
		{
			strcpy(string + i, string + i + 1);
			n--;
			i--;
		}
	}
}

void shift(char* string, int k)
{
	int n = strlen(string);
	for (int i = 0; i < n; i++)
	{
		int c = ((string[i]-65 + k) % 26);
		string[i] = c + 'A';
	}
}
int f(int i, char* string) //nr de aparitii ale simbolului i in string
{
	
	char ch;
	int k = 0, n = strlen(string);
	ch = (char)(i + 65);
	for (int j = 0; j < n; j++) {
		if (string[j] == ch)
			k++;
	}
	return k;
}

double IC(char* string) //probabilitatea ca x sa aiba 2 aparitii a unui cacracter in el - pt lungimea cheii
{
	double suma = 0;
	double fct;
	double n = strlen(string);
	for (int i = 0; i < 26; i++)
	{
		fct = (double)f(i, string);
		suma += (fct / n) * ((fct - 1) / (n - 1));
	}
	return suma;
}

double MIC(char* string) // -pt aflarea cheii
{
	double suma = 0;
	double fct;
	double n = strlen(string);
	double freq[26] = { 8.55, 1.60, 3.16, 3.87, 12.10, 2.18, 2.09, 4.96, 7.33, 0.22, 0.81, 4.21, 2.53, 7.17, 7.47, 2.07, 0.10,
					6.33, 6.73, 8.94, 2.68, 1.06, 1.83, 0.19, 1.72, 0.11 };
	for (int i = 0; i < 26; i++)
	{
		fct = (double)f(i, string);
		suma += (fct / n) * (freq[i]/100);
	}
	return suma;
}

int lungimea_cheii(char* cript)
{
	double ic = 0;
	char s[MAX_LG] = "";
	int m = 1;
	for (int i = 1; i <= m; i++)
		{
			for (int j = 0; j <= strlen(cript); j = j + m)
				strncat(s, &cript[j], 1);
			
			ic = IC(s);
			//cout << ic << '\n';
			if (ic <= 0.055)
			{
				m++;
				i = 1;
				strcpy(s, "");
			}	
		}
		return m;
	}

void cheia(int m, char* cript, int K[MAX_LG])
{
	//char K[256]="";
	int s;
	double mic;
	char y_m[MAX_LG] = "";
	for (int j= 0; j < m; j++)
	{
		s = -1;
		for (int i = j; i < strlen(cript); i = i + m)
			strncat(y_m, &cript[i], 1);
		do
		{
			s++;
			shift(y_m, s);
			mic = MIC(y_m);
			cout << mic << " ";
		} while (mic > 0.055);
		cout << "s: " << s << '\n';
		K[j] =(26 - s) % 26;
		strcpy(y_m, "");
		cout << '\n';
		char x = K[j] + 'A';
		cout << "k: " << x << '\n';
	}
	//return K;
}


