package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
톱니바퀴 회전 -> 회전시킬 톱니바퀴와 회전시킬 방향을 결정해야 함
톱니바퀴가 회전할 때, 서로 맞닿은 극에 따라서 옆에 있는 톱니바퀴를 회전시킬 수도 있고, 회전시키지 않을 수도 있다. 
=> 톱니바퀴 A를 회전할 때, 그 옆에 있는 톱니바퀴 B와 서로 맞닿은 톱니의 극이 다르다면, B는 A가 회전한 방향과 반대방향으로 회전하게 된다. 

- 옆에 있는 극과 같으면 회전X
극이 다르면 반대로 회전
옆에있는 톱니바퀴가 회전하지 않으면 회전X

1시간 소요
 */
public class BOJ_Gold5_14891_톱니바퀴 {
	
	static ArrayList<Integer> circle[];
	static int direction[], K, round[][];
	static Queue<int[]> queue = new LinkedList<>(); // {idx, 방향}

	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		circle = new ArrayList[5];
		direction = new int[5];
		
		StringTokenizer st = null;
		for (int idx = 1; idx <= 4; idx++) {
			circle[idx] = new ArrayList<>();
			
			String top = in.readLine();
			for (int circleIdx = 0; circleIdx < 8; circleIdx++) {
				circle[idx].add(top.charAt(circleIdx) - '0'); // char to int
			}
		}
		
		K = Integer.parseInt(in.readLine());
		
		round = new int[K][2];
		
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			
			round[i][0] = Integer.parseInt(st.nextToken());
			round[i][1] = Integer.parseInt(st.nextToken());
		}
		
		for (int k = 0; k < K; k++) {
			// 1. 이번 턴에 돌아야 할 방향 update
			updateDirection(k);
			
			// 2. 방향 기반으로 톱니 update
			updateCircle();
		}
		
		System.out.println(sumScore());
	}

	private static int sumScore() {
		int score = 0;
		int sScoreRate[] = new int[]{0, 1, 2, 4, 8};
		for (int circleIdx = 1; circleIdx <= 4; circleIdx++) {
			if (circle[circleIdx].get(0) == 1) {
				score += sScoreRate[circleIdx];
			}
		}
		
		return score;
	}

	private static void updateCircle() {
		for (int circleIdx = 1; circleIdx <= 4; circleIdx++) {
			if (direction[circleIdx] == 1) {
				Integer removeTop = circle[circleIdx].remove(7);
				circle[circleIdx].add(0, removeTop);
			} else if (direction[circleIdx] == -1) {
				Integer removeTop = circle[circleIdx].remove(0);
				circle[circleIdx].add(removeTop); 
			}
		}
	}

	static int dx[] = {1,-1};
	private static void updateDirection(int k) {
		int startCircleIdx = round[k][0];
		int startDir = round[k][1];
		
		// direction 초기화
		for (int idx = 1; idx <= 4; idx++) {
			direction[idx] = -10; // 0: 회전x, 1: 시계, -1: 반시계
		}
		queue.clear();
		queue.offer(new int[] {startCircleIdx, startDir});
		direction[startCircleIdx] = startDir;
		
		while (!queue.isEmpty()) {
			int curCircleIdx = queue.peek()[0];
			int circleDir = queue.peek()[1];
			queue.poll();
			
			for (int dir = 0; dir < 2; dir++) {
				int nIdx = curCircleIdx + dx[dir];
				
				if (nIdx < 1 || nIdx > 4 || direction[nIdx] != -10) continue;
				
				// 원래 바퀴가 회전했는지 확인
				if (direction[curCircleIdx] == 0) {
					direction[nIdx] = 0;
					continue;
				}
				
				// 극 체크
				// 오른쪽 방향일 때
				if (dir == 0) {
					if (circle[nIdx].get(6) == circle[curCircleIdx].get(2)) {
						direction[nIdx] = 0; continue;
					} else {
						direction[nIdx] = -direction[curCircleIdx];
						queue.offer(new int[] {nIdx, direction[nIdx]});
					}
				} else {
					if (circle[nIdx].get(2) == circle[curCircleIdx].get(6)) {
						direction[nIdx] = 0; continue;
					} else {
						direction[nIdx] = -direction[curCircleIdx];
						queue.offer(new int[] {nIdx, direction[nIdx]});
					}
				}
			}
		}
	}
}
