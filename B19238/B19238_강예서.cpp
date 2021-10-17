#include <iostream>
#include <vector>
#include <queue>
#include <climits>
using namespace std;
class info {
public:
	int num, fx, fy, tx, ty;
	info() {}
	info(int num, int fx, int fy, int tx, int ty) { this->num = num; this->fx = fx; this->fy = fy; this->tx = tx; this->ty = ty; }
};
class compare {
public:
	bool operator()(pair<pair<int, int>, int> a, pair<pair<int, int>, int> b) { // 거리,행,열 순으로 오름차순 정렬
		if (a.second == b.second) {
			if (a.first.first == b.first.first) return a.first.second > b.first.second;
			return a.first.first > b.first.first;
		}
		return a.second > b.second;
	}
};
int N, M, res, X, Y, dist = INT_MAX, who = 0;
int arr[21][21];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
vector <info> customer;

void findCustomer() { // 태우러 갈 승객 찾음
	queue < pair<pair<int, int>, int> > q;
	priority_queue <pair<pair<int, int>, int>, vector <pair<pair<int, int>, int> >, compare> candidate;
	bool visited[21][21] = { 0 };
	if (arr[X][Y] > 0) candidate.push({ {X,Y},0 });
	else  q.push({ {X,Y},0 }), visited[X][Y] = true;
	while (!q.empty()) {
		if ((!candidate.empty() && q.front().second > candidate.top().second) || dist < q.front().second) break;
		if (res < q.front().second) { // 연료 범위 벗어나면 종료
			dist = INT_MAX;
			break;
		}
		for (int i = 0; i < 4; i++) {
			int x = q.front().first.first + dx[i], y = q.front().first.second + dy[i];
			if (x < 1 || x > N || y < 1 || y > N || visited[x][y] || arr[x][y] == -1) continue;
			if (arr[x][y]) candidate.push({ {x,y},q.front().second + 1 });
			q.push({ {x,y},q.front().second + 1 });
			visited[x][y] = true;
		}
		q.pop();
	}
	if (candidate.empty() || candidate.top().second > dist) return;
	who = arr[candidate.top().first.first][candidate.top().first.second];
	dist = candidate.top().second;
}

int dropCustomer() { // 목적지로 승객 태워줌
	queue < pair<pair<int, int>, int> > q;
	bool visited[21][21] = { 0 };
	q.push({ {X,Y},0 }), visited[X][Y] = true;
	while (!q.empty()) {
		if (res < q.front().second) return -1;
		for (int i = 0; i < 4; i++) {
			int x = q.front().first.first + dx[i], y = q.front().first.second + dy[i];
			if (x < 1 || x > N || y < 1 || y > N || visited[x][y] || arr[x][y] == -1) continue;
			if (x == customer[who].tx && y == customer[who].ty) return q.front().second + 1;
			q.push({ {x,y},q.front().second + 1 });
			visited[x][y] = true;
		}
		q.pop();
	}
	return -1;
}

int main() {
	cin >> N >> M >> res;
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			int input;
			cin >> input;
			arr[i][j] = input * -1; // 벽은 -1로 표기
		}
	}
	cin >> X >> Y;
	customer.push_back({ -1,-1,-1,-1,-1 });
	for (int i = 1; i <= M; i++) { // 승객 번호 1부터 시작
		int fx, fy, tx, ty;
		cin >> fx >> fy >> tx >> ty;
		customer.push_back({ i,fx,fy,tx,ty });
		arr[fx][fy] = i;
	}
	for (int t = 0; t < M; t++) {
		findCustomer();
		X = customer[who].fx, Y = customer[who].fy;
		if (X != -1) arr[X][Y] = 0;
		res -= dist;
		if (res < 0) { // 태우러 가는 새 연료 부족으로 실패
			res = -1;
			break;
		}
		int val = dropCustomer();
		if (val == -1 || res - val < 0) {
			res = -1;
			break;
		}
		res += val;
		dist = INT_MAX;
		X = customer[who].tx, Y = customer[who].ty;
	}
	cout << res;
	return 0;
}
