#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstring>
#include <chrono>
#include <cmath>

using namespace std;

int S[256];
int i;
int j;
int keystream[256];

void init(char key[]) {
    int keylength = strlen(key);

    for (int i = 0; i < 256; i++) {
        S[i] = i;
    }
    for (int i = 0; i <= 255; i++) {
        j = (j + S[i] + (int)key[i % keylength]) % 256;
        int aux = S[i];
        S[i] = S[j];
        S[j] = aux;
    }
    i = 0;
    j = 0;
}

int output() {
    i = (i + 1) % 256;
    j = (j + S[i]) % 256;

    int aux = S[i];
    S[i] = S[j];
    S[j] = aux;

    return S[(S[i] + S[j]) % 256];
}
void generateKeyStream(char key[],int size) {
    init(key);
    for(int i=0;i<size;i++)
    keystream[i] = output();
}

void cript(char plaintext[]) {
    for (int i = 0; i < strlen(plaintext); i++) {
        cout<<((char)(plaintext[i % 256] ^ keystream[i]));
    }
}

void decript(char cripttext[]) {
    for (int i = 0; i < strlen(cripttext); i++) {
        cout << ((char)(cripttext[i % 256] ^ keystream[i]));
    }
}

int main() {
    auto start = std::chrono::high_resolution_clock::now();

    char cheie[] = "pwd12";
    char plaintext[256]="Criptext de test";
    generateKeyStream(cheie, 128);
    cout << "Key stream" << '\n';
    for (int i = 0; i < 128; i++)
        cout << keystream[i]<<" ";
    cout << '\n';
    cout << "Algorithm ran for ";
    auto finish =chrono::high_resolution_clock::now();
    std::cout << (chrono::duration_cast<chrono::nanoseconds>(finish - start).count())/pow(10,9) << " s\n";
    cout << "Cripttext:" << '\n';
    cript(plaintext);
   
}
