#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
int N, K, res = -1;
int board[13][13];
int dx[5] = { 0,0,0,-1,1 }, dy[5] = { 0,1,-1,0,0 }, dd[5] = { 0,2,1,4,3 }; // �� �� �� ��
vector<pair<int, int> > status[13][13]; // ��ȣ, ����
vector<pair<pair<int, int>, int> > chess; // x, y, ����
int main() {
	cin >> N >> K;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) cin >> board[i][j];
	for (int i = 0; i < K; i++) {
		int x, y, d;
		cin >> x >> y >> d;
		status[x][y].push_back({ i, d });
		chess.push_back({ {x,y}, d });
	}
	for (int t = 1; t <= 1000; t++) {
		for (int i = 0; i < chess.size(); i++) {
			int X = chess[i].first.first, Y = chess[i].first.second, d = chess[i].second;
			int x = X + dx[d], y = Y + dy[d];
			if (x < 1 || x > N || y < 1 || y > N || board[x][y] == 2) { // ���� ����ų� �Ķ����̸� ���� �ٲ�
				d = dd[d];
				chess[i].second = d;
				for (int k = 0; k < status[X][Y].size(); k++) {
					if (status[X][Y][k].first == i) {
						status[X][Y][k].second = d;
						break;
					}
				}
			}
			x = X + dx[d], y = Y + dy[d];
			if (x < 1 || x > N || y < 1 || y > N || board[x][y] == 2) continue; // ���� �ٲ㵵 �Ķ����̰ų� ������ ����� �Ѿ
			vector<pair<int, int> > tmp;
			while (!status[X][Y].empty()) {
				tmp.push_back(status[X][Y].back());
				status[X][Y].pop_back();
				if (tmp.back().first == i) break;
			}
			if (status[x][y].size() + tmp.size() >= 4) {
				res = t;
				break;
			}
			if (board[x][y] == 1) reverse(tmp.begin(), tmp.end()); // �������̸� ���� ������
			while (!tmp.empty()) {
				status[x][y].push_back(tmp.back());
				chess[tmp.back().first] = { {x,y},tmp.back().second };
				tmp.pop_back();
			}
		}
		if (res != -1) break;
	}
	cout << res;
	return 0;
}
