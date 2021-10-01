package algo2109_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 상도 - NxN 크기의 땅 구매 1x1 - rxc
 * 
 * 양분을 조사하는 로봇 S2D2 1x1 크기의 칸에 들어있는 양분을 조사
 * 
 * 가장 처음 양분 - 모든 칸에 5만큼 들어있다.
 * 
 * 나무재테크 - 작은 묘목을 구매해 어느정도 키운 후 팔아서 수익을 얻는 재테크 "M개"의 나무를 구매해 땅에 심음 **같은 1x1 크기의
 * 칸에 여러 개의 나무가 심어져 있을 수도 있다.
 * 
 * 사계절 봄 1. 자신의 나이만큼 양분을 먹음 -> 나무가 있는 1x1 크기의 칸에 있는 양분만 먹을 수 있음 => 하나의 칸에 여러 개의
 * 나무가 있다면, 나무가 어린 나무부터 양분을 먹는다. => 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무 : 양분 먹지
 * 못하고 즉시 죽음 2. 나이가 1증가
 * 
 * 여름 1. 봄에 죽은 나무가 양분으로 변하게 된다. 2. 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다.
 * 소수점 아래는 버린다.
 * 
 * 가을 1. 나무가 번식한다. 번식하는 나무는 나이가 5의 배수 -> 인접한 8개의 칸에 나이가 1인 나무가 생긴다. -> (r,c)와
 * 인접한 칸은 8칸 => 벗어나는 칸에는 나무가 생기지 않는다.
 * 
 * 겨울 1. S2D2가 땅을 돌아다니면서 땅에 양분을 추가. 각 칸에 추가되는 양분의 양 : A[r][c]이고, 입력으로 주어진다.
 * 
 * 구해야할 것: K년이 지난 후 상도의 땅에 살아있는 나무의 개수를 구하는 프로그램
 * 
 * 입력 N, M, K
 * 
 * 둘째 줄 N개의 줄에 A배열의 값이 주어진다. r번째 줄의 c번째 값은 A[r][c]
 * 
 * M개의 줄에 상도가 심은 나무의 정보를 나타내는 세 정수 x,y,z x,y : 나무의 위치 / z: 나무의 나이
 * 
 * 1<=N<=10 1<=M<=100 1<=K<=1000
 * 
 * 
 *
 */
class Tree implements Comparable<Tree> {
	int r;
	int c;
	int age;

	public Tree(int r, int c, int age) {
		super();
		this.r = r;
		this.c = c;
		this.age = age;
	}

	@Override
	public int compareTo(Tree t) {
		return this.age - t.age;
	}
}

public class BOJ_Gold4_16235_나무재테크 {

	static int N, M, K, A[][], ground[][];
	static List<Tree> AliveTrees;
	static Stack<Tree> DeathTrees = new Stack<>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		A = new int[N + 1][N + 1];
		ground = new int[N + 1][N + 1];
		AliveTrees = new ArrayList<>();
		// ground 초기 양분 5로 초기화
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				ground[i][j] = 5;
			}
		}

		// A 입력받기
		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				A[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		// M개의 나무 입력받기
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());

			AliveTrees.add(new Tree(r, c, age));
		}

		Collections.sort(AliveTrees);

		for (int i = 0; i < K; i++) {
			Spring();
			Summer();
			Autumn();
			Winter();
		}

		System.out.println(AliveTrees.size());
	}

	private static void Winter() {
//		겨울에는 S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				ground[r][c] += A[r][c];
			}
		}
	}

	static int dr[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static int dc[] = { -1, 0, 1, -1, 1, -1, 0, 1 };

	private static void Autumn() {
//		 * 1. 나무가 번식한다. 번식하는 나무는 나이가 5의 배수
//		 * -> 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
//		 * -> (r,c)와 인접한 칸은 8칸 => 벗어나는 칸에는 나무가 생기지 않는다.
		Queue<Tree> treesToAdd = new LinkedList<>();
		for (Tree tree : AliveTrees) {
			if (tree.age % 5 == 0) {
				for (int dir = 0; dir < 8; dir++) {
					int nr = tree.r + dr[dir];
					int nc = tree.c + dc[dir];

					if (!check(nr, nc))
						continue;
					treesToAdd.add(new Tree(nr, nc, 1));
				}
			}
		}
		
		List<Tree> combined = new ArrayList<>();
		while(!treesToAdd.isEmpty()) {
			combined.add(treesToAdd.poll());
		}
		combined.addAll(AliveTrees);
		AliveTrees = combined;
	}

	private static boolean check(int r, int c) {
		return r >= 1 && r <= N && c >= 1 && c <= N;
	}

	private static void Summer() {
//		 1. 봄에 죽은 나무가 양분으로 변하게 된다.
//		 * 2. 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다. 소수점 아래는 버린다.
		while (!DeathTrees.isEmpty()) {
			Tree tree = DeathTrees.pop();
			ground[tree.r][tree.c] += tree.age / 2;
		}
	}

	private static void Spring() {
//		* 1. 자신의 나이만큼 양분을 먹음 -> 나무가 있는 1x1 크기의 칸에 있는 양분만 먹을 수 있음
//		 * 	=> 하나의 칸에 여러 개의 나무가 있다면, 나무가 어린 나무부터 양분을 먹는다.
//		 * 	=> 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무 : 양분 먹지 못하고 즉시 죽음
		int idx = 0;
		Stack<Integer> removeStack = new Stack<>();
		for (Tree tree : AliveTrees) {
			if (tree.age <= ground[tree.r][tree.c]) {
				ground[tree.r][tree.c] -= tree.age;
				++tree.age;
			} else {
				removeStack.push(idx);
				DeathTrees.push(tree);
			}
			++idx;
		}
		
		while (!removeStack.isEmpty()) {
			AliveTrees.remove((int) removeStack.pop());
		}
	}
}
