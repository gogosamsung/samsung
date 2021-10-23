#include <iostream>
#include <queue>
#include <cstring>
using namespace std;
class info {
public:
	int x, y;
	info() {}
	info(int x, int y) { this->x = x; this->y = y; }
};
int N, M, D, S, res;
int arr[51][51];
int dx[9] = { 0,0,-1,-1,-1,0,1,1,1 }, dy[9] = { 0,-1,-1,0,1,1,1,0,-1 };
queue<info> cloud, newcloud;
int main() {
	cin >> N >> M;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) cin >> arr[i][j];
	bool wasCloud[51][51] = { 0 };
	cloud.push({ N,1 });
	cloud.push({ N,2 });
	cloud.push({ N - 1,1 });
	cloud.push({ N - 1,2 });
	for (int m = 0; m < M; m++) {
		cin >> D >> S;
		while (!cloud.empty()) {
			int x = (cloud.front().x + dx[D] * (S % N)) % N, y = (cloud.front().y + dy[D] * (S % N)) % N;
			if (x <= 0) x = N + x;
			if (y <= 0) y = N + y;
			arr[x][y]++;
			newcloud.push({ x,y });
			cloud.pop();
		}
		while (!newcloud.empty()) {
			int x = newcloud.front().x, y = newcloud.front().y, cnt = 0;
			for (int k = 2; k <= 8; k += 2) {
				int xx = x + dx[k], yy = y + dy[k];
				if (xx < 1 || xx > N || yy < 1 || yy > N || !arr[xx][yy]) continue;
				cnt++;
			}
			arr[x][y] += cnt;
			wasCloud[x][y] = true;
			newcloud.pop();
		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (wasCloud[i][j]) {
					wasCloud[i][j] = false;
					continue;
				}
				if (arr[i][j] < 2) continue;
				arr[i][j] -= 2;
				cloud.push({ i,j });
			}
		}
	}
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) res += arr[i][j];
	cout << res;
	return 0;
}
