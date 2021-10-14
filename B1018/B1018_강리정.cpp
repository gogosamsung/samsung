#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

// 0 WHITE 1 BLACK
char map[51][51] = { 0 };
int N, M;
int min = 99;
char W[9] = "WBWBWBWB";
char B[9] = "BWBWBWBW";


int main() {
	scanf("%d %d", &N, &M);
	char temp;
	for (int y = 0; y < N; y++) {
		scanf("%s", &map[y][0]);
	}

	// 8*8로 잘라내기
	for (int i = 0; i <= N - 8; i++) {
		for (int j = 0; j <= M - 8; j++) {
			// 다시 칠하기
			int cnt_w = 0, cnt_b = 0;
			for (int ii = i; ii < i + 8; ii++) {
				for (int jj = j; jj < j + 8; jj++) {
					// 홀수 행
					if ((ii - i) % 2 == 0) {
							if (W[jj-j] != map[ii][jj]) cnt_w++;
							if (B[jj-j] != map[ii][jj]) cnt_b++;
					}
					// 짝수 행
					else {
						if (W[jj-j] != map[ii][jj]) cnt_b++;
						if (B[jj-j] != map[ii][jj]) cnt_w++;
					}
				}
			}
			if (cnt_w < min) min = cnt_w;
			if (cnt_b < min) min = cnt_b;

		}
	}
		
	printf("%d", min);


	return 0;
}
