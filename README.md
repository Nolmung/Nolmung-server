# Nolmung-server

### 💡 개요
> 강아지 동반 가능 시설 공유 플랫폼 ‘놀멍(Nolmung)’은 반려인들이 반려견과 함께 방문할 수 있는 장소를 쉽게 탐색하고, </br>
> 방문 후 ‘오늘멍’이라는 일기를 통해 소중한 추억을 기록하고 회상함으로써 반려견과의 유대감을 더욱 강화한다. </br>
> 또한, 다른 사용자들과 오늘멍을 공유하여 다양한 반려생활을 지원한다.

### 🙌🏻 목적
- 반려견 동반 가능한 장소 제공
- 반려견과의 활동 활성화
- 추억 기록

### 🙋🏻 기능 및 기능별 담당자
- 고경남(팀장)
  -  장소 검색 시스템
  -  다이어리 시스템(오늘멍) : 아호코라식 기법 적용
  -  즐겨찾기 시스템
  -  인프라 구축
- 강희준
  -  추천 시스템 
- 배혜원
  - 후기 시스템
  - 다이어리 시스템(오늘멍)
  - 인프라 구축
- 정희진
  - 회원 시스템 (소셜 로그인)
  - 반려견 정보 관리 시스템

### 🛠️ 시스템 아키텍쳐(개발)
<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/fe598b0f-16a8-43f0-9ac7-73ff3dff7c52" alt="nolmung_architecture" style="border:1px solid black;">
</kbd>

### 🛠️ 시스템 아키텍쳐(운영)
<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/81b46d91-97ac-4a2b-a0df-cbfbdd024cd4" alt="nolmung_architecture" style="border:1px solid black;">
</kbd>

### 📌 ERD
<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/530b465d-729f-4645-98fb-8a7906ece0cc" alt="nolmung_erd" style="border:1px solid black;">
</kbd>
<p>
  🔗 <a href="https://www.erdcloud.com/d/i8uWGycunfcGahQYE" rel="nofollow">ERDCloud</a>  
</p>


### 📋 Conventions

#### 1. Commit Message Conventions
<details>
<summary><b>Types</b></summary>
<div markdown="1">

  - `feat`: 새로운 기능  
  - `fix`: 버그 수정  
  - `refact`: 기능 변경 없이 코드 구조 개선  
  - `docs`: 문서 수정 (README 등)  
  - `test`: 테스트 코드 추가 또는 수정  
  - `env`: 환경 설정 관련  
  - `hotfix`: 긴급 수정  
  - `chore`: 그 외의 작은 수정들  
  - `deploy`: 운영 서버 배포  

</div>
</details>

#### 2. Branch Conventions
 - 지라 이슈 생성 후 '타입/SV-jira 티켓 넘버'로 브랜치를 생성한다.  
  예) `feat/SV-1`
 - 구현됨에 따라 자주 커밋하고, 한번에 몰아 커밋하지 않는다.
 - 커밋 메시지는 지정된 컨벤션에 따른다.

#### 3. Code Review & PR Conventions 
- PR은 지정된 template을 이용해 구현한다. 이때 PR 제목은 issue와 같은 형식으로 작성한다.
- PR은 커밋 메시지와 마찬가지로 여러 업무를 모아서 보내지 말고 자주 보내 conflict를 줄여야한다.
- 팀원 중 1인 이상 코드 리뷰를 해주고, approve하면 본인이 merge하여 develop 브랜치에 푸쉬한다.
- develop 브랜치에서 main 브랜치는 모든 팀원의 동의하에 merge 한다.

### 🗓️ 추진 일정
<img width="682" alt="스크린샷 2024-12-15 오후 3 34 05" src="https://github.com/user-attachments/assets/9e946772-27e8-49a1-bec2-6c8e813e0deb" />

<!--### 🤔 고민한 이야기-->
