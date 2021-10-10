package algo211006_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
10/07 12:06~12:48 42분소요
Dummy
NxN 사과 보드의 상하좌우 끝에 벽이 있다.

# 사과를 먹으면 뱀의 길이가 늘어난다.
# 뱀이 벽 or 자기자신의 몸과 부딪히면 게임 끝

게임이 시작할 때, 뱀은 맨위 맨좌측에 위치하고 뱀의 길이는 1이다. 뱀은 처음에 오른쪽을 향한다.

뱀은 매 초마다 이동
- 먼저 몸길이를 늘려 머리를 다음칸에 위치시킨다.
- 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
- 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 몸길이는 변하지 않는다.

N, K
K줄에는 사과의 위치
L 뱀의 방향 변환 횟수
X초가 끝난 뒤에 왼쪽 'L' or 오른쪽 'D'로 90도 방향 회전
X는 10000 이하의 양의 정수, 방향 전환 정보는 X가 증가하는 순으로 주어진다.
 */
public class BOJ_Gold5_3190_뱀 {

	static int N, K, L, map[][], timeCnt;
	static int curHeadR = 1, curHeadC = 1, curDir = 0;
	static int dr[] = {0,-1,0,1};
	static int dc[] = {1,0,-1,0};
	static Queue<String[]> changeDir = new LinkedList<String[]>();
	static Queue<int[]> snake = new LinkedList<>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(in.readLine());
		map = new int[N+1][N+1];
		map[1][1] = 1;
		
		K = Integer.parseInt(in.readLine());
		for (int k = 0; k < K; k++) {
			st = new StringTokenizer(in.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			map[r][c] = 2;
		}
		
		L = Integer.parseInt(in.readLine());
		
		for (int l = 0; l < L; l++) {
			st = new StringTokenizer(in.readLine(), " ");
			String X = st.nextToken();
			String C = st.nextToken();
			
			changeDir.offer(new String[] {X, C});
		}
		
		// logic
		snake.offer(new int[]{curHeadR, curHeadC});
		while (true) {
			++timeCnt;
			
			// 몸길이 늘리기
			int nHeadR = curHeadR + dr[curDir];
			int nHeadC = curHeadC + dc[curDir];
			
			// 몸이나 벽이면 게임 끝
			if (!checkBoundary(nHeadR, nHeadC) || map[nHeadR][nHeadC] == 1) break;
			
			// 만약에 사과가 있다면
			if (map[nHeadR][nHeadC] == 2) {
				map[nHeadR][nHeadC] = 1;
				snake.offer(new int[] {nHeadR, nHeadC});
			} else {
				map[nHeadR][nHeadC] = 1;
				snake.offer(new int[] {nHeadR, nHeadC});
				// 꼬리 지워주기
				int[] tail = snake.poll();
				map[tail[0]][tail[1]] = 0;
			}
			
			curHeadR = nHeadR;
			curHeadC = nHeadC;
			
			// 방향 바꾸기 게임 시작 후 X초가 끝난 후,
			if (!changeDir.isEmpty() && Integer.parseInt(changeDir.peek()[0]) == timeCnt) {
				String[] change = changeDir.poll();
				
				if (change[1].equals("L")) {
					curDir = (curDir + 1)%4;
				} else {
					curDir = curDir == 0 ? (4 - 1)%4 : (curDir - 1)%4;
				}
			}
		}
		
		System.out.println(timeCnt);
	}
	
	private static boolean checkBoundary(int r, int c) {
		return r>=1 && r<=N && c>=1 && c<=N;
	}
}
