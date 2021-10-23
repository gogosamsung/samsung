package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
10/10 11:44 시작 - 

주사위 윷놀이
- 처음에 시작 칸에 말 4개가 있다.

- 말은 게임판에 그려진 화살표의 방향대로만 이동할 수 있다.
- 말이 파란색 칸에서 이동을 시작하면 파란색 화살표를 타야 하고, 이동하는 도중이거나 파란색이 아닌 칸에서 이동을 시작하면 빨간색 화살표를 타야 한다.
- 말이 도착 칸으로 이동하면, 주사위에 나온 수와 관계 없이 이동을 마친다.

- 게임은 10개의 턴으로 이루어진다. 매 턴마다 1~5까지 한 면에 하나씩 적혀있는 5면체 주사위를 굴리고, 도착 칸에 있지 않은 말을 하나 골라 주사위에 나온 수만큼 이동시킨다.
- 말이 이동을 마치는 칸에 다른 말이 있으면 그 말은 고를 수 없다. 단, 이동을 마치는 칸이 도착 칸이면 고를 수 있다.
- 말이 이동을 마칠 때마다 칸에 적혀있는 수가 점수에 추가된다.

구해야할 것 : 점수의 최댓값!

 */
class Node {
	int score;
	Node nextRed;
	Node nextBlue;
	int curHorse;
	boolean isFinish;
	boolean isEmpty;
	public Node(int score) {
		super();
		this.score = score;
		this.nextRed = null;
		this.nextBlue = null;
		this.curHorse = 0;
		this.isFinish = false;
		this.isEmpty = true;
	}
	
}
public class BOJ_Gold2_17825_주사위윷놀이 {

	static Node startNode;
	static Node[] horse;
	static int dice[], order[], maxScore = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		dice = new int[10];
		order = new int[10];
		horse = new Node[5];
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		for (int idx = 0; idx < 10; idx++) {
			dice[idx] = Integer.parseInt(st.nextToken());
		}
		
		setNode();
		
		
		permutation(0);
		
		System.out.println(maxScore);
	}

	private static void permutation(int idx) {
		if (idx == 10) {
			moveHorse();
			
			return;
		}
		
		for (int num = 1; num <= 4; num++) {
			order[idx] = num;
			permutation(idx+1);
		}
		
	}

	private static void moveHorse() {
		Arrays.fill(horse, startNode);
		
		int score = 0;
		for (int idx = 0; idx < 10; idx++) {
			Node curNode = horse[order[idx]];
			
			
			curNode.isEmpty = true;
			
			for (int cnt = 1; cnt <= dice[idx]; cnt++) {
				if (cnt == 1 && curNode.nextBlue != null) {
					curNode = curNode.nextBlue;
				} else {
					curNode = curNode.nextRed;
				}
			}
			
			horse[order[idx]] = curNode;
			
			if (!curNode.isEmpty && !curNode.isFinish) {
				for (int i = 1; i <= 4; i++) { // ## 중요
					horse[i].isEmpty = true;
				}
				return;
			}
			
			score += curNode.score;
			
			curNode.isEmpty = false;
		}
		
		for (int i = 1; i <= 4; i++) {
			horse[i].isEmpty = true;
		}
		
		maxScore = Math.max(maxScore, score);
	}

	private static void setNode() {
		
		startNode = new Node(0); // 시작
		
		// 다시!!
		Node temp = startNode;
		
		for (int i = 2; i <= 40; i+=2) {
			Node nextNode = new Node(i);
			temp.nextRed = nextNode;
			temp = temp.nextRed;
		}
		
		Node end = new Node(0);
		
		end.isFinish = true;
		end.nextRed = end;
		
		temp.nextRed = end;
		
		Node crossRoad = new Node(25);
		temp = crossRoad;
		
		for (int score = 30; score <= 35; score+=5) {
			Node nextNode = new Node(score);
			temp.nextRed = nextNode;
			temp = temp.nextRed;
		}
		
		temp.nextRed = getNode(40);
		
		// 10 node
		Node tenNode = getNode(10);
		
		tenNode.nextBlue = new Node(13);
		tenNode = tenNode.nextBlue;
		for (int i = 16; i <= 19; i += 3) {
			Node nextNode = new Node(i);
			tenNode.nextRed = nextNode;
			tenNode = tenNode.nextRed;
		}
		
		tenNode.nextRed = crossRoad;
		
		// 20 node
		Node twentyNode = getNode(20);
		twentyNode.nextBlue = new Node(22);
		twentyNode = twentyNode.nextBlue;
		
		Node nextNode = new Node(24);
		twentyNode.nextRed = nextNode;
		twentyNode = twentyNode.nextRed;
		
		twentyNode.nextRed = crossRoad;
		
		// 30 node
		Node thirtyNode = getNode(30);
		thirtyNode.nextBlue = new Node(28);
		thirtyNode = thirtyNode.nextBlue;
		
		nextNode = new Node(27);
		thirtyNode.nextRed = nextNode;
		thirtyNode = thirtyNode.nextRed;
		
		nextNode = new Node(26);
		thirtyNode.nextRed = nextNode;
		thirtyNode = thirtyNode.nextRed;
		
		thirtyNode.nextRed = crossRoad;
	}
	
	public static Node getNode(int score) {
		Node temp = startNode;
		
		while (temp.nextRed != null) {
			if (temp.score == score) return temp;
			temp = temp.nextRed;
		}
		return temp;
	}

}
