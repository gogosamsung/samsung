#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N, M, x, y, K;
int map[20][20];

int dx[4] = { 0,0,-1,1 };
int dy[4] = { 1,-1,0,0 };

int roll[5] = { 0,4,3,5,2 };
int dice[6] = { 0,0,0,0,0,0 };

int main() {
	// Input
	scanf("%d %d %d %d %d", &N, &M, &y, &x, &K);
	for (int r = 0; r < N; r++) {
		for (int c = 0; c < M; c++) {
			scanf("%d", &map[r][c]);
		}
	}

	// Roll
	int top = 1;
	int cmd;
	for (int k = 0; k < K; k++){
		scanf("%d", &cmd);

		int newX = x + dx[cmd - 1];
		int newY = y + dy[cmd - 1];

		// Border check
		if (newX < 0 || newX >= N || newY < 0 || newY >= M) continue;

		// Roll
		if (cmd == 1) {
			int temp = roll[1];
			roll[1] = 7 - top;
			roll[2] = top;
			top = temp;
		}
		else if (cmd == 2) {
			int temp = roll[2];
			roll[1] = top;
			roll[2] = 7 - top;
			top = temp;
		}
		else if (cmd == 3) {
			int temp = roll[3];
			roll[3] = 7 - top;
			roll[4] = top;
			top = temp;
		}
		else {
			int temp = roll[4];
			roll[3] = top;
			roll[4] = 7 - top;
			top = temp;
		}

		// Change number
		if (map[newX][newY] == 0) map[newX][newY] = 7 - top;
		else {
			dice[7 - top - 1] = map[newX][newY];
			map[newX][newY] = 0;
		}
		// Update x, y
		x = newX;
		y = newY;

		printf("%d\n", dice[top - 1]);
	}

	return 0;
}
