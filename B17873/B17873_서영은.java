package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
새로운 게임 - NxN 체스판, 말의 개수 K개
하나의 말 위에 다른 말을 올릴 수 있다.

체스판의 각 칸 : 흰색, 빨간색, 파란색 중 하나로 칠해져 있다.

체스판 위에 말 K개를 놓고 시작. 1~K 번호. 이동 방향 정해져 있다. 위 아래 왼 오

## 턴 한번
- 1~K번 말까지 순서대로 이동시키는 것
한 말이 이동할 때, 위에 올려져 있는 말도 함께 이동.

말의 이동 방향에 있는 칸에 따라서 말의 이동이 다르다.

***턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임 종료
규칙
이동하려는 칸
- 흰색 : 그 칸으로 이동.
- 빨간색 : 이동한 후에, 순서를 반대로 바꾼다.(이동한 말들만)
- 파란색 : 이동 방향을 반대로 하고, 한 칸 이동. 방향을 반대로 바꾼 후에 이동하려는 칸이 파란색인 경우는 이동 X
- 체스판을 벗어나는 경우에는 파란색과 같은 경우이다!

*** 구해야할 것 : 게임이 종료되는 턴의 번호!
4<=N<=12
4<=K<=10

문제 잘못 이해해서 1시간 40분 소요..
 */
class Chess {
	int chessIdx;
	int r;
	int c;
	int dir;

	public Chess(int chessIdx, int r, int c, int dir) {
		super();
		this.chessIdx = chessIdx;
		this.r = r;
		this.c = c;
		this.dir = dir;
	}
}

public class BOJ_Gold2_17873_새로운게임2 {

	static ArrayList<Integer> chessIdxMap[][];
	static ArrayList<Chess> chessList;
	static int colorMap[][], turnNum, N, K;
	static boolean finish;

	static int dr[] = { 0, 0, 0, -1, 1 };
	static int dc[] = { 0, 1, -1, 0, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		chessIdxMap = new ArrayList[N][N];
		colorMap = new int[N][N];
		chessList = new ArrayList<>();

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				colorMap[r][c] = Integer.parseInt(st.nextToken());
				chessIdxMap[r][c] = new ArrayList<>();
			}
		}

		chessList.add(new Chess(0, -1, -1, -1));
		for (int k = 1; k <= K; k++) {
			st = new StringTokenizer(in.readLine(), " ");

			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int dir = Integer.parseInt(st.nextToken());

			chessList.add(new Chess(k, r, c, dir));
			chessIdxMap[r][c].add(k);
		}

		while (true) {
			++turnNum;
			
			if (turnNum > 1000) {
				turnNum = -1;
				break;
			}

			// 1. chess 순서대로 진행
			if (startChessOrder()) {
				break;
			}
		}
		
		System.out.println(turnNum);
	}

	private static boolean startChessOrder() {
		for (int cIdx = 1; cIdx <= K; cIdx++) {
			moveChess(cIdx);
			
			if (finish) {
				return true;
			}
		}
		return false;
	}

	private static void moveChess(int cIdx) {
		Chess curChess = chessList.get(cIdx);
		int curCIdx = curChess.chessIdx;
		int curR = curChess.r;
		int curC = curChess.c;
		int curDir = curChess.dir;

		int curMapChessCnt = chessIdxMap[curR][curC].size();
		int nr = curR + dr[curDir];
		int nc = curC + dc[curDir];

		if (!checkBoundary(nr, nc) || colorMap[nr][nc] == 2) { // 파란색
			curDir = getReverseDir(curDir);
			curChess.dir = curDir;

			nr = curR + dr[curDir];
			nc = curC + dc[curDir];

			if (checkBoundary(nr, nc) && colorMap[nr][nc] != 2) {
				int moveStartIdx = 0;
				// 말 위치 찾기
				for (int curMapIdx = 0; curMapIdx < curMapChessCnt; curMapIdx++) {
					// 체크맵의 idx가 같다면
					if (chessIdxMap[curR][curC].get(curMapIdx) == curCIdx) {
						moveStartIdx = curMapIdx;
						break;
					}
				}
				
				if (colorMap[nr][nc] == 0) { // 흰
					// 말 이동
					for (int curMapIdx = moveStartIdx; curMapIdx < curMapChessCnt; curMapIdx++) {
						Integer curChessIdx = chessIdxMap[curR][curC].get(curMapIdx);
						chessIdxMap[nr][nc].add(curChessIdx);
						// 체스 리스트도 갱신
						chessList.get(curChessIdx).r = nr;
						chessList.get(curChessIdx).c = nc;
					}
					// 말 삭제
					for (int curMapIdx = curMapChessCnt-1; curMapIdx >= moveStartIdx; curMapIdx--) {
						chessIdxMap[curR][curC].remove(curMapIdx);
					}
				} else { // 빨
					// 말 이동
					for (int curMapIdx = curMapChessCnt-1; curMapIdx >= moveStartIdx; curMapIdx--) {
						Integer curChessIdx = chessIdxMap[curR][curC].get(curMapIdx);
						chessIdxMap[nr][nc].add(curChessIdx);
						// 체스 리스트도 갱신
						chessList.get(curChessIdx).r = nr;
						chessList.get(curChessIdx).c = nc;
					}
					// 말 삭제
					for (int curMapIdx = curMapChessCnt-1; curMapIdx >= moveStartIdx; curMapIdx--) {
						chessIdxMap[curR][curC].remove(curMapIdx);
					}
				}
				if (chessIdxMap[nr][nc].size() >= 4) {
					finish = true;
				}
			}
		} else if (colorMap[nr][nc] == 0) {
			int moveStartIdx = 0;
			// 움직일 말의 위치 찾기
			for (int curMapIdx = 0; curMapIdx < curMapChessCnt; curMapIdx++) {
				// 체크맵의 idx가 같다면
				if (chessIdxMap[curR][curC].get(curMapIdx) == curCIdx) {
					moveStartIdx = curMapIdx;
					break;
				}
			}

			// 말 이동
			for (int curMapIdx = moveStartIdx; curMapIdx < curMapChessCnt; curMapIdx++) {
				Integer curChessIdx = chessIdxMap[curR][curC].get(curMapIdx);
				chessIdxMap[nr][nc].add(curChessIdx);
				// 체스 리스트도 갱신
				chessList.get(curChessIdx).r = nr;
				chessList.get(curChessIdx).c = nc;
			}
			// 말 삭제
			for (int curMapIdx = curMapChessCnt-1; curMapIdx >= moveStartIdx; curMapIdx--) {
				chessIdxMap[curR][curC].remove(curMapIdx);
			}
			if (chessIdxMap[nr][nc].size() >= 4) {
				finish = true;
			}
		} else if (colorMap[nr][nc] == 1) { // 빨간색
			int moveStartIdx = 0;
			// 움직일 말의 위치 찾기
			for (int curMapIdx = 0; curMapIdx < curMapChessCnt; curMapIdx++) {
				// 체크맵의 idx가 같다면
				if (chessIdxMap[curR][curC].get(curMapIdx) == curCIdx) {
					moveStartIdx = curMapIdx;
					break;
				}
			}

			// 말 이동
			for (int curMapIdx = curMapChessCnt - 1; curMapIdx >= moveStartIdx; curMapIdx--) {
				Integer curChessIdx = chessIdxMap[curR][curC].get(curMapIdx);
				chessIdxMap[nr][nc].add(curChessIdx);
				// 체스 리스트도 갱신
				chessList.get(curChessIdx).r = nr;
				chessList.get(curChessIdx).c = nc;

			}
			// 말 삭제
			for (int curMapIdx = curMapChessCnt - 1; curMapIdx >= moveStartIdx; curMapIdx--) {
				chessIdxMap[curR][curC].remove(curMapIdx);
			}
			if (chessIdxMap[nr][nc].size() >= 4) {
				finish = true;
			}
		}
	}

	private static boolean checkBoundary(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	private static int getReverseDir(int dir) {
		if (dir == 1)
			return 2;
		else if (dir == 2)
			return 1;
		else if (dir == 3)
			return 4;
		else
			return 3;
	}
}
