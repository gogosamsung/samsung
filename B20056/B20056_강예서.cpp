#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
using namespace std;
class info {
public:
	int x, y, m, s, d; // x, y, 질량, 속력, 방향
	info(){}
	info(int x, int y, int m, int s, int d) { this->x = x; this->y = y; this->m = m; this->s = s; this->d = d; }
};
int N, M, K, res;
int dx[8] = { -1,-1,0,1,1,1,0,-1 }, dy[8] = { 0,1,1,1,0,-1,-1,-1 }, dd[2][4] = { {0,2,4,6},{1,3,5,7} };
vector <info> arr[51][51], tmp[51][51];
int main() {
	cin >> N >> M >> K;
	for (int i = 0; i < M; i++) {
		int x, y, m, s, d;
		cin >> x >> y >> m >> s >> d;
		x--, y--;
		arr[x][y].push_back({ x,y,m,s,d });
	}
	for (int t = 0; t < K; t++) {
		// 1. 파이어볼 이동
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!arr[i][j].size()) continue;
				while (!arr[i][j].empty()) {
					int d = arr[i][j].back().d;
					int x = i + dx[d] * arr[i][j].back().s, y = j + dy[d] * arr[i][j].back().s;
					x %= N, y %= N;
					x = (x + N) % N, y = (y + N) % N;
					tmp[x][y].push_back(arr[i][j].back());
					arr[i][j].pop_back();
				}
			}
		}
		// 2. 파이어볼 찐 이동 및 합치기
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tmp[i][j].empty()) continue;
				if (tmp[i][j].size() == 1) {
					arr[i][j].push_back(tmp[i][j].back());
					tmp[i][j].pop_back();
					continue;
				}
				int m = 0, s = 0, cnt = 0;
				for (int k = 0; k < tmp[i][j].size(); k++) {
					m += tmp[i][j][k].m, s += tmp[i][j][k].s;
					if (tmp[i][j][k].d % 2) cnt++;
					else cnt--;
				}
				m /= 5, s /= tmp[i][j].size();
				if (m == 0) {
					tmp[i][j].clear();
					continue;
				}
				cnt = abs(cnt) == tmp[i][j].size() ? 0 : 1;
				for (int k = 0; k < 4; k++) arr[i][j].push_back({ i,j,m,s,dd[cnt][k] });
				tmp[i][j].clear();
			}
		}
	}
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) for (int k = 0; k < arr[i][j].size(); k++) res += arr[i][j][k].m;
	cout << res;
	return 0;
}
