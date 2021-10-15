#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N, r, c;
int cnt = 0;
int flag = 0;

void divide(int size, int ii, int jj) {
	// 발견했으면 종료
	if (flag == 1) return;

	// 종료조건
	if (size <= 1) {
		for (int i = ii; i < ii + 2; i++) {
			for (int j = jj; j < jj + 2; j++) {
				if (i == r && j == c) {
					flag = 1;
					break;
				}
				cnt++;
			}
			if (flag == 1) break;
		}
		return;
	}

	// 진행
	int temp = 1;
	for (int i = 0; i < size - 1; i++) {
		temp *= 2;
	}
	if (r < ii + temp && c < jj + temp) {
		divide(size - 1, ii, jj);
	}
	else if (r < ii + temp && c >= jj + temp) {
		cnt += temp*temp;
		divide(size - 1, ii, jj+temp);
	}
	else if (r >= ii + temp && c < jj + temp) {
		cnt += temp*temp * 2;
		divide(size - 1, ii+temp, jj);
	}
	else {
		cnt += temp*temp * 3;
		divide(size - 1, ii+temp, jj+temp);
	}

}

int main() {
	scanf("%d %d %d", &N, &r, &c);

	divide(N, 0, 0);

	printf("%d", cnt);

	return 0;
}
