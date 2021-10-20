package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
10/19 12:04 시작 - 12:25
10:45 - 11:28
-> 20분 + 33분 -> 53분 소요

상어 : 1<=번호<=M
모든 번호는 서로 다르다.

상어들은 영역을 사수하기 위해 다른 상어들을 쫓아내려고 하는데, 1의 번호를 가진 어른 상어는 가장 강력해서 나머지 모두를 쫓아낼 수 있다.

NxN 크기의 격자 중 M개의 칸에 상어가 한 마리 씩

1. 모든 상어가 자신의 위치에 자신의 냄새를 뿌린다.
2. 1초마다 모든 상어가 동시에 상하좌우로 인접한 칸 중 하나로 이동하고, 자신의 냄새를 그 칸에 뿌린다.
냄새는 상어가 k번 이동하고 나면 사라진다.

상어가 이동방향 결정시 : 인접한 칸 중 아무 냄새가 없는 칸의 방향 -> 없으면, 자신의 냄새가 있는 칸의 방향 => 가능한 칸이 여러개인 경우, 특정한 우선순위를 따른다.

1 2 3 4 : 위 아래 왼쪽 오른쪽
//상어가 맨 처음에 보고 있는 방향은 입력으로 주어지고, 그 후에는 방금 이동한 방향이 보고 있는 방향이 된다.
//
//모든 상어가 이동한 후 한 칸에 여러 마리의 상어가 남아 있으면, 가장 작은 번호를 가진 상어를 제외하고 모두 격자 밖으로 쫓겨난다.
 * 
 * 1번 상어만 격자에 남게 되기까지 몇 초가 걸리는지를 구하는 프로그램을 작성하시오.
 * 
 * ## 넘다 = 초과하다
 */
class Shark {
	int r;
	int c;
	int curDir;
	int dir[][];
	boolean alive;
	
	public Shark(int r, int c) {
		super();
		this.r = r;
		this.c = c;
		this.dir = new int[5][4];
		this.alive = true;
	}
	
	public void setCurDir(int curDir) {
		this.curDir = curDir;
	}
	
	public void setDir(int[][] dir) {
		for (int d = 0; d < 5; d++) {
			this.dir[d] = dir[d].clone();
		}
	}
}

class Smell {
	int sharkNum;
	int remainTime;
	public Smell(int sharkNum, int remainTime) {
		super();
		this.sharkNum = sharkNum;
		this.remainTime = remainTime;
	}
}
public class BOJ_Gold3_19237_어른상어 {

	static int N, M, k, map[][], time;
	static Smell smells[][];
	static Shark[] sharks;
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		smells = new Smell[N][N];
		sharks = new Shark[M+1];
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				smells[r][c] = new Smell(0, 0);
			}
		}
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if (map[r][c] != 0) {
					sharks[map[r][c]] = new Shark(r, c);
				}
			}
		}

		st = new StringTokenizer(in.readLine(), " ");
		for (int m = 1; m <= M; m++) {
			sharks[m].setCurDir(Integer.parseInt(st.nextToken()));
		}
		
		for (int m = 1; m <= M; m++) {
			int[][] dir = new int[5][4];
			for (int d = 1; d <= 4; d++) {
				st = new StringTokenizer(in.readLine(), " ");
				
				dir[d][0] = Integer.parseInt(st.nextToken());
				dir[d][1] = Integer.parseInt(st.nextToken());
				dir[d][2] = Integer.parseInt(st.nextToken());
				dir[d][3] = Integer.parseInt(st.nextToken());
			}
			sharks[m].setDir(dir);
		}
		
//		1. 모든 상어가 자신의 위치에 자신의 냄새를 뿌린다.
		setSmell();
		// logic
		while (++time <= 1000) {
//		1초마다 모든 상어가 동시에 상하좌우로 인접한 칸 중 하나로 이동하고, 자신의 냄새를 그 칸에 뿌린다.
			moveShark();
			
			removeSmell();
			
			setSmell();
//		냄새는 상어가 k번 이동하고 나면 사라진다.
//		상어가 이동방향 결정시 : 인접한 칸 중 아무 냄새가 없는 칸의 방향 -> 없으면, 자신의 냄새가 있는 칸의 방향 => 가능한 칸이 여러개인 경우, 특정한 우선순위를 따른다.
//		1 2 3 4 : 위 아래 왼쪽 오른쪽
			
			if (checkFirstShark()) break;
		}
		
		System.out.println(time > 1000 ? -1 : time);

	}
	
	private static boolean checkFirstShark() {
		int cnt = 0;
		for (int idx = 1; idx <= M; idx++) {
			if (sharks[idx].alive) ++cnt;
		}
		
		if (cnt == 1) return true;
		return false;
	}

	private static void removeSmell() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (smells[r][c].remainTime > 0) {
					--smells[r][c].remainTime;
					
					if (smells[r][c].remainTime == 0) {
						smells[r][c].sharkNum = 0;
					}
				}
			}
		}
		
	}

	static int dr[] = {0,-1,1,0,0};
	static int dc[] = {0,0,0,-1,1};
	private static void moveShark() {
		for (int idx = 1; idx <= M; idx++) {
			if (!sharks[idx].alive) continue;
//			상어가 이동방향 결정시 : 인접한 칸 중 아무 냄새가 없는 칸의 방향 -> 없으면, 자신의 냄새가 있는 칸의 방향 => 가능한 칸이 여러개인 경우, 특정한 우선순위를 따른다.
			int curR = sharks[idx].r;
			int curC = sharks[idx].c;
			int curDir = sharks[idx].curDir;
			
			int dirType[] = sharks[idx].dir[curDir];
			
			boolean noSmellSuccess = false;
			// 아무 냄새가 없는 칸에서 찾기
			for (int dir = 0; dir < 4; dir++) {
				int nr = curR + dr[dirType[dir]];
				int nc = curC + dc[dirType[dir]];
				
				if (!checkBoundary(nr, nc) || smells[nr][nc].remainTime != 0) continue;
				
				noSmellSuccess = true;
				sharks[idx].r = nr;
				sharks[idx].c = nc;
				sharks[idx].curDir = dirType[dir];
				
				// 같은 칸에 속하는지 확인
				if (map[nr][nc] != 0) {
					if (map[nr][nc] < idx) {
						sharks[idx].alive = false;
					} else { // 이동한 상어가 우선순위가 더 클 때
						sharks[map[nr][nc]].alive = false;
						map[nr][nc] = idx;
					}
				} else {
					map[nr][nc] = idx;
				}
				
				map[curR][curC] = 0;
				break;
			}
			
			if (noSmellSuccess) continue;
			
			// 자신의 냄새가 있는 칸의 방향
			for (int dir = 0; dir < 4; dir++) {
				int nr = curR + dr[dirType[dir]];
				int nc = curC + dc[dirType[dir]];
				
				if (!checkBoundary(nr, nc) || smells[nr][nc].sharkNum != idx) continue;
				
				sharks[idx].r = nr;
				sharks[idx].c = nc;
				sharks[idx].curDir = dirType[dir];
				
				// 같은 칸에 속하는지 확인
				if (map[nr][nc] != 0) {
					if (map[nr][nc] < idx) {
						sharks[idx].alive = false;
					} else { // 이동한 상어가 우선순위가 더 클 때
						sharks[map[nr][nc]].alive = false;
						map[nr][nc] = idx;
					}
				} else {
					map[nr][nc] = idx;
				}

				map[curR][curC] = 0;
				break;
			}
		}
		
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<N && c>=0 && c<N;
	}

	private static void setSmell() {
		for (int idx = 1; idx <= M; idx++) {
			// 상어가 죽어있으면 넘김
			if (!sharks[idx].alive) continue;
			
			int curR = sharks[idx].r;
			int curC = sharks[idx].c;
			smells[curR][curC].sharkNum = idx;
			smells[curR][curC].remainTime = k;
		}
	}
}
