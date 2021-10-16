#include <iostream>
#include <queue>
#include <algorithm>
using namespace std;
class info {
public:
	int x, y, val;
	info(){}
	info(int x, int y, int val) { this->x = x; this->y = y; this->val = val; }
};
int R, C, T, X = -1, Y = -1, res;
int arr[51][51], tmp[300];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
queue<info> diffused;

void clean(int idx) { // 공기 청정기 돌리기
	for (int i = idx; i >= 0; i--) tmp[i + 1] = tmp[i];
	tmp[0] = 0;
}

int main() {
	cin >> R >> C >> T;
	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			cin >> arr[i][j];
			if (arr[i][j] == -1 && X == -1) X = i, Y = j; // 공기 청정기 위쪽 위치
		}
	}
	for (int t = 1; t <= T; t++) {
		for (int i = 0; i < R; i++) { // 확산할 미세먼지 찾기
			for (int j = 0; j < C; j++) {
				if (arr[i][j] == 0 || arr[i][j] == -1) continue;
				int cnt = 0;
				for (int k = 0; k < 4; k++) {
					int x = i + dx[k], y = j + dy[k];
					if (x < 0 || x >= R || y < 0 || y >= C || arr[x][y] == -1) continue;
					diffused.push({ x,y,arr[i][j] / 5 });
					cnt++;
				}
				arr[i][j] = arr[i][j] - (arr[i][j] / 5) * cnt;
			}
		}
		while (!diffused.empty()) { // 미세먼지 확산시키기
			arr[diffused.front().x][diffused.front().y] += diffused.front().val;
			diffused.pop();
		}
		// 공기 청정기 위쪽 돌리기
		int idx = -1;
		for (int i = Y + 1; i < C; i++) tmp[++idx] = arr[X][i];
		for (int i = X - 1; i >= 0; i--) tmp[++idx] = arr[i][C - 1];
		for (int i = C - 2; i >= 0; i--) tmp[++idx] = arr[0][i];
		for (int i = 1; i < X; i++) tmp[++idx] = arr[i][0];
		clean(idx), idx = -1;
		for (int i = Y + 1; i < C; i++) arr[X][i] = tmp[++idx];
		for (int i = X - 1; i >= 0; i--) arr[i][C - 1] = tmp[++idx];
		for (int i = C - 2; i >= 0; i--) arr[0][i] = tmp[++idx];
		for (int i = 1; i < X; i++) arr[i][0] = tmp[++idx];

		// 공기 청정기 아래쪽 돌리기
		idx = -1, X++;
		for (int i = Y + 1; i < C; i++) tmp[++idx] = arr[X][i];
		for (int i = X + 1; i < R; i++) tmp[++idx] = arr[i][C - 1];
		for (int i = C - 2; i >= 0; i--) tmp[++idx] = arr[R - 1][i];
		for (int i = R - 2; i > X; i--) tmp[++idx] = arr[i][0];
		clean(idx), idx = -1;
		for (int i = Y + 1; i < C; i++) arr[X][i] = tmp[++idx];
		for (int i = X + 1; i < R; i++) arr[i][C - 1] = tmp[++idx];
		for (int i = C - 2; i >= 0; i--) arr[R - 1][i] = tmp[++idx];
		for (int i = R - 2; i > X; i--) arr[i][0] = tmp[++idx];
		X--;
	}
	for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) res += arr[i][j];
	cout << res + 2;
	return 0;
}
