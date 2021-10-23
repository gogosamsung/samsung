#include <iostream>
#include <queue>
#include <stack>
#include <vector>
#include <cmath>
#include <algorithm>
using namespace std;
int N, M, res;
int arr[21][21];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
class bigTeamInfo {
public:
	int teamSize, rainbowcnt, x, y, teamNum;
	bigTeamInfo(){}
	bigTeamInfo(int teamSize, int rainbowcnt, int x, int y, int teamNum) { this->teamSize = teamSize; this->rainbowcnt = rainbowcnt; this->x = x; this->y = y; this->teamNum = teamNum; }
	bool operator()(bigTeamInfo a, bigTeamInfo b) {
		if (a.teamSize == b.teamSize) {
			if (a.rainbowcnt == b.rainbowcnt) {
				if (a.x == b.x) return a.y < b.y;
				return a.x < b.x;
			}
			return a.rainbowcnt < b.rainbowcnt;
		}
		return a.teamSize < b.teamSize;
	}
};
class standardpq {
public:
	standardpq() {}
	bool operator()(pair<int, int> a, pair<int, int> b){
		if (a.first == b.first) return a.second > b.second;
		return a.first > b.first;
	}
};
priority_queue<bigTeamInfo, vector<bigTeamInfo>, bigTeamInfo> bigTeam;
vector<pair<int, int> > team[500];
void gravity() {
	for (int j = 0; j < N; j++) {
		stack<int> st;
		int idx = 0;
		for (int i = 0; i < N; i++) {
			if (arr[i][j] == -1) {
				for (int m = i - 1; m >= idx; m--) {
					if (st.empty()) arr[m][j] = -2;
					else {
						arr[m][j] = st.top();
						st.pop();
					}
				}
				idx = i + 1;
				continue;
			}
			if (arr[i][j] == -2) continue;
			st.push(arr[i][j]);
		}
		for (int i = N - 1; i >= idx; i--) {
			if (st.empty()) arr[i][j] = -2;
			else {
				arr[i][j] = st.top();
				st.pop();
			}
		}
	}
}
int main() {
	cin >> N >> M;
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	while (1) {
		// 1. 연결된 블록 그룹 중 가장 큰 그룹 찾기
		int teamNum = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j] <= 0) continue;
				bool visited[21][21] = { 0 };
				queue<pair<int, int>> q, teamq;
				q.push({ i,j }), teamq.push({ i,j }), visited[i][j] = true;
				while (!q.empty()) {
					for (int k = 0; k < 4; k++) {
						int x = q.front().first + dx[k], y = q.front().second +dy[k];
						if (x < 0 || x >= N || y < 0 || y >= N || visited[x][y] || arr[x][y] <= -1) continue;
						if (arr[x][y] > 0 && arr[i][j] != arr[x][y]) continue;
						visited[x][y] = true;
						q.push({ x,y }), teamq.push({ x,y });
					}
					q.pop();
				}
				if (teamq.size() == 1) visited[i][j] = false;
				else {
					int teamSize = teamq.size(), rainbowcnt = 0;
					priority_queue<pair<int,int>,vector<pair<int,int> >,standardpq>  standard;
					team[teamNum].clear();
					while (!teamq.empty()) {
						int x = teamq.front().first, y = teamq.front().second;
						if (arr[x][y] == 0) rainbowcnt++;
						else standard.push({ x,y });
						team[teamNum].push_back({ x,y });
						teamq.pop();
					}
					bigTeam.push({ teamSize,rainbowcnt,standard.top().first,standard.top().second, teamNum });
					teamNum++;
				}
			}
		}
		if (teamNum == 1) break; // 블록 그룹이 없으면 종료
		
		// 2. 가장 큰 블록 그룹의 모든 블록 제거 및 점수 얻기
		res += pow(bigTeam.top().teamSize, 2);
		teamNum = bigTeam.top().teamNum;
		for (int i = 0; i < team[teamNum].size(); i++) arr[team[teamNum][i].first][team[teamNum][i].second] = -2;
		while (!bigTeam.empty()) bigTeam.pop();

		// 3. 중력 작용
		gravity();

		// 4. 반시계 방향으로 90도 회전
		int tmp[21][21] = { 0 };
		for (int i = 0; i < N; i++) {
			queue<int> q;
			for (int j = N - 1; j >= 0; j--) q.push(arr[i][j]);
			int idx = 0;
			while (!q.empty()) {
				tmp[idx++][i] = q.front();
				q.pop();
			}
		}
		copy(&tmp[0][0], &tmp[0][0] + 21 * 21, &arr[0][0]);

		// 5. 중력 작용
		gravity();
	}
	cout << res;
	return 0;
}
