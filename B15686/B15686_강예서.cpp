#include <iostream>
#include <vector>
#include <climits>
#include <algorithm>
#include <cstdlib>
using namespace std;
int N, M, res = INT_MAX;
int arr[51][51];
vector<pair<int, int> > home;
vector<pair<int, int> > chicken, picked;

void combination(int idx) {
	if (picked.size() == M) {
		int sum = 0;
		for (int i = 0; i < home.size(); i++) {
			int tmp = INT_MAX;
			for (int j = 0; j < picked.size(); j++) {
				tmp = min(tmp, abs(home[i].first - picked[j].first) + abs(home[i].second - picked[j].second));
			}
			sum += tmp;
		}
		res = min(res, sum);
		return;
	}
	for (int i = idx; i < chicken.size(); i++) {
		picked.push_back({ chicken[i].first, chicken[i].second });
		combination(i + 1);
		picked.pop_back();
	}
}

int main() {
	cin >> N >> M;
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> arr[i][j];
			if (arr[i][j] == 1) home.push_back({ i,j });
			else if (arr[i][j] == 2) chicken.push_back({ i,j });
		}
	}
	combination(0);
	cout << res;
	return 0;
}