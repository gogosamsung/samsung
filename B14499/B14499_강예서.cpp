#include <iostream>
#include <algorithm>
using namespace std;
int N, M, X, Y, K, d;
int map[21][21], num[7], row[4] = { 4,1,3,6 }, col[4] = { 2,1,5,6 };
int dx[5] = { 0,0,0,-1,1 }, dy[5] = { 0,1,-1,0,0 }; // ©Л,аб,╩С,го

void move(int a[]) { for (int i = 1; i < 4; i++) swap(a[0], a[i]);}

int main() {
	cin >> N >> M >> X >> Y >> K;
	for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) cin >> map[i][j];
	for (int i = 0; i < K; i++) {
		cin >> d;
		int x = dx[d] + X, y = dy[d] + Y;
		if (x < 0 || x >= N || y < 0 || y >= M) continue;
		X = x, Y = y;
		if (d <= 2) {
			move(row);
			if (d == 2)for (int j = 0; j < 2; j++) move(row);
			col[1] = row[1];
			col[3] = row[3];
		}
		else {
			move(col);
			if (d == 3)for (int j = 0; j < 2; j++) move(col);
			row[1] = col[1];
			row[3] = col[3];
		}
		if (map[X][Y] == 0) map[X][Y] = num[row[3]];
		else {
			num[row[3]] = map[X][Y];
			map[X][Y] = 0;
		}
		cout << num[row[1]] << "\n";
	}
	return 0;
}