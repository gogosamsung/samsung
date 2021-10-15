from copy import deepcopy


def right(x: int, y: int, copy: list) -> None:
	"""
	오른쪽을 세는 함수 벽이 있으면 종료
	:param x:int
	:param y:int
	:param copy: list
	"""
	for col in range(y + 1, m):
		if copy[x][col] == 6:
			break
		if 1 <= copy[x][col] <= 5:
			continue
		copy[x][col] = -1


def left(x: int, y: int, copy: list) -> None:
	"""
	왼쪽을 세는 함수 벽이 있으면 종료
	:param x:
	:param y:
	:param copy:
	"""
	for col in range(y - 1, -1, -1):
		if copy[x][col] == 6:
			break
		if 1 <= copy[x][col] <= 5:
			continue
		copy[x][col] = -1


def down(x: int, y: int, copy: list) -> None:
	"""
	아래를 세는 함수 벽이 있으면 종료
	:param x:
	:param y:
	:param copy:
	"""
	for row in range(x + 1, n):
		if copy[row][y] == 6:
			break
		
		if 1 <= data[row][y] <= 5:
			continue
		copy[row][y] = -1


def up(x: int, y: int, copy: list) -> None:
	"""
	위를 세는 함수 벽이 있으면 종료
	:param x:
	:param y:
	:param copy:
	"""
	for row in range(x - 1, -1, -1):
		if copy[row][y] == 6:
			break
		if 1 <= copy[row][y] <= 5:
			continue
		copy[row][y] = -1


def check() -> list:
	"""
	각각의 cctv를 돌리면서 확인하는 함수
	:return: list
	"""
	copy = deepcopy(data)
	for cctv in cctvs:
		x, y, type, dir = cctv
		if type == 1:
			if dir == 0:
				right(x, y, copy)
			elif dir == 1:
				left(x, y, copy)
			elif dir == 2:
				down(x, y, copy)
			elif dir == 3:
				up(x, y, copy)
				
		elif type == 2:
			if dir == 0:
				right(x, y, copy)
				left(x, y, copy)
			elif dir == 1:
				up(x, y, copy)
				down(x, y, copy)
				
		elif type == 3:
			if dir == 0:
				up(x, y, copy)
				right(x, y, copy)
			elif dir == 1:
				right(x, y, copy)
				down(x, y, copy)
			elif dir == 2:
				down(x, y, copy)
				left(x, y, copy)
			elif dir == 3:
				left(x, y, copy)
				up(x, y, copy)
				
		elif type == 4:
			if dir == 0:
				left(x, y, copy)
				up(x, y, copy)
				right(x, y, copy)
			elif dir == 1:
				up(x, y, copy)
				right(x, y, copy)
				down(x, y, copy)
			elif dir == 2:
				right(x, y, copy)
				down(x, y, copy)
				left(x, y, copy)
			elif dir == 3:
				down(x, y, copy)
				left(x, y, copy)
				up(x, y, copy)
				
		elif type == 5 and dir == 0:
			right(x, y, copy)
			left(x, y, copy)
			up(x, y, copy)
			down(x, y, copy)
	
	return copy


def count_area(copy: list) -> int:
	"""
	사각지대를 카운팅하는 함수
	:param copy: list
	:return:int
	"""
	sum = 0
	for row in range(n):
		for col in range(m):
			if copy[row][col] == 0:
				sum += 1
	return sum


def go(cnt: int) -> None:
	"""
	바향을 지정하는 함수
	:param cnt:
	:return: None
	"""
	global answer
	if cnt == len(cctvs):
		copy = check()
		answer = min(answer, count_area(copy))
		return
	
	cctvs[cnt][3] = 0
	go(cnt + 1)
	
	cctvs[cnt][3] = 1
	go(cnt + 1)
	
	cctvs[cnt][3] = 2
	go(cnt + 1)
	
	cctvs[cnt][3] = 3
	go(cnt + 1)


answer = 987654321
n, m = map(int, input().split())

data = [list(map(int, input().split())) for _ in range(n)]

cctvs = []
for i in range(n):
	for j in range(m):
		if 1 <= data[i][j] <= 5:
			cctvs.append([i, j, data[i][j], -1])
            
go(0)
print(answer)
