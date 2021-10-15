from collections import deque

n, m, k = map(int, input().split())

s2d2 = [list(map(int, input().split())) for _ in range(n)]

nutirition = [[5]*n for _ in range(n)]
data = [[deque() for _ in range(n)] for _ in range(n)]

for _ in range(m):
    a, b, c = map(int, input().split())
    a -= 1
    b -= 1
    data[a][b].append(c)

dx = [-1, -1, -1, 0, 0, 1, 1, 1]
dy = [-1, 0, 1, -1, 1, -1, 0, 1]


def spring_summer():
    for row in range(n):
        for col in range(n):
            temp = deque()
            die = 0
            for ele in sorted(data[row][col]):
                if nutirition[row][col] - ele >= 0:
                    nutirition[row][col] -= ele
                    temp.append(ele+1)
                else:
                    die += ele//2
            data[row][col] = temp
            nutirition[row][col] += die


def fall_winter():
    for row in range(n):
        for col in range(n):
            for ele in data[row][col]:
                if ele % 5 == 0:
                    for dir in range(8):
                        nx, ny = row + dx[dir], col + dy[dir]
                        if not (0 <= nx < n and 0 <= ny < n):
                            continue
                        data[nx][ny].appendleft(1)
            nutirition[row][col] += s2d2[row][col]


while k:
    spring_summer()
    fall_winter()
    k -= 1

answer = 0
for row in range(n):
    for col in range(n):
        answer += len(data[row][col])
print(answer)
