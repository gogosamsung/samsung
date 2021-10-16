package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
10/16 17:49 시작 - 6:31 끝 (42분 소요)

 유현 - 새 집으로 이사
 새 집의 크기 NxN의 격자판
 각각의 칸 (r,c)
 
 파이프는 매우 무겁기 때문에, 파이프를 밀어서 이동
 파이프가 벽을 긁으면 안되고, 항상 빈 칸만 차지해야 한다.
 
 파이프를 밀 수 있는 방향 : 3가지
 => 파이프는 밀면서 회전시킬 수 있다! 회전은 45도만 회전시킬 수 있음
 미는 방향은 오른쪽, 아래, 오른쪽 아래 대각선 방향
 
 
 */
public class BOJ_Gold5_17070_파이프옮기기1 {

	static int N, map[][], wayCnt;
	static int dr[] = {0,1,1}; // 오론쪽 대각선 아래
	static int dc[] = {1,1,0};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		
		map = new int[N+1][N+1];
		
		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// logic
		dfs(0, 1, 1, 1, 2);
		
		System.out.println(wayCnt);
		
	}
	
	private static void dfs(int shape, int frontR, int frontC, int backR, int backC) {
		if ((backR == N && backC == N) || (frontR == N && frontC == N)) {
			++wayCnt;
			return;
		}
		
		int frontNR = -1;
		int frontNC = -1;
		int backNR = -1;
		int backNC = -1;
		// 가로일 때
		if (shape == 0) {
			frontNR = frontR;
			frontNC = frontC + 1;
			for (int dir = 0; dir <= 1; dir++) {
				backNR = frontNR + dr[dir];
				backNC = frontNC + dc[dir];
				
				if (!boundaryCheck(frontNR, frontNC, backNR, backNC) || map[frontNR][frontNC] == 1 || map[backNR][backNC] == 1) continue;
				if (dir == 1 && (map[frontNR+1][frontNC] == 1 || map[frontNR][frontNC+1] == 1)) continue;
				
				dfs(dir, frontNR, frontNC, backNR, backNC);
			}
		// 대각선일 때 
		} else if (shape == 1) {
			frontNR = frontR + 1;
			frontNC = frontC + 1;
			for (int dir = 0; dir <= 2; dir++) {
				backNR = frontNR + dr[dir];
				backNC = frontNC + dc[dir];
				
				if (!boundaryCheck(frontNR, frontNC, backNR, backNC) || map[frontNR][frontNC] == 1 || map[backNR][backNC] == 1) continue;
				if (dir == 1 && (map[frontNR+1][frontNC] == 1 || map[frontNR][frontNC+1] == 1)) continue;
				
				dfs(dir, frontNR, frontNC, backNR, backNC);
			}
		// 세로일 때
		} else {
			frontNR = frontR + 1;
			frontNC = frontC;
			for (int dir = 1; dir <= 2; dir++) {
				backNR = frontNR + dr[dir];
				backNC = frontNC + dc[dir];
				
				if (!boundaryCheck(frontNR, frontNC, backNR, backNC) || map[frontNR][frontNC] == 1 || map[backNR][backNC] == 1) continue;
				if (dir == 1 && (map[frontNR+1][frontNC] == 1 || map[frontNR][frontNC+1] == 1)) continue;
				
				dfs(dir, frontNR, frontNC, backNR, backNC);
			}
		}
	}

	private static boolean boundaryCheck(int frontR, int frontC, int backR, int backC) {
		if (frontR >= 1 && frontR <= N && frontC >= 1 && frontC <= N &&
				backR >= 1 && backR <= N && backC >= 1 && backC <= N) {
			return true;
		}
		return false;
	}

}
