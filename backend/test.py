#-*- coding: utf-8 -*-

import pymysql

conn = pymysql.connect(host='localhost', user='root', password='dlwngid1',
                       db='empty_room', charset='utf8')

curs = conn.cursor()

sql = """INSERT INTO empty_room (mon) VALUES (%s)"""

curs.execute(sql, ( 'hello world'))
conn.commit()

conn.close()
