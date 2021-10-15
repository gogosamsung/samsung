dx = [1, 0, -1, 0]
dy = [0, -1, 0, 1]

n = int(input())
data = [[0] * 101 for _ in range(101)]
for _ in range(n):
	x, y, d, g = map(int, input().split())
	data[x][y] = 1
	dirs = [d]
	for _ in range(g):
		temp = []
		for i in range(len(dirs)):
			temp.append((dirs[-i - 1] + 1) % 4)
		dirs.extend(temp)

	for dir in dirs:
		nx, ny = x + dx[dir], y + dy[dir]
		data[nx][ny] = 1
		x, y = nx, ny

result = 0
for row in range(100):
    for col in range(100):
        if data[row][col] and data[row+1][col] and data[row][col+1] and data[row+1][col+1]:
            result += 1
else:
    print(result)
