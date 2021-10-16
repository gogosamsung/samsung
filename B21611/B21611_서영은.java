package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
10/16 9:25 시작 - 10:51 종료 : 1시간 25분 소요
+ 17분 디버깅

NxN 격자에서 연습
N은 항상 홀수! (1,1) - (N,N)

마법사 상어 : ((N+1)/2, (N+1)/2)

일부 칸과 칸 사이에는 벽 O

실선 - 벽, 점선 - 벽X

가장 처음에 상어가 있는 칸을 제외한 나머지 칸에는 구슬이 하나 들어갈 수 있다.

구슬 - 1, 2, 3번 구슬

같은 번호를 가진 구슬이 연속하는 칸에 있으면, 그 구슬을 연속하는 구슬이라고 한다.

1.
블리자드 마법 -> 방향 di, 거리 si를 정해야 한다.
방향 : 위아왼오 - 1,2,3,4로 나타낸다.

상어는 di방향으로 거리가 si 이하인 모든 칸에 얼음 파편을 던져 그 칸에 있는 구슬을 모두 파괴한다.
구슬이 파괴되면, 그 칸은 구슬이 들어있지 않은 빈 칸이 된다.
벽은 파괴되지 않는다.

구슬이 파괴된 후에는 빈칸이 생겨 구슬이 이동

2. 구슬이 폭발하는 단계
4개 이상 연속하는 구슬이 있을 때 발생
폭발이 발생해 빈 칸이 생기면 구슬이 이동
=> 또 4개 이상 연속하는 구슬이 있으면 구슬이 다시 폭발한다.

3. 구슬이 변화하는 단계
연속하는 구슬 - 하나의 그룹 : 두 개의 구슬 A와 B로 변한다.
구슬 A의 번호 : 구릅에 들어있는 구슬의 개수
구슬 B의 번호 : 그룹을 이루고 있는 구슬의 번호
A - B 순서대로 칸에 들어간다.

구해야할 것 : 1×(폭발한 1번 구슬의 개수) + 2×(폭발한 2번 구슬의 개수) + 3×(폭발한 3번 구슬의 개수)

입력 :
N, M(블리자드 마법의 방향)
구슬이 없는 칸 - 0
상어가 있는 칸 - 0

// 런타임 에러 발생
 * 이유? circles index 고려 안해줌! 길이 고려해줬더니 해결
 */
public class BOJ_Gold2_21611_마법사상어와블리자드 {
	
	static int N, M, map[][], magic[][], sharkR, sharkC, bombCircles[];
	static ArrayList<int[]> pos;
	static ArrayList<Integer> removeIdx;
	static ArrayList<Integer> circles;
	static Queue<Integer> changeCircles;
	static int dr[] = {0,-1,1,0,0};
	static int dc[] = {0,0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		sharkR = (N+1)/2;
		sharkC = (N+1)/2;
		
		map = new int[N+1][N+1];
		magic = new int[M][2];
		pos = new ArrayList<>();
		removeIdx = new ArrayList<>();
		circles = new ArrayList<>();
		changeCircles = new LinkedList<Integer>();
		bombCircles = new int[4];
		
		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(in.readLine(), " ");
			magic[m][0] = Integer.parseInt(st.nextToken());
			magic[m][1] = Integer.parseInt(st.nextToken());
		}
		
		// logic
		
		// 0. 달팽이 이용해서 pos 세팅
		settingPos();
		
		//블리자드 M회
		for (int m = 0; m < M; m++) {
			// 1. 얼음 던지기
			breakIce(m);
			
			// 2. 구슬 폭발
			while (checkFourCircles()) {
				bombCircles();
			}
			
			// 3. 구슬 변화
			change();
		}
		
		System.out.println(answer());
	}
	
	private static int answer() {
		return bombCircles[1] + 2*bombCircles[2] + 3*bombCircles[3];
	}

	private static void change() {
		int circlesLen = circles.size();
		
		if (circlesLen == 0) {
			for (int r = 1; r <= N; r++) {
				Arrays.fill(map[r], 0);
			}
			return;
		}
		
		int curNum = circles.get(0);
		int cnt = 1;
		changeCircles.clear();
		
		if (circlesLen > 1) {
			for (int idx = 1; idx < circlesLen; idx++) {
				if (curNum == circles.get(idx)) {
					++cnt;
				} else {
					changeCircles.offer(cnt);
					changeCircles.offer(curNum);
					curNum = circles.get(idx);
					cnt = 1;
				}
			}
		}
		
		changeCircles.offer(cnt);
		changeCircles.offer(curNum);
		
		for (int r = 1; r <= N; r++) {
			Arrays.fill(map[r], 0);
		}
		
		// map에 다시 쎄팅
		int idx = 0;
		int posLen = pos.size();
		while (idx < posLen && !changeCircles.isEmpty()) {
			int[] curPos = pos.get(idx);
			map[curPos[0]][curPos[1]] = changeCircles.poll();
			++idx;
		}
		
	}

	private static void bombCircles() {
		removeIdx.clear();
		int circlesLen = circles.size();
		if (circlesLen == 0) return;
		int curNum = circles.get(0);
		int cnt = 1;
		
		if (circlesLen > 1) {
			for (int idx = 1; idx < circlesLen; idx++) {
				if (curNum == circles.get(idx)) {
					++cnt;
				} else {
					if (cnt >= 4) {
						while (cnt > 0) {
							removeIdx.add(idx-cnt);
							++bombCircles[curNum];
							--cnt;
						}
					}
					curNum = circles.get(idx);
					cnt = 1;
				}
			}
			
			if (cnt >= 4) {
				while (cnt > 0) {
					removeIdx.add(circlesLen-cnt); // 인덱스 주의
					++bombCircles[curNum];
					--cnt;
				}
			}
			
			// 삭제
			int removeIdxLen = removeIdx.size();
			for (int idx = removeIdxLen-1; idx >= 0; idx--) {
				circles.remove((int) removeIdx.get(idx)); // *******(int)로 형변환 해줘야 index로 인식한다
			}
		}
	}

	private static boolean checkFourCircles() {
		int circlesLen = circles.size();
		
		if (circlesLen == 0) return false;
		int curNum = circles.get(0);
		int cnt = 1;
		
		if (circlesLen > 1) {
			for (int idx = 1; idx < circlesLen; idx++) {
				if (curNum == circles.get(idx)) {
					++cnt;
				} else {
					if (cnt >= 4) return true;
					curNum = circles.get(idx);
					cnt = 1;
				}
			}
		
		
			if (cnt >= 4) return true;
		}
		return false;
	}

	private static void breakIce(int m) {
		int d = magic[m][0];
		int s = magic[m][1];
		
		for (int dist = 1; dist <= s; dist++) {
			int nr = sharkR + dist*dr[d];
			int nc = sharkC + dist*dc[d];
			map[nr][nc] = 0;
		}
		
		// circles에 남아있는 구슬 다 담기
		circles.clear();
		
		for (int[] curPos : pos) {
			if (map[curPos[0]][curPos[1]] == 0) continue;
			circles.add(map[curPos[0]][curPos[1]]);
		}
	}

	static int snailDr[] = {0,1,0,-1};
	static int snailDc[] = {1,0,-1,0};
	private static void settingPos() {
		int r = 1;
		int c = 1;
		int dir = 0;
		
		boolean visited[][] = new boolean[N+1][N+1];
		
		while (!(r == (N+1)/2 && c == (N+1)/2)) {
			pos.add(0, new int[] {r,c});
			visited[r][c] = true;
			
			int nr = r + snailDr[dir];
			int nc = c + snailDc[dir];
			
			if (!boundaryCheck(nr, nc) || visited[nr][nc]) {
				dir = (dir+1)%4;
				nr = r + snailDr[dir];
				nc = c + snailDc[dir];
			}
			
			r = nr;
			c = nc;
		}
	}
	
	private static boolean boundaryCheck(int r, int c) {
		return r>=1 && r<=N && c>=1 && c<=N;
	}
}
