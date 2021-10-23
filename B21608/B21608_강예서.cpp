#include <iostream>
#include <queue>
#include <cmath>
#include <vector>
using namespace std;
class info {
public:
	int x, y, cnt, emptycnt;
	info() {}
	info(int x, int y, int cnt, int emptycnt) { this->x = x; this->y = y; this->cnt = cnt; this->emptycnt = emptycnt; }
	bool operator()(info a, info b) {
		if (a.cnt == b.cnt) {
			if (a.emptycnt == b.emptycnt) {
				if (a.x == b.x) return a.y > b.y;
				return a.x > b.x;
			}
			return a.emptycnt < b.emptycnt;
		}
		return a.cnt < b.cnt;
	}
};
int N, num, res;
int arr[21][21], like[4], student[500][4];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
int main() {
	cin >> N;
	for (int n = 0; n < N * N; n++) {
		cin >> num >> like[0] >> like[1] >> like[2] >> like[3];
		priority_queue<info,vector<info>,info> pq;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j]) continue;
				int cnt = 0, emptycnt = 0;
				for (int k = 0; k < 4; k++) {
					int x = i + dx[k], y = j + dy[k];
					if (x < 0 || x >= N || y < 0 | y >= N) continue;
					if (!arr[x][y]) emptycnt++;
					for (int m = 0; m < 4; m++) {
						if (arr[x][y] == like[m]) {
							cnt++;
							break;
						}
					}
				}
				pq.push({ i,j,cnt,emptycnt });
			}
		}
		arr[pq.top().x][pq.top().y] = num;
		for (int i = 0; i < 4; i++) student[num][i] = like[i];
	}

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			int cnt = 0;
			for (int k = 0; k < 4; k++) {
				int x = i + dx[k], y = j + dy[k];
				if (x < 0 || x >= N || y < 0 | y >= N) continue;
				for (int m = 0; m < 4; m++) {
					if (arr[x][y] == student[arr[i][j]][m]) {
						cnt++;
						break;
					}
				}
			}
			res += pow(10, cnt - 1);
		}
	}

	cout << res;
	return 0;
}
