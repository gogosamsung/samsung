#include <iostream>
#include <algorithm>
#include <cmath>
#include <string>
using namespace std;
int K, num, dir, res;
int gear[4][8];

void rotate(int x) { for (int i = 1; i < 8; i++) swap(gear[x][0], gear[x][i]); }

void leftGear(int x, int left, int direction) {
	if (--x < 0 || gear[x][2] == left) return; // ¿ŞÂÊ Åé´Ï¹ÙÄû°¡ ¾ø°Å³ª °°Àº ±ØÀÌ¸é return
	left = gear[x][6], direction *= -1;
	rotate(x);
	if (direction == -1) for (int i = 0; i < 6; i++) rotate(x);
	leftGear(x, left, direction);
}

void rightGear(int x, int right, int direction) {
	if (++x > 3 || gear[x][6] == right) return; // ¿À¸¥ÂÊ Åé´Ï¹ÙÄû°¡ ¾ø°Å³ª °°Àº ±ØÀÌ¸é return
	right = gear[x][2], direction *= -1;
	rotate(x);
	if (direction == -1) for (int i = 0; i < 6; i++) rotate(x);
	rightGear(x, right, direction);
}

int main() {
	for (int i = 0; i < 4; i++) {
		string a;
		cin >> a;
		for (int j = 0; j < 8; j++) gear[i][j] = a[j] - '0';
	}
	cin >> K;
	for (int i = 0; i < K; i++) {
		cin >> num >> dir;
		num--;
		leftGear(num, gear[num][6], dir);
		rightGear(num, gear[num][2], dir);
		rotate(num);
		if (dir == -1) for (int j = 0; j < 6; j++) rotate(num);
	}
	for (int i = 0; i < 4; i++) res += gear[i][0] * pow(2, i);
	cout << res;
	return 0;
}