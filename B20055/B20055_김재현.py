import sys
sys.stdin = open('input.txt')

n, k = map(int, input().split())

data = list(map(int, input().split()))
robot = [False] * n


time = 0

while True:
    time += 1
    data = data[-1:] + data[:-1]
    robot = robot[-1:] + robot[:-1]
    robot[n - 1] = 0

    for i in range(n - 2, -1, -1):
        if robot[i] and not robot[i + 1] and data[i + 1] >= 1:
            data[i + 1] -= 1
            robot[i + 1] = 1
            robot[i] = False

    robot[n - 1] = False

    if robot[0] == 0 and data[0] >= 1:
        data[0] -= 1
        robot[0] = True

    if data.count(0) >= k:
        print(time)
        break
