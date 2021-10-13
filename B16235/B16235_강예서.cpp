#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <cstring>
using namespace std;

class info {
public:
	int x, y, z;
	info(){}
	info(int x, int y, int z) { this->x = x; this->y = y; this->z = z; }
};

int N, M, K, X, Y, Z, res;
int originearth[12][12], earth[12][12];
int dx[8] = { -1,-1,-1,0,0,1,1,1 }, dy[8] = { -1,0,1,-1,1,-1,0,1 };
vector<int> tree[12][12];
queue<info> dead, born;

bool compare(info a, info b) {
	if (a.x == b.x) {
		if (a.y == b.y) return a.z < b.z; // 위치가 같으면 나이가 작은 순서
		return a.y < b.y;
	}
	return a.x < b.x;
}

void spring() { // 1. 봄
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			sort(tree[i][j].begin(), tree[i][j].end());
			for (int k = 0; k < tree[i][j].size(); k++) {
				if (earth[i][j] < tree[i][j][k]) { // 양분 못 먹으면 나무 죽음
					int len = tree[i][j].size();
					for (int m = k; m < len; m++) {
						dead.push({ i,j,tree[i][j][m] });
						tree[i][j].pop_back();
					}
					break;
				}
				else { // 아니면 양분 먹음
					earth[i][j] -= tree[i][j][k];
					tree[i][j][k]++;
				}
			}
		}
	}
}

void summer() { // 2. 여름
	while (!dead.empty()) {
		earth[dead.front().x][dead.front().y] += dead.front().z / 2;
		dead.pop();
	}
}

void autumn() { // 3. 가을
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			for (int k = 0; k < tree[i][j].size(); k++) {
				if (tree[i][j][k] % 5 == 0) {
					for (int m = 0; m < 8; m++) {
						int x = i + dx[m], y = j + dy[m];
						if (x < 1 || x > N || y < 1 || y > N) continue;
						born.push({ x,y,1 });
					}
				}
			}
		}
	}
	while (!born.empty()) {
		tree[born.front().x][born.front().y].push_back(born.front().z);
		born.pop();
	}
}

void winter() {
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) earth[i][j] += originearth[i][j];
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cin >> N >> M >> K;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) cin >> originearth[i][j];
	for (int i = 0; i < M; i++) {
		cin >> X >> Y >> Z;
		tree[X][Y].push_back(Z);
	}
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) earth[i][j] = 5;
	for (int i = 0; i < K; i++) {
		spring();
		summer();
		autumn();
		winter();
	}
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) res += tree[i][j].size();
	cout << res;
	return 0;
}
