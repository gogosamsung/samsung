def find(x: int, y: int) -> int:
    """도형들의 경우에 수는 이미 정해져 있다.

    Args:
        x (int): x 좌표
        y (int): y 좌표

    Returns:
        int: 최댓값을 구한다.
    """
    value = 0
    for i in range(19):
        result = 0
        for j in range(4):
            nx, ny = x + tetromino[i][j][0], y + tetromino[i][j][1]
            if not (0 <= nx < n and 0 <= ny < m):
                break
            result += data[nx][ny]
        value = max(value, result)
    return value


tetromino = [
    [(0, 0), (0, 1), (1, 0), (1, 1)],  # ㅁ
    [(0, 0), (0, 1), (0, 2), (0, 3)],  # ㅡ
    [(0, 0), (1, 0), (2, 0), (3, 0)],  # ㅣ
    [(0, 0), (0, 1), (0, 2), (1, 0)],
    [(1, 0), (1, 1), (1, 2), (0, 2)],
    [(0, 0), (1, 0), (1, 1), (1, 2)],
    [(0, 0), (0, 1), (0, 2), (1, 2)],  # ㄴ
    [(0, 0), (1, 0), (2, 0), (2, 1)],
    [(2, 0), (2, 1), (1, 1), (0, 1)],
    [(0, 0), (0, 1), (1, 0), (2, 0)],
    [(0, 0), (0, 1), (1, 1), (2, 1)],  # ㄱ
    [(0, 0), (0, 1), (0, 2), (1, 1)],  # ㅜ
    [(1, 0), (1, 1), (1, 2), (0, 1)],  # ㅗ
    [(0, 0), (1, 0), (2, 0), (1, 1)],  # ㅏ
    [(1, 0), (0, 1), (1, 1), (2, 1)],  # ㅓ
    [(1, 0), (2, 0), (0, 1), (1, 1)],
    [(0, 0), (1, 0), (1, 1), (2, 1)],
    [(1, 0), (0, 1), (1, 1), (0, 2)],
    [(0, 0), (0, 1), (1, 1), (1, 2)]
]

n, m = map(int, input().split())
data = [list(map(int, input().split())) for _ in range(n)]

answer = 0
for i in range(n):
    for j in range(m):
        answer = max(answer, find(i, j))

print(answer)
