<h1> Social-Network-Platform </h1> 

Social-Network-Platform은 Java와 JDBC를 활용해 제작된 소셜 네트워크 플랫폼입니다. </br>
사용자 관리, 피드 관리, 댓글 관리, 친구 관계 관리 등 소셜 네트워크의 핵심 기능을 구현했습니다.


<h3>주요 기능</h3>
1. 사용자 관리 </br>
2. 피드 관리 </br>
3. 댓글 관리 </br>
4. 친구 관계 관리


<h3>개념적 설계 진행</h3>
<img width="596" alt="KakaoTalk_Photo_2024-12-20-15-38-36" src="https://github.com/user-attachments/assets/b31938e0-bad9-4677-b7a6-66f8ab80566d" />
<img width="596" alt="KakaoTalk_Photo_2024-12-20-15-44-12" src="https://github.com/user-attachments/assets/f20a480e-64d6-49c1-bb89-5cb2eb7b14d2" />

<개체-관계 다이어그램(ERD)> </br>
<img width="596" src = "https://github.com/user-attachments/assets/2941bf91-1a9a-418a-97cf-97f17ac26e73" />

</br>
</br>


<h3>데이터베이스 스키마</h3>

## 사용자 테이블 (User)

| 속성 이름 (국문) | 속성 이름 (영문) | 데이터 타입 | 널 허용 여부 | 기본값 | 기본키 | 외래키 | 제약조건 |
|------------------|------------------|-------------|--------------|--------|--------|--------|----------|
| 사용자 번호      | user_id          | VARCHAR(20) | N            |        | PK     |        |          |
| 이름             | user_name        | VARCHAR(20) | N            |        |        |        |          |
| 이메일           | user_email       | VARCHAR(20) | N            |        |        |        |          |
| 성별             | sex              | VARCHAR(10) | Y            |        |        |        |          |
| 연락처           | phonenumber      | VARCHAR(20) | Y            |        |        |        |          |

---

## 게시물 테이블 (Feed)

| 속성 이름 (국문) | 속성 이름 (영문) | 데이터 타입 | 널 허용 여부 | 기본값 | 기본키 | 외래키 | 제약조건 |
|------------------|------------------|-------------|--------------|--------|--------|--------|----------|
| 게시물 번호      | feed_id          | INTEGER     | N            |        | PK     |        |          |
| 내용             | feed_detail      | VARCHAR(10) | N            |        |        |        |          |
| 작성 시간        | timestamp        | INTEGER     | N            | 0 이상 |        |        |          |
| 사용자 번호      | user_id          | VARCHAR(20) | N            |        | FK     |        |          |

---

## 댓글 테이블 (Comment)

| 속성 이름 (국문) | 속성 이름 (영문) | 데이터 타입 | 널 허용 여부 | 기본값 | 기본키 | 외래키 | 제약조건 |
|------------------|------------------|-------------|--------------|--------|--------|--------|----------|
| 댓글 번호        | comment_id       | INTEGER     | N            |        | PK     |        |          |
| 댓글 내용        | comment_detail   | VARCHAR(20) | N            |        |        |        |          |
| 작성 시간        | timestamp        | VARCHAR(10) | N            |        |        |        |          |
| 게시물 번호      | feed_id          | INTEGER     | N            | 0 이상 |        | FK     |          |
| 사용자 번호      | user_id          | VARCHAR(20) | N            |        | FK     |          |

---

## 친구 테이블 (Friend)

| 속성 이름 (국문) | 속성 이름 (영문) | 데이터 타입 | 널 허용 여부 | 기본값 | 기본키 | 외래키 | 제약조건 |
|------------------|------------------|-------------|--------------|--------|--------|--------|----------|
| 사용자 번호      | user_id          | VARCHAR(20) | N            |        |        | FK     |          |
| 친구 번호        | friend_id        | VARCHAR(20) | N            |        |        | FK     |          |



</br>
</br>

<img width="580" alt="KakaoTalk_Photo_2024-12-20-15-51-12" src="https://github.com/user-attachments/assets/2c2ac53d-46f7-488d-babe-db89e9c56ddc" />
