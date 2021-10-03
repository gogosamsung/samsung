#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N, M, r, c, d;
int map[50][50];
int sum = 0;

int left_r[4] = { 0,-1,0,1 };
int left_c[4] = { -1,0,1,0 };
int back_r[4] = { 1,0,-1,0 };
int back_c[4] = { 0,-1,0,1 };

void turn() {
	if (d == 0) d = 3;
	else d--;
}

int main() {
	// Input
	scanf("%d %d", &N, &M);
	scanf("%d %d %d", &r, &c, &d);

	for (int y = 0; y < N; y++) {
		for (int x = 0; x < M; x++) {
			scanf("%d", &map[y][x]);
		}
	}

	while (1) {
		// clean!
		if (map[r][c] != -1) {
			map[r][c] = -1;
			sum++;
		}
		
		// search
		int cnt = 0;
		while (cnt != 4) {
			// 왼쪽 탐색
			int new_r = r + left_r[d];
			int new_c = c + left_c[d];
			// 청소 안되어있으면
			if (map[new_r][new_c] == 0) {
				turn();
				r = new_r;
				c = new_c;
				break;
			}
			// 갈 수 없으면 회전만
			else {
				turn();
			}
			cnt++;
		}
		if (cnt == 4) {
			int new_r = r + back_r[d];
			int new_c = c + back_c[d];
			// 후진 불가
			if (map[new_r][new_c] == 1)
				break;
			else {
				r = new_r;
				c = new_c;
			}
		}
	}

	printf("%d", sum);

	return 0;
}
