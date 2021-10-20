package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
10/19 10:40 시작 - 11:23 끝 -> 43분 소요

 */
public class BOJ_Gold4_15685_드래곤커브 {

	static ArrayList<Integer> dirList;
	static int N, curve[][], squareCnt;
	static boolean map[][];
	static int dx[] = {1,0,-1,0}; // x축 -> / y축 아래
	static int dy[] = {0,-1,0,1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());
		
		curve = new int[N][4];
		map = new boolean[101][101];
		dirList = new ArrayList<>();
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(in.readLine(), " ");
			curve[n][0] = Integer.parseInt(st.nextToken());
			curve[n][1] = Integer.parseInt(st.nextToken());
			curve[n][2] = Integer.parseInt(st.nextToken());
			curve[n][3] = Integer.parseInt(st.nextToken());
		}
		
		// logic
		for (int idx = 0; idx < N; idx++) {
			dragonCurve(idx);
		}
		
		checkSquare();
		
		System.out.println(squareCnt);
	}
	
	private static void checkSquare() {
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				if (map[y][x] && map[y][x+1] && map[y+1][x] && map[y+1][x+1]) ++squareCnt;
			}
		}
	}

	private static void dragonCurve(int idx) {
		int x = curve[idx][0];
		int y = curve[idx][1];
		int d = curve[idx][2];
		int g = curve[idx][3];
		
		dirList.clear();
		dirList.add(d);
		
		// 세대만큼 체크하기
		map[y][x] = true;
		
		int ny = y + dy[d];
		int nx = x + dx[d];
		map[ny][nx] = true;
		
		x = nx;
		y = ny;
		for (int curG = 1; curG <= g; curG++) {
			
			// 크기만큼 증가
			int listSize = dirList.size();
			for (int i = listSize-1; i >= 0; i--) {
				int curDir = dirList.get(i);
				
				int nDir = (curDir + 1) % 4;
				dirList.add(nDir);
				
				nx = x + dx[nDir];
				ny = y + dy[nDir];
				
				map[ny][nx] = true;
				
				x = nx;
				y = ny;
			}
		}
	}

}
