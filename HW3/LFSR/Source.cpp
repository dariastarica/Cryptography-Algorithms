#include <cmath>
#include <iostream>
#include <chrono>
#include <cmath>
using namespace std;

struct state {
    int S1[17];
    int S2[25];
    int c;
}S;

void LFSR1(int stream[17], int output) {
    output = stream[0];
    stream++;
    stream[16] = stream[0] ^ stream[14];
}

void LFSR2(int stream[25], int output) {
    output = stream[0];
    stream++;
    stream[24] = stream[0] ^ stream[3] ^ stream[4] ^ stream[12];
}

int convertArrayToNumber(int stream[]) {
    int result = 0;
    for (int i = 0; i < 8; i++)
        if (stream[i] == 1)
            result += pow(2, i);
    return result;
}

void init(int K[40])
{
    int K1[17], K2[25];
    for (int i = 0; i < 16; i++)
        K1[i] = K[i];
    int j = 0;
    for (int i = 16; i < 24; i++)
        K2[j++] = K[i];

    for (int i = 15; i >= 8; i--)
        K1[i + 1] = K1[i];
    K1[8] = 1;
    for (int i = 23; i >= 21; i--)
        K2[i + 1] = K2[i];
    K2[21] = 1;

    int output = 0;
    for (int i = 0; i < 8; i++)
        LFSR1(K1, output);
    for (int i = 0; i < 17; i++)
        S.S1[i] = K1[i];
    for (int i = 0; i < 16; i++)
        LFSR2(K2, output);
    for (int i = 0; i < 25; i++)
        S.S2[i] = K2[i];
    S.c = 0;
}

int trans() {
    int x[8], y[8];
    int output = 0;
    for (int i = 0; i < 8; i++)
    {
        LFSR1(S.S1, output);
        x[i] = output;
        LFSR1(S.S2, output);
        y[i] = output;
    }
    if (convertArrayToNumber(x) + convertArrayToNumber(y) > 255)
        S.c = 1;
    else S.c = 0;
    return (convertArrayToNumber(x) + convertArrayToNumber(y) + S.c) % 256;
}


int main() {
    auto start = chrono::high_resolution_clock::now();
    int key[40] = { 1,0,1,0,1,1,1 };
    init(key);
    for (int i = 0; i < 5; i++)
        cout << trans();
    cout << '\n';
    cout << "Algorithm ran for ";
    auto finish = chrono::high_resolution_clock::now();
    std::cout << (chrono::duration_cast<chrono::nanoseconds>(finish - start).count()) / pow(10, 9) << " s\n";
}