#include <iostream>
using namespace std;
int N, res;
int arr[500][500];
int dx[4] = { 0,1,0,-1 }, dy[4] = { -1,0,1,0 };
int tx[2][10] = { {0,-1,1,-2,-1,1,2,-1,1,0},
				  {0,-1,0,1,2,-1,0,1,0,1}},
	ty[2][10] = { { -2,-1,-1,0,0,0,0,1,1,-1 },
				  {-2,-1,-1,-1,0,1,1,1,2,0}},
	percent[2][10] = { {5,10,10,2,7,7,2,1,1},
					   {2,1,7,10,5,1,7,10,2} };
int main() {
	cin >> N;
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	int d = 0, xx = N / 2, xy = N / 2, chk = 0;
	for (int m = 1; m <= N; m++) {
		for (int n = 0; n < 2; n++) {
			for (int k = 0; k < m; k++) {
				int tmp = arr[xx][xy];
				arr[xx][xy] = 0;
				xx += dx[d], xy += dy[d];
				int val = arr[xx][xy], x, y, sum = 0;
				for (int i = 0; i < 10; i++) {
					x = xx + tx[d % 2][i], y = xy + ty[d % 2][i];
					if (d == 2) y = xy - ty[d % 2][i];
					else if (d == 3) x = xx - tx[d % 2][i];
					int val2 = val * percent[d % 2][i] / 100;
					if (i == 9) val2 = val - sum;
					if (x < 0 || x >= N || y < 0 || y >= N) res += val2;
					else arr[x][y] += val2;
					sum += val2;
				}
				arr[xx][xy] = tmp;
				if (!xx && !xy) {
					chk = 1;
					break;
				}
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
	cout << res;
	return 0;
}
