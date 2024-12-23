<h1> Social-Network-Platform </h1> 

Social-Network-Platform은 Java와 JDBC를 활용해 제작된 소셜 네트워크 플랫폼입니다. </br>
사용자 관리, 피드 관리, 댓글 관리, 친구 관계 관리 등 소셜 네트워크의 핵심 기능을 구현했습니다.

</br>


## 주요 기능
1. 사용자 관리 </br>
2. 피드 관리 </br>
3. 댓글 관리 </br>
4. 친구 관계 관리
   
</br>

## 요구사항 정의
1.﻿﻿﻿ 사용자를 관리하기 위해 사용자 ID, 이름, 이메일, 연락처, 성별을 저장한다. </br>
2.﻿﻿﻿ 사용자간의 친구 관계를 관리하며, 여러명의 친구를 가질 수 있다.  </br>
3.﻿﻿﻿ 게시물을 관리하기 위해 게시물 ID,작성자 ID, 내용,작성 시간을 저장한다.  </br>
4.﻿﻿﻿ 각 게시물에 작성된 댓글을 관리하기 위해 댓글 ID, 게시물 ID,작성자 ID, 댓글내용, 작성 시간을 저장한다. </br>
5.﻿﻿﻿ 사용자는 여러개의 게시물을 작성할 수 있다. </br>
6.﻿﻿﻿ 각 게시물에는 여러개의 댓글을 작성할 수 있다.

</br>
</br>

## 개념적 설계 진행

| 개체명 | 속성                               | 키      | 다중값속성 | 유도속성 | 비고 |
|--------|------------------------------------|---------|------------|----------|------|
| 사용자 | 사용자 번호, 이름, 이메일, 연락처, 성별 | 사용자 번호 |            |          |      |
| 게시글 | 게시글 번호, 작성자 번호, 게시글 내용, 작성 시간 | 게시글 번호 |            |          |      |
| 댓글   | 댓글 번호, 게시글 번호, 작성자 번호, 댓글 내용, 작성 시간 | 댓글 번호  |            |          |      |

| 관계명     | 관계에 참여하는 개체           | 관계유형 | 속성          | 비고 |
|------------|------------------------------|----------|---------------|------|
| 게시글 작성 | 사용자(선택), 게시글(필수)    | 1:N      |               |      |
| 댓글 작성   | 게시글(선택), 댓글(필수)      | 1:N      |               |      |
| 친구       | 사용자(선택), 사용자(선택)    | N:M      | 사용자 번호, 친구 번호 |      |




<개체-관계 다이어그램(ERD)> </br>
<img width="596" src = "https://github.com/user-attachments/assets/2941bf91-1a9a-418a-97cf-97f17ac26e73" />

</br>
</br>


## 데이터베이스 스키마

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

---

## 기대 효과

<h3>효율적인 사용자 관리</h3>
사용자 정보와 친구 관계를 데이터베이스로 체계적으로 관리하여 데이터의 신뢰성을 높일 수 있습니다.

<h3>게시물과 댓글 관리의 간편화</h3>
사용자 생성 콘텐츠(게시물 및 댓글)를 효율적으로 저장 및 검색할 수 있어, 소셜 플랫폼의 성능을 향상시킵니다.

<h3>확장 가능성</h3>
현재 데이터 모델은 새로운 기능(예: 메시지, 그룹 등)을 쉽게 추가할 수 있도록 설계되었습니다.

---

## 구현 과정

<li>요구사항 분석 및 데이터베이스 설계</li>
</br>
<li>ERD 기반 테이블 작성 및 데이터 스키마 생성</li>
</br>
<li>JDBC를 활용한 CRUD 기능 구현</li>
</br>
<li>예제 데이터를 활용한 테스트 및 결과 확인</li>

</br>

---

<img width="580" alt="KakaoTalk_Photo_2024-12-20-15-51-12" src="https://github.com/user-attachments/assets/2c2ac53d-46f7-488d-babe-db89e9c56ddc" />

---

## 예제 데이터

<img width="641" alt="image" src="https://github.com/user-attachments/assets/de10b5ce-7fcf-4085-86ff-63c567a4812d" />


—

## 실행 결과

<h3>usersMain 실행 결과</h3>

<img width="993" alt="image" src="https://github.com/user-attachments/assets/0ce0748f-379a-4124-9fd1-b7c514f2f4d8" />

<h3>feedMain 실행 결과</h3>

<img width="817" alt="image" src="https://github.com/user-attachments/assets/0c8d588c-64f7-41b0-9de3-ef96844008f7" />


<h3>cimmentMain 실행 결과</h3>

<img width="1016" alt="image" src="https://github.com/user-attachments/assets/50f4351f-d689-4380-8cf4-a97527adde6e" />


<h3>friend 실행 결과</h3>

<img width="996" alt="image" src="https://github.com/user-attachments/assets/7617fe78-747d-483a-a3ca-dcd3641bd51c" />


---

## 프로젝트 참여자

@Boyeon-Shin </br>
@pingmong
