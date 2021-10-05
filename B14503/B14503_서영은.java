package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
11:40 시작 - 12:35 종료
-> 동서남북 방향 헷갈려서 틀리기...ㅠ 동쪽은 오른쪽, 서쪽은 왼쪽!

로봇 청소기 -> 청소하는 영역의 개수

NxM의 직사각형
청소기 - 바라보는 방향 : 동서남북 중 하나
칸 - 벽 or 빈칸

청소기 - 바라보는 방향이 있고, 이 방향은 동,서,남,북 중 하나이다.

 */
public class BOJ_Gold5_14503_로봇청소기 {
	static int N, M, map[][], curR, curC, curDir;
	static int dr[] = {-1,0,1,0};
	static int dc[] = {0,1,0,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		st = new StringTokenizer(in.readLine(), " ");
		curR = Integer.parseInt(st.nextToken());
		curC = Integer.parseInt(st.nextToken());
		curDir = Integer.parseInt(st.nextToken());
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// logic
		boolean fourDirCheck = false;
		while (true) {
			fourDirCheck = false;
			// 1번
			if (map[curR][curC] != -1 && map[curR][curC] != -1) {
				map[curR][curC] = -1;
			}
			// a+b번
			for (int dir = 1; dir <= 4; dir++) {
				int nDir = (4 + curDir - dir) % 4;
				
				int nr = curR + dr[nDir];
				int nc = curC + dc[nDir];
				
				if (map[nr][nc] == 1 || map[nr][nc] == -1) continue;
				
				curR = nr;
				curC = nc;
				curDir = nDir;
				fourDirCheck = true;
				break;
			}
			
			if (fourDirCheck) continue;
			
			// c번
			int moveDir = (curDir + 2) % 4;
			
			int nr = curR + dr[moveDir];
			int nc = curC + dc[moveDir];
			
			if (map[nr][nc] == 1) {
				break;
			} else {
				curR = nr;
				curC = nc;
			}
		}
		
		System.out.println(checkCleanCell());
	}
	
	private static int checkCleanCell() {
		int sum = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == -1) {
					++sum;
				}
			}
		}
		
		return sum;
	}
}
