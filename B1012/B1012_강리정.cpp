#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N, M, K;
int map[50][50] = { 0 };
// 빈 땅 0, 배추 있는 땅 1, 탐색 완료 -1
int cnt = 0;
int di[4] = { -1, 1, 0, 0 };
int dj[4] = { 0, 0, -1, 1 };

void DFS(int i, int j) {
	map[i][j] = -1;
	int new_i, new_j;
	for (int dir = 0; dir < 4; dir++) {
		new_i = i + di[dir];
		new_j = j + dj[dir];
		if (new_i >= 0 && new_i < N && new_j >= 0 && new_j < M) {
			if(map[new_i][new_j] == 1)
				DFS(new_i, new_j);
		}
	}

}

int main() {
	int T;
	scanf("%d", &T);

	for (int tc = 1; tc <= T; tc++) {
		// input
		scanf("%d %d %d", &M, &N, &K);
		int x, y;
		for (int i = 0; i < K; i++) {
			scanf("%d %d", &x, &y);
			map[y][x] = 1;
		}

		// solve
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1) {
					DFS(i, j);
					cnt++;
				}
			}
		}

		// output
		printf("%d\n", cnt);

		// reset
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				map[i][j] = 0;
			}
		}
		cnt = 0;

	}

	return 0;
}
