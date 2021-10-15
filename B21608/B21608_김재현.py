n = int(input())
students = []
data = [[0]*n for _ in range(n)]
dic = {}
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]


students = [list(map(int, input().split())) for _ in range(n**2)]


def cnt(friends, x, y):
    count = 0
    cnt = 0
    for i in range(4):
        ny = x + dy[i]
        nx = y + dx[i]
        if not (0<=nx<n and 0<=ny<n):
            continue
        if data[ny][nx] == 0:
            count += 1
        if friends and data[ny][nx] in friends:
            cnt += 1
    return count, cnt


for s in students:

    friend = [s[i] for i in range(1, 5) if s[i] in dic]

    if friend:
        max_cnt = []
        for i in range(n):
            for j in range(n):
                if data[i][j] == 0:
                    result, f_cnt = cnt(friend, i, j)
                    if not max_cnt:
                        max_cnt = [f_cnt, result, i, j]
                    if f_cnt > max_cnt[0]:
                        max_cnt = [f_cnt, result, i, j]
                    elif f_cnt == max_cnt[0] and result > max_cnt[1]:
                        max_cnt = [f_cnt, result, i, j]

        data[max_cnt[2]][max_cnt[3]] = s[0]
        dic[s[0]] = s[1:]

    else:
        max_cnt = []
        for i in range(n):
            for j in range(n):
                if data[i][j] == 0:
                    result, f_cnt = cnt(friend, i, j)
                    if not max_cnt:
                        max_cnt = [result, i, j]
                    elif result > max_cnt[0]:
                        max_cnt = [result, i, j]

        data[max_cnt[1]][max_cnt[2]] = s[0]
        dic[s[0]] = s[1:]

point = 0
for i in range(n):
    for j in range(n):
        f_list = dic[data[i][j]]
        empty_val, f_cnt = cnt(f_list, i, j)
        if f_cnt == 0:
            point += 0
        elif f_cnt == 1:
            point += 1
        elif f_cnt == 2:
            point += 10
        elif f_cnt == 3:
            point += 100
        else:
            point += 1000

print(point)
