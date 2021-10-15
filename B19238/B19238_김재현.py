from collections import deque
from copy import deepcopy


def merge(x: int, y: int, dx: int, dy: int) -> None:  # row index, column index, y방향, x방향
    """점수를 합치는 함수
    Args:
        x (int): 현재 위치 x
        y (int): 현재 위치 y
        dx (int): 현재 가고자 하는 방향
        dy (int): 현재 가고자 하는 방향
    """
    while q:
        block = q.popleft()
        if not data[x][y]:
            data[x][y] = block
        elif data[x][y] == block:
            data[x][y] = block * 2
            x, y = x + dx, y + dy
        else:
            x, y = x + dx, y + dy
            data[x][y] = block


def move(k: int) -> None:
    """방향을 기준으로 큐에 추가한다.

    Args:
        k (int): 0.1.2.3 은 방향을 나타낸다.
    """
    if k == 0:
        for col in range(n):
            for row in range(n):
                if data[row][col]:
                    q.append(data[row][col])
                    data[row][col] = 0
            merge(0, col, 1, 0)
    elif k == 1:
        for col in range(n):
            for row in range(n - 1, -1, -1):
                if data[row][col]:
                    q.append(data[row][col])
                    data[row][col] = 0
            merge(n - 1, col, -1, 0)
    elif k == 2:
        for row in range(n):
            for col in range(n):
                if data[row][col]:
                    q.append(data[row][col])
                    data[row][col] = 0
            merge(row, 0, 0, 1)
    elif k == 3:
        for row in range(n):
            for col in range(n - 1, -1, -1):
                if data[row][col]:
                    q.append(data[row][col])
                    data[row][col] = 0
            merge(row, n - 1, 0, -1)


def go(cnt: int) -> None:
    global data, answer
    if cnt == 5:
        for i in range(n):
            answer = max(answer, max(data[i]))
        return

    copy = deepcopy(data)
    for i in range(4):
        move(i)
        go(cnt+1)
        data = deepcopy(copy)


n = int(input())
data = [list(map(int, input().split())) for _ in range(n)]
answer = 0
q = deque()

go(0)
print(answer)
