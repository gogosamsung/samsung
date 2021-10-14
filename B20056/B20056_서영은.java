package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
시작 10/14 12:12 - 

마법사 상어 - 크기가 NxN인 격자에 파이어볼 M개를 발사
i번 파이어볼의 위치 r,c 질량 m 방향 d 속력 s

격자의 행과 열 1~N => 1번 행은 N번과 연결, 1번 열은 N번 열과 연결

파이어볼의 방향은 인접한 8개의 칸의 방향 
dr : -1 -1 0 1 1 1 0 -1
dc : 0 1 1 1 0 -1 -1 -1

#마법사 상어가 모든 파이어볼에게 이동을 명령
1. 모든 파이어볼이 자신의 방향 d로 속력 s만큼 이동
	- 이동하는 중에는 같은 칸에 여러 개의 파이어볼이 있을 수도 있다.
2. 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 다음과 같은 일이 일어난다.
	- 같은 칸에 있는 파이어볼 하나로 합쳐짐
	- 4개의 파이어볼로 나누어짐
	- 나누어진 파이어볼의 질량, 속력, 방향은 다음과 같다.
		질량 = [합쳐진 파이어볼 질량의 합 / 5]
		속력 = [합쳐진 파이어볼 속력의 합/합쳐진 파이어볼의 개수]
		합쳐지는 파이어볼 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0,2,4,6이 되고, 그렇지 않으면 1,3,5,7이 된다.
	- 질량이 0인 파이어볼은 소멸되어 없어진다.

## K번 명령한 후, 남아있는 파이어볼 질량의 합
 */
class FireBall {
	int r;
	int c;
	int m;
	int s;
	int d;

	public FireBall(int r, int c, int m, int s, int d) {
		super();
		this.r = r;
		this.c = c;
		this.m = m;
		this.s = s;
		this.d = d;
	}
}

public class BOJ_Gold5_20056_마법사상어와파이어볼 {

	static int N, M, K;
	static ArrayList<FireBall> fireBalls;
	static ArrayList<FireBall>[][] map;
	static int dr[] = {-1,-1,0,1,1,1,0,-1};
	static int dc[] = {0,1,1,1,0,-1,-1,-1};
	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		fireBalls = new ArrayList<>();
		map = new ArrayList[N+1][N+1];
		
		for (int r = 0; r <= N; r++) {
			for (int c = 0; c <= N; c++) {
				map[r][c] = new ArrayList<>();
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			FireBall curFireBall = new FireBall(r, c, m, s, d);
			fireBalls.add(curFireBall);
			map[r][c].add(curFireBall); // 파이어볼의 index 넣어주기
		}
		
		// logic
		for (int k = 0; k < K; k++) {
			// 1. 모든 파이어볼이 자신의 방향 di로 속력 si칸 만큼 이동한다.
			moveAllFireBalls();
			
			// 2. 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 ~~ 일이 일어난다.
			workMultipleFireBalls();
		}
		
		System.out.println(getTotalM());
	}
	
	private static int getTotalM() {
		int totalM = 0;
		
		for (FireBall fireBall : fireBalls) {
			totalM += fireBall.m;
		}
		
		return totalM;
	}

	private static void workMultipleFireBalls() {
		
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				if (map[r][c].size() < 2) continue;
				
				int cnt = map[r][c].size();
				int sumM = 0;
				int sumS = 0;
				int dirOddCnt = 0;
				int dirEvenCnt = 0;
				for (FireBall fireBall : map[r][c]) {
					sumM += fireBall.m;
					sumS += fireBall.s;
					if (fireBall.d % 2 == 0) {
						++dirEvenCnt;
					} else {
						++dirOddCnt;
					}
					
					fireBalls.remove(fireBall);
				}
				map[r][c].clear();
				
				int avgM = sumM / 5;
				
				if (avgM == 0) continue; // 질량이 0인 파이어볼은 소멸되어 없어진다.
				
				int avgS = sumS / cnt;
				int dir[];
				
				if (dirEvenCnt == cnt || dirOddCnt == cnt) {
					dir = new int[] {0,2,4,6};
				} else {
					dir = new int[] {1,3,5,7};
				}
				
				for (int i = 0; i < 4; i++) {
					FireBall addFireBall = new FireBall(r,c,avgM,avgS,dir[i]);
					map[r][c].add(addFireBall);
					fireBalls.add(addFireBall);
				}
				
			}
		}
		
	}

	private static void moveAllFireBalls() {
		
		for (FireBall fireBall : fireBalls) {
			int r = fireBall.r;
			int c = fireBall.c;
			int s = fireBall.s;
			int d = fireBall.d;
			
			int nr = (r + s*dr[d]%N)%N;
			int nc = (c + s*dc[d]%N)%N;
			
			nr = nr > 0 ? nr : N+nr;
			nc = nc > 0 ? nc : N+nc;
			
			// map과 arraylist 모두 이동
			fireBall.r = nr;
			fireBall.c = nc;
			map[nr][nc].add(fireBall);
			map[r][c].remove(fireBall);
		}
		
	}

}
