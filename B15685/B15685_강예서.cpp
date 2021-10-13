#include <iostream>
#include <vector>
using namespace std;
class info {
public:
	int fx, fy, tx, ty, d;
	info(){}
	info(int fx, int fy, int tx, int ty, int d) { this->fx = fx; this->fy = fy; this->tx = tx; this->ty = ty; this->d = d; }
};
int N, X, Y, D, G, res;
int arr[101][101];
int dx[4] = { 0,-1,0,1 }, dy[4] = { 1,0,-1,0 };

void countSquare() {
	for (int i = 0; i < 100; i++) {
		for (int j = 0; j < 100; j++) {
			if (arr[i][j] && arr[i][j + 1] && arr[i + 1][j] && arr[i + 1][j + 1]) res++;
		}
	}
}

int main() {
	cin >> N;
	for (int i = 0; i < N; i++) {
		cin >> Y >> X >> D >> G;
		vector<info> dragon;
		dragon.push_back(info(X, Y, X + dx[D], Y + dy[D], D));
		arr[X][Y] = arr[X + dx[D]][Y + dy[D]] = 1; // µå·¡°ï Ä¿ºê ÁÂÇ¥ Ç¥½Ã
		for (int j = 1; j <= G; j++) {
			int dragonLen = dragon.size();
			for (int k = dragonLen - 1; k >= 0; k--) {
				int d = (dragon[k].d + 1) % 4;
				int fx = dragon.back().tx, fy = dragon.back().ty;
				dragon.push_back(info(fx, fy, fx + dx[d], fy + dy[d], d));
				arr[fx][fy] = arr[fx + dx[d]][fy + dy[d]] = 1; // µå·¡°ï Ä¿ºê ÁÂÇ¥ Ç¥½Ã
			}
		}
	}
	countSquare();
	cout << res;
	return 0;
}