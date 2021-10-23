package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
10/22 11:43 시작 - 1:07

4x4크기의 공간이 있고, 1x1인 정사각형 칸으로 나누어져 있다.
공간의 각 칸은 (x,y)와 같이 표현

x는 행, y는 열 (r, c)

한 칸에는 물고기가 한 마리 존재 -> 각 물고기는 번호와 방향을 가지고 있다.
1<=번호<=16

방향 8가지

1. 상어가 먹음
상어 : (0,0)에 있는 물고기를 먹고, (0,0)에 들어가게 된다.
-> 상어의 방향은 (0,0)에 있던 물고기의 방향과 같다.

2. 이후 물고기가 이동
번호가 작은 물고기부터 순서대로 이동
물고기는 한 칸을 이동
이동할 수 있는 칸 = 빈 칸, 다른 물고기가 있는 칸
이동할 수 없는 칸 = 상어가 있는 칸, 공간의 경계를 넘는 칸

각 물고기는 방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전
이동할 수 있는 칸이 없으면 이동을 하지 않는다. 그 외의 경우에는 이동 => @@@@@@@방향 어떻게하쥐..?
** 물고기가 다른 물고기가 있는 칸으로 이동할 때 : 서로의 위치를 바꾸는 방식으로 이동

3. 
물고기의 이동이 모두 끝나면 상어가 이동

방향에 있는 칸으로 이동 가능 : 한 번에 여러 개의 칸을 이동
상어가 물고기가 있는 칸으로 이동했다면, 그 칸에 있는 물고기를 먹고, 그 물고기의 방향을 가지게 된다.
이동하는 중에 지나가는 칸에 있는 물고기는 먹지 않는다. 물고기가 없는 칸으로는 이동 불가

## 이동할 수 있는 칸이 없으면 집으로 간다.

2 - 3번 반복

구해야할 것 : 상어가 먹을 수 있는 물고기 번호의 합의 최댓값!

## 틀린 이유? 교환했을 때 체크 제대로 안해줌
*/
class Fish implements Cloneable {
	int r;
	int c;
	int dir;
	boolean alive;

	public Fish(int r, int c, int dir, boolean alive) {
		super();
		this.r = r;
		this.c = c;
		this.dir = dir;
		this.alive = alive;
	}
}

public class BOJ_GOld2_19236_청소년상어 {

	static int map[][], maxNumSum = Integer.MIN_VALUE;
	static Fish fishes[];
	static int dr[] = {0,-1,-1,0,1,1,1,0,-1};
	static int dc[] = {0,0,-1,-1,-1,0,1,1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		map = new int[4][4];
		fishes = new Fish[17];
		
		for (int r = 0; r < 4; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < 4; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				
				fishes[map[r][c]] = new Fish(r, c, dir, true);
			}
		}
		
		// logic
		// 상어 세팅 : -1로 표시
		int eatNumSum = map[0][0];
		int sharkR = 0;
		int sharkC = 0;
		int sharkDir = fishes[map[0][0]].dir;
		fishes[map[0][0]].alive = false;
		map[0][0] = -1;
		
		findMaxEatFish(sharkR, sharkC, sharkDir, eatNumSum);
		
		System.out.println(maxNumSum);
		
	}

	private static void findMaxEatFish(int sharkR, int sharkC, int sharkDir, int eatNumSum) {
//		maxNumSum = Math.max(maxNumSum, eatNumSum);
		
		// 물고기 이동
		moveFish();
		
//		3. 
//		물고기의 이동이 모두 끝나면 상어가 이동
//
//		방향에 있는 칸으로 이동 가능 : 한 번에 여러 개의 칸을 이동
//		상어가 물고기가 있는 칸으로 이동했다면, 그 칸에 있는 물고기를 먹고, 그 물고기의 방향을 가지게 된다.
//		이동하는 중에 지나가는 칸에 있는 물고기는 먹지 않는다. 물고기가 없는 칸으로는 이동 불가
//
//		## 이동할 수 있는 칸이 없으면 집으로 간다.
		
//		boolean canEat = false;
		// fishes, map copy
		int copyMap[][] = new int[4][4];
		for (int r = 0; r < 4; r++) {
			copyMap[r] = map[r].clone();
		}
		
		Fish copyFishes[] = new Fish[17];
		for (int idx = 1; idx <= 16; idx++) {
			Fish curFish = fishes[idx];
			copyFishes[idx] = new Fish(curFish.r, curFish.c, curFish.dir, curFish.alive);
		}
		
		int dist = 0;
		boolean success = false;
		while (true) {
			++dist;
			int nr = sharkR + dist * dr[sharkDir];
			int nc = sharkC + dist * dc[sharkDir];
			
			if (!checkBoundary(nr, nc)) break;
			
			if (map[nr][nc] == 0) continue;
			
			int eatNum = map[nr][nc];
			map[sharkR][sharkC] = 0;
			map[nr][nc] = -1;
			
			success = true;
			fishes[eatNum].alive = false;
			findMaxEatFish(nr, nc, fishes[eatNum].dir, eatNumSum + eatNum);
			
			// 다시 복사 원래대로 돌려놓기
			for (int r = 0; r < 4; r++) {
				map[r] = copyMap[r].clone();
			}
			
			for (int idx = 1; idx <= 16; idx++) {
				Fish curFish = copyFishes[idx];
//				fishes[idx] = new Fish(curFish.r, curFish.c, curFish.dir, curFish.alive);
				fishes[idx].r = curFish.r;
				fishes[idx].c = curFish.c;
				fishes[idx].dir = curFish.dir;
				fishes[idx].alive = curFish.alive;
			}
			
		}
		
		if (!success) {
			maxNumSum = Math.max(maxNumSum, eatNumSum);
			return;
		}
		
	}

	private static void moveFish() {
		for (int idx = 1; idx <= 16; idx++) {
			if (!fishes[idx].alive) continue;
			
			Fish curFish = fishes[idx];
			
			for (int d = 0; d < 8; d++) {
				int dir = (curFish.dir + d) % 8;
				dir = dir == 0 ? 8 : dir;
				
				int nr = curFish.r + dr[dir];
				int nc = curFish.c + dc[dir];
				
//				if (d==7) {
//					fishes[idx].dir = dir;
//				}
				// 상어가 있거나 공간의 경계를 넘는 칸
				if (!checkBoundary(nr, nc) || map[nr][nc] == -1) continue;
				
				//이동할 수 있는 칸 = 빈 칸, 다른 물고기가 있는 칸
				if (map[nr][nc] == 0) { // 빈칸일 때
					map[nr][nc] = map[curFish.r][curFish.c];
					map[curFish.r][curFish.c] = 0; // 빈칸으로 바꿔주기 ## 이거 꼭 필요!
					curFish.r = nr;
					curFish.c = nc;
					curFish.dir = dir;
					break;
				} else { // 물고기가 있는 칸일 때
					// 서로의 위치를 바꿔줘야 한다.
					int nNum = map[nr][nc];
					map[nr][nc] = map[curFish.r][curFish.c];
					map[curFish.r][curFish.c] = nNum;
					
					fishes[nNum].r = curFish.r;
					fishes[nNum].c = curFish.c;
					
					curFish.r = nr;
					curFish.c = nc;
					curFish.dir = dir;
					break;
				}
				
			}
		}
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<4 && c>=0 && c<4;
	}

}
