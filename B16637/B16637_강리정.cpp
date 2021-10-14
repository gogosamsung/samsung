#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

char str[20];
int N;
int max = -2147483647;

int cal(int a, int b, char op) {
	switch (op) {
	case '+':
		return a + b;
	case '-':
		return a - b;
	case'*':
		return a * b;
	}
}

void DFS(int index, int result) {
	// 종료조건
	if (index > N - 1) {
		if (max < result) max = result;
		return;
	}
	char op = (index == 0) ? '+' : str[index - 1];

	// 괄호를 친다
	if (index < N - 2) {
		int temp = cal(str[index] - '0', str[index + 2] - '0', str[index + 1]);
		DFS(index + 4, cal(result, temp, op));
	}

	// 괄호를 안친다
	DFS(index + 2, cal(result, str[index] - '0', op));
}


int main() {
	scanf("%d", &N);
	scanf("%s", str);

	DFS(0, 0);

	printf("%d", max);

	return 0;
}
