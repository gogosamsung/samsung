package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
10:06 시작 - 11:26 종료 -> 1시간 20분 소요
=> 주사위는 모든 방향에서 고려해야 함을 잊지 말자.

크기 NxM의 지도가 존재.
지도의 오른쪽 - 동쪽, 위쪽 - 북쪽

주사위 굴렸을 때,
- 이동한 칸에 쓰여있는 수가 0이면, 주사위의 바닥면에 쓰여 있는 수가 칸에 복사된다.
: 주사위 바닥면 -> 칸에 복사
- 0이 아닌 경우, 칸에 쓰여 있는 수가 주사위의 바닥면으로 복사도고, 칸에 쓰여있는 수는 0이 된다.
: 칸 -> 주사위 바닥면 복사 , 칸의 수 = 0

주사위가 이동했을 때마다 상단에 쓰여있는 값을 구하는 프로그램 작성!
## -> 주사위는 지도의 바깥으로 이동시킬 수 없다. 만약 바깥으로 이동시키려고 하는 경우는 해당명령 무시 및 출력x
 */
public class BOJ_Gold4_14499_주사위굴리기 {
	
	static int N, M, x, y, K, dice[], diceR[], diceC[], map[][], moveDir[], curTopIdx;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		dice = new int[7];
		map = new int[N][M];
		moveDir = new int[K];
		diceR = new int[]{4,1,3}; // 다음에 바뀔 주사위 위치
		diceC = new int[]{2,1,5,6};
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		for (int k = 0; k < K; k++) {
			moveDir[k] = Integer.parseInt(st.nextToken());
		}
		
		for (int k = 0; k < K; k++) {
			process(k);
		}
		
	}

	static int dr[] = {0,0,0,-1,1};
	static int dc[] = {0,1,-1,0,0};
	private static void process(int k) {
		int curR = x;
		int curC = y;
		int curDir = moveDir[k];
		
		// 1. 이동 방향에 따른 다음 위치 찾기 -> 바깥으로 이동하는 경우, 넘어가기
		int nr = curR + dr[curDir];
		int nc = curC + dc[curDir];

		if (!checkBoundary(nr, nc)) return;
		
		// 2. 주사위 전개도 바꾸기
		changeDiceStruct(curDir);
		
		// 3. 주사위 or 바닥면 숫자 변경
		moveDice(nr, nc);
		
		// 4. 맨 위에 있는 면 출력
		int topIdx = diceR[1];
		System.out.println(dice[topIdx]);
		
		x = nr;
		y = nc;
	}

	private static void moveDice(int nr, int nc) {
		int bottomIdx = diceC[3];
		
		if (map[nr][nc] == 0) {
			map[nr][nc] = dice[bottomIdx];
		} else {
			dice[bottomIdx] = map[nr][nc];
			map[nr][nc] = 0;
		}
	}

	private static void changeDiceStruct(int curDir) {
		int tempDiceR[] = new int[3];
		int tempDiceC[] = new int[4];
		
		if (curDir == 1) { // 오른쪽 방향일 때
			tempDiceR[0] = diceC[3];
			tempDiceR[1] = diceR[0];
			tempDiceR[2] = diceR[1];
			tempDiceC[0] = diceC[0];
			tempDiceC[1] = tempDiceR[1];
			tempDiceC[2] = diceC[2];
			tempDiceC[3] = diceR[2];
		} else if (curDir == 2) {
			tempDiceR[0] = diceR[1];
			tempDiceR[1] = diceR[2];
			tempDiceR[2] = diceC[3];
			tempDiceC[0] = diceC[0];
			tempDiceC[1] = tempDiceR[1];
			tempDiceC[2] = diceC[2];
			tempDiceC[3] = diceR[0];
		} else if (curDir == 3) {
			tempDiceR[0] = diceR[0];
			tempDiceR[1] = diceC[2];
			tempDiceR[2] = diceR[2];
			tempDiceC[0] = diceC[1];
			tempDiceC[1] = tempDiceR[1];
			tempDiceC[2] = diceC[3];
			tempDiceC[3] = diceC[0];
		} else {
			tempDiceR[0] = diceR[0];
			tempDiceR[1] = diceC[0];
			tempDiceR[2] = diceR[2];
			tempDiceC[0] = diceC[3];
			tempDiceC[1] = tempDiceR[1];
			tempDiceC[2] = diceC[1];
			tempDiceC[3] = diceC[2];
		}
		
		diceR = tempDiceR.clone();
		diceC = tempDiceC.clone();
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<N && c>=0 && c<M;
	}

}
