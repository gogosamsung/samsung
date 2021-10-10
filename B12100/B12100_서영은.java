package algo211006_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
시작 : 10:09 6:07 - 7:00 끝

네 방향 중 하나로 이동
같은 값을 갖는 두 블럭이 충돌하면 하나로 합쳐진다.
한 번의 이동에서 이미 합쳐진 블록은 또 다른 불록과 다시 합쳐질 수 없다.
똑같은 수가 세 개가 있는 경우, 이동하려고 하는 쪽의 칸이 먼저 합쳐진다.

NxN 크기의 보드

보드의 크기, 보드판의 블록 상태
구하는 것: 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값 구하기

1<=N<=20
0은 빈칸
2<= <=1024(2^10)
 */
public class BOJ_Gold2_12100_2048EASY {

	static int N, map[][], curMap[][], p[], maxBlock = -1;
	static Queue<Integer> queue = new LinkedList<Integer>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		map = new int[N][N];
		curMap = new int[N][N];
		p = new int[5];

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		// 중복 순열 5번 (최대 5번)
		getPermutation(0);
		
		System.out.println(maxBlock);
	}

	private static void getPermutation(int idx) {
		if (idx == 5) {
			initCurMap();
			process();
			return;
		}

		for (int dir = 0; dir < 4; dir++) {
			p[idx] = dir;
			getPermutation(idx + 1);
		}
	}

	private static void initCurMap() {
		for (int idx = 0; idx < N; idx++) {
			curMap[idx] = map[idx].clone();
		}
	}

	private static void process() {
		for (int pIdx = 0; pIdx < 5; pIdx++) {
			int curDir = p[pIdx];

			// 1. 먼저 해당 방향으로 블록 다 이동하기
			moveDir(curDir);
			// 2. 해당 방향으로 블록 합치기
			plusDir(curDir);
			// 3. 가장 큰 블록 찾기
			findMaxBlock();
		}
	}

	private static void findMaxBlock() {
		int max = -1;
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (curMap[r][c] != 0) {
					max = Math.max(max, curMap[r][c]);
				}
			}
		}
		
		maxBlock = Integer.max(maxBlock, max);
	}

	private static void plusDir(int curDir) {

		if (curDir == 0) { // 위 방향일 때
			for (int c = 0; c < N; c++) {
				queue.clear();

				int r = 0;
				while (r < N - 1) {
					if (curMap[r][c] == 0)
						break;

					if (curMap[r][c] == curMap[r + 1][c]) {
						queue.offer(2 * curMap[r][c]);
						curMap[r][c] = 0;
						curMap[r + 1][c] = 0;
						r += 2;
					} else {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
						++r;
					}
				}

				if (curMap[N - 1][c] != 0) {
					queue.offer(curMap[N - 1][c]);
					curMap[N - 1][c] = 0;
				}

				// 다 넣은 후에
				int rIdx = 0;
				while (!queue.isEmpty()) {
					curMap[rIdx++][c] = queue.poll();
				}
			}
		} else if (curDir == 1) { // 오른쪽 방향일 때
			for (int r = 0; r < N; r++) {
				queue.clear();

				int c = N - 1;
				while (c > 0) {
					if (curMap[r][c] == 0)
						break;

					if (curMap[r][c] == curMap[r][c - 1]) {
						queue.offer(2 * curMap[r][c]);
						curMap[r][c] = curMap[r][c - 1] = 0;
						c -= 2;
					} else {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
						--c;
					}
				}

				if (curMap[r][0] != 0) {
					queue.offer(curMap[r][0]);
					curMap[r][0] = 0;
				}

				// 다 넣은 후에
				int cIdx = N - 1;
				while (!queue.isEmpty()) {
					curMap[r][cIdx--] = queue.poll();
				}
			}
		} else if (curDir == 2) { // 아래쪽 방향일 때
			for (int c = 0; c < N; c++) {
				queue.clear();

				int r = N - 1;
				while (r > 0) {
					if (curMap[r][c] == 0)
						break;

					if (curMap[r][c] == curMap[r - 1][c]) {
						queue.offer(2 * curMap[r][c]);
						curMap[r][c] = curMap[r - 1][c] = 0;
						r -= 2;
					} else {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
						--r;
					}
				}

				if (curMap[0][c] != 0) {
					queue.offer(curMap[0][c]);
					curMap[0][c] = 0;
				}

				int rIdx = N - 1;
				while (!queue.isEmpty()) {
					curMap[rIdx--][c] = queue.poll();
				}
			}
		} else if (curDir == 3) { // 왼쪽 방향일 때
			for (int r = 0; r < N; r++) {
				queue.clear();

				int c = 0;
				while (c < N - 1) {
					if (curMap[r][c] == 0)
						break;

					if (curMap[r][c] == curMap[r][c + 1]) {
						queue.offer(2 * curMap[r][c]);
						curMap[r][c] = curMap[r][c + 1] = 0;
						c += 2;
					} else {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
						++c;
					}
				}

				if (curMap[r][N - 1] != 0) {
					queue.offer(curMap[r][N - 1]);
					curMap[r][N - 1] = 0;
				}

				int cIdx = 0;
				while (!queue.isEmpty()) {
					curMap[r][cIdx++] = queue.poll();
				}
			}
		}

	}

	private static void moveDir(int curDir) {

		if (curDir == 0) { // 위 방향일 때
			for (int c = 0; c < N; c++) {
				queue.clear();
				for (int r = 0; r < N; r++) {
					if (curMap[r][c] != 0) {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
					}
				}

				// 다 넣은 후에
				int rIdx = 0;
				while (!queue.isEmpty()) {
					curMap[rIdx++][c] = queue.poll();
				}
			}
		} else if (curDir == 1) { // 오른쪽 방향일 때
			for (int r = 0; r < N; r++) {
				queue.clear();
				for (int c = N - 1; c >= 0; c--) {
					if (curMap[r][c] != 0) {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
					}
				}

				// 다 넣은 후에
				int cIdx = N - 1;
				while (!queue.isEmpty()) {
					curMap[r][cIdx--] = queue.poll();
				}
			}
		} else if (curDir == 2) { // 아래쪽 방향일 때
			for (int c = 0; c < N; c++) {
				queue.clear();
				for (int r = N - 1; r >= 0; r--) {
					if (curMap[r][c] != 0) {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
					}
				}

				int rIdx = N - 1;
				while (!queue.isEmpty()) {
					curMap[rIdx--][c] = queue.poll();
				}
			}
		} else if (curDir == 3) { // 왼쪽 방향일 때
			for (int r = 0; r < N; r++) {
				queue.clear();
				for (int c = 0; c < N; c++) {
					if (curMap[r][c] != 0) {
						queue.offer(curMap[r][c]);
						curMap[r][c] = 0;
					}
				}
				int cIdx = 0;
				while (!queue.isEmpty()) {
					curMap[r][cIdx++] = queue.poll();
				}
			}
		}
	}
}
