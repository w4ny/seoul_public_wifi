# seoul_public_wifi
- [ZB] 내 위치 기반 공공 와이파이 정보를 제공하는 웹서비스 개발
- 서울시 공공 데이터 API를 하여 내 위치 및 입력 위치(위도, 경도)에서 가까운 와이파이 정보를 제공해주는 웹서비스 개발 프로젝트

## 💻 기술 스택
- 개발언어 : Java (JDK 1.8)
- Build : Maven
- Database : SQLite3
- Server : Tomcat 9.0.96
- Web : CSS, HTML5, JSP
- Library : Lombok, Okhttp3, Gson
- ERD : ERDCloud

## 📌 기능 사항
1. OPEN API를 활용하여 서울시의 모든 공공 와이파이 정보를 가져옵니다.
2. 사용자가 입력한 위치 좌표를 기반으로 주변에 있는 공공 와이파이 정보 20개를 보여줍니다.
3. 사용자가 입력한 위치 정보와 조회한 시점을 기준으로 DB에 정보를 저장하고, 저장된 정보를 조회할 수 있습니다.
4. 특정 공공 와이파이의 상세 정보를 제공합니다.
5. 사용자는 북마크 그룹을 생성하고, 그룹 목록을 확인하며 그룹을 수정하거나 삭제할 수 있습니다.
6. 사용자는 공공 와이파이 정보를 북마크로 추가하거나 삭제할 수 있습니다.

## 📍 ERD
![WIFI-ERD (2)](https://github.com/user-attachments/assets/dc4d03f4-61da-4706-a545-c9512fb02de5)

## 📍 영상
https://www.youtube.com/watch?v=KLvpE_Ii9Eg
[![video Label](http://img.youtube.com/vi/KLvpE_Ii9Eg/0.jpg)](https://youtube.be/KLvpE_Ii9Eg)

