#-*- coding: utf-8 -*-

##웹 크롤러를 이용한 소스코드 스크래핑 Selenium 을 이용함
##Selenium 은 웹 테스팅 모듈

from selenium import webdriver
from bs4 import BeautifulSoup

room_list = ['EB0102', 'EB0103', 'EB0104', 'EB0105', 'EB0106', 'EB0225', 'EB0401', 'EB0402', 'EB0418', 'EB0420', 'SB0101', 'SB0102', 'SB0103', 'SB0104', 'SB0107', 'SB0108', 'SB0109', 'SB0110', 'SB0111', 'SB0112', 'SB0114', 'SB0115', 'SB0116', 'SB0117', 'SB0201', 'SB0202', 'SB0203', 'SB0206', 'SB0208', 'SB0211', 'SB0218', 'SB0219', 'SB0220', 'SB0221', 'SB0222', 'SB0232', 'SB0233', 'SB0234', 'SB0235', 'SB0301', 'SB0304', 'SB0306', 'SB0308', 'SB0311', 'SB0326', 'SB0327', 'SB0332', 'SB0407', 'SB0409', 'SB0412', 'SB0415', 'SB0418', 'SB0401', 'SB0403', 'SB0405', 'SB0406', 'SB0422', 'SB0424', 'SB0432', 'NB101', 'NB102', 'NB104', 'NB105', 'NB107', 'NB108', 'NB201', 'NB202', 'NB203', 'NB204', 'NB205', 'NB206', 'NB207', 'NB208', 'NB209', 'NB210', 'NB211', 'NB213', 'NB214', 'NB309', 'NB310']

driver = webdriver.Chrome () ##selenium 사용을 위한 crhome driver 연결

driver.implicitly_wait(3) ##암묵적으로 3초 기다림 (서버 안정화를 위함)

driver.get('https://www.kau.ac.kr/page/login.jsp?ppage=&target_page=act_Portal_Check.jsp@chk1-1') ##로그인 페이지에 접속

driver.find_element_by_name('p_id').send_keys('2015125061') ##소스코드중에 p_id 를 찾아서 입력
driver.find_element_by_name('p_pwd').send_keys('01040562889z!') ##소스코드중에 P_pwd를 찾아서 입력
driver.find_element_by_xpath('/html/body/div[2]/div[2]/table[2]/tbody/tr[3]/td/form/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr/td[2]/a/img').click() ##login 버튼 클릭

for room in room_list :
    url = 'https://portal.kau.ac.kr/sugang/LectSpaceRoomSchView.jsp?year=2017&hakgi=10&room_name=102&room_no=' + room ##url 생성 마지막에 i 는 강의실 코드
    driver.get(url) ##url 에 접속
    html = driver.page_source ##html 에 url에 있는 모든 소스코드를 담음
    fname = "room/" + room + ".txt"
    f = open(fname, "w", encoding="utf-8") ## 강의실코드.txt 파일에 소스코드를 저장
    f.write(html)
    f.close()
