from collections import deque
from copy import deepcopy

n, m = map(int, input().split())

virus = []
zeros = []
data = []

for row in range(n):
    line = list(map(int, input().split()))
    data.append(line)
    for col in range(m):
        if data[row][col] == 2:
            virus.append([row, col])
        elif data[row][col] == 0:
            zeros.append([row, col])


def calc() -> int:
    """BFS 를 통해 area 를 찾는다

    Returns:
        int: 비어 있는 칸은 찾아서 반환한다.
    """
    global data
    q = deque(virus)
    dx = [0, 0, 1, -1]
    dy = [1, -1, 0, 0]
    area = len(zeros)-3
    revoke = []

    while q:
        x, y = q.popleft()
        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]

            if not (0 <= nx < n and 0 <= ny < m):
                continue
            if data[nx][ny] == 0:
                q.append([nx, ny])
                data[nx][ny] = 2
                area -= 1
                revoke.append((nx, ny))

    for row, col in revoke:
        data[row][col] = 0

    return area


def go(index: int, cnt: int) -> None:
    """조합을 통해서 벽 3개를 세운다.

    Args:
        index (int): 추가 해야 하는 벽의 위치
        cnt (int): 현재 세운 벽의 개수
    """
    global sel, result, data
    if cnt == 3:
        for row, col in sel:
            data[row][col] = 1
        area = calc()
        result = max(area, result)
        for row, col in sel:
            data[row][col] = 0
        return

    if index >= len(zeros):
        return

    sel.append(zeros[index])
    go(index + 1, cnt + 1)
    sel.pop()

    go(index + 1, cnt)


result = -987654321
sel = []
go(0, 0)
print(result)
