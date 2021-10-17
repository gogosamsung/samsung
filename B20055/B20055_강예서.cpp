#include <iostream>
#include <algorithm>
using namespace std;
int N, K, res;
int belt[205], robot[105];

bool check() {
	int cnt = 0;
	for (int i = 1; i <= 2 * N; i++) if (!belt[i]) cnt++;
	if (cnt >= K) return true;
	return false;
}

int main() {
	cin >> N >> K;
	for (int i = 1; i <= 2 * N; i++) cin >> belt[i];
	while (++res) {
		// 1. 벨트 한 칸 회전
		for (int i = 1; i < N * 2; i++) swap(belt[N * 2], belt[i]);
		for (int i = N; i >= 1; i--) swap(robot[i + 1], robot[i]);
		if (robot[N] > 0) robot[N]--;
		// 2. 로봇 이동
		for (int i = N - 1; i >= 1; i--) {
			if (!robot[i] || robot[i + 1] > 0 || belt[i + 1] < 1) continue;
			robot[i + 1] = robot[i];
			robot[i] = 0;
			belt[i + 1]--;
		}
		if (robot[N] > 0) robot[N]--;
		// 3. 로봇 올리기
		if (!robot[1] && belt[1]) robot[1]++, belt[1]--;
		// 4. 내구도가 0인 칸의 개수가 K개 이상이라면 종료
		if (check()) break;
	}
	cout << res;
	return 0;
}
