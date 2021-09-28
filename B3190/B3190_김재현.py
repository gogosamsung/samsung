from collections import deque

n = int(input())
k = int(input())

data = [[0] * n for _ in range(n)]

dx = [1, 0, -1, 0]
dy = [0, 1, 0, -1]

for _ in range(k):
    a, b = map(int, input().split())
    a -= 1
    b -= 1
    data[a][b] = 1
    
l = int(input())
commands = [input().split() for _ in range(l)]


def turn(snake: int, dir: str) -> int:
    if dir == 'L':
        return (snake + 1) % 4
    else:
        return (snake+3) % 4


time = 0
head = [0, 0, 1]
data[0][0] = 2
snake = deque([[0, 0, 1]])

while True:
    time += 1
    x, y, dir = head
    nx, ny = x+dx[dir], y+dy[dir]

    if not (0 <= nx < n and 0 <= ny < n):
        # 벽을 만나거나
        break
    elif data[nx][ny] == 2:
        # 자기 자신을 만나면
        break
    elif data[nx][ny] == 0:
        # 사과가 없을 때
        data[nx][ny] = 2
        snake.append([nx, ny, dir])
        row, col, temp_dir = snake.popleft()
        data[row][col] = 0
        head = [nx, ny, dir]
    else:
        # 사과가 있을 때
        data[nx][ny] = 2
        snake.append([nx, ny, dir])
        head = [nx, ny, dir]

    if commands and time == int(commands[0][0]):
        head[2] = turn(dir, commands[0][1])
        del commands[0]

print(time)
