#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N;
char RGB[100][100];
int map1[100][100] = { 0 };
int map2[100][100] = { 0 };
int cnt1 = 0, cnt2 = 0;

int dx[4] = { -1, 1, 0, 0 };
int dy[4] = { 0, 0, -1, 1 };

void dfs1(int x, int y, int cnt) {
	map1[y][x] = cnt;

	for (int dir = 0; dir < 4; dir++) {
		int newX = x + dx[dir];
		int newY = y + dy[dir];

		if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
			if (map1[newY][newX] == 0 && RGB[y][x] == RGB[newY][newX]) {
				dfs1(newX, newY, cnt);
			}
		}
	}

}

void dfs2(int x, int y, int cnt) {
	map2[y][x] = cnt;

	for (int dir = 0; dir < 4; dir++) {
		int newX = x + dx[dir];
		int newY = y + dy[dir];

		if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
			if (map2[newY][newX] == 0){
				if (RGB[y][x] == RGB[newY][newX]) {
					dfs2(newX, newY, cnt);
				}
				else if (RGB[y][x] == 'R' && RGB[newY][newX] == 'G') {
					dfs2(newX, newY, cnt);
				}
				else if (RGB[y][x] == 'G' && RGB[newY][newX] == 'R') {
					dfs2(newX, newY, cnt);
				}
			}
		}
	}

}

int main() {
	scanf("%d", &N);
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			scanf(" %c", &RGB[i][j]);
		}
	}

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			if (map1[i][j] == 0) {
				cnt1++;
				dfs1(j, i, cnt1);
			}
			if (map2[i][j] == 0) {
				cnt2++;
				dfs2(j, i, cnt2);
			}
		}
	}

	printf("%d %d", cnt1, cnt2);

	return 0;
}
