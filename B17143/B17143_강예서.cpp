#include <iostream>
#include <queue>
#include <cstdlib>
using namespace std;
class info {
public:
	int x, y, s, d, z;
	info() {}
	info(int x, int y, int s, int d, int z) { // x, y, 속력, 방향, 크기
		this->x = x; this->y = y; this->s = s; this->d = d; this->z = z;
	}
};
int R, C, M, res;
int dx[5] = { 0,-1,1,0,0 }, dy[5] = { 0,0,0,1,-1 }, rot[5] = { 0,2,1,4,3 };
info arr[101][101];
queue<info> moved;
int main() {
	cin >> R >> C >> M;
	for (int i = 0; i < M; i++) {
		int r, c, s, d, z;
		cin >> r >> c >> s >> d >> z;
		arr[r][c] = { r,c,s,d,z };
	}
	for (int i = 1; i <= C; i++) {
		for (int j = 1; j <= R; j++) { // 제일 가까운 상어 잡음
			if (!arr[j][i].z) continue;
			res += arr[j][i].z;
			arr[j][i].z = 0;
			break;
		}
		for (int m = 1; m <= R; m++) {
			for (int n = 1; n <= C; n++) {
				if (!arr[m][n].z) continue;
				int x = m, y = n, s = arr[m][n].s, val;
				x += dx[arr[m][n].d] * s, y += dy[arr[m][n].d] * s;
				if (x<1 || x>R || y<1 || y>C) { // 범위를 벗어난다면
					if (x < 1) {
						x = 1, s -= m - 1, val = R - 1;
						if ((s / val) % 2) {
							if (s % val) x = R - (s % val);
							else x = R, arr[m][n].d = rot[arr[m][n].d];
						}
						else {
							if (s % val) x += (s % val), arr[m][n].d = rot[arr[m][n].d];
						}
					}
					else if (x > R) {
						x = R, s -= R - m, val = R - 1;
						if ((s / val) % 2) {
							if (s % val) x = 1 + (s % val);
							else x = 1, arr[m][n].d = rot[arr[m][n].d];
						}
						else {
							if (s % val) x -= (s % val), arr[m][n].d = rot[arr[m][n].d];
						}
					}
					else if (y < 1) {
						y = 1, s -= n - 1, val = C - 1;
						if ((s / val) % 2) {
							if (s % val) y = C - (s % val);
							else y = C, arr[m][n].d = rot[arr[m][n].d];
						}
						else {
							if (s % val) y += (s % val), arr[m][n].d = rot[arr[m][n].d];
						}
					}
					else {
						y = C, s -= C - n, val = C - 1;
						if ((s / val) % 2) {
							if (s % val) y = 1 + (s % val);
							else y = 1, arr[m][n].d = rot[arr[m][n].d];
						}
						else {
							if (s % val) y -= (s % val), arr[m][n].d = rot[arr[m][n].d];
						}
					}
				}
				moved.push({ x,y,arr[m][n].s,arr[m][n].d,arr[m][n].z }); // 이동 상어 위치 저장
				arr[m][n] = { m,n,0,0,0 };
			}
		}
		while (!moved.empty()) {
			info tmp = moved.front();
			if (arr[tmp.x][tmp.y].z < tmp.z) { // 더 큰 상어만 살아남음
				arr[tmp.x][tmp.y] = tmp;
			}
			moved.pop();
		}
	}
	cout << res;
	return 0;
}
