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
  -  다이어리 시스템(오늘멍)
  -  인프라 구축
- 강희준
  -  추천 시스템 
- 배혜원
  - 후기 시스템
  - 다이어리 시스템(오늘멍)
  - 인프라 구축
- 정희진
  - 회원 시스템
  - 반려견 정보 관리 시스템
  - 즐겨찾기 시스템 

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


### 📋 Git 브랜치 전략
> Github-Flow
> 기본적으로 Github Flow를 따라 개발 프로세스를 진행한다. </br>
> 이는 기능별 브랜치를 생성하고, 코드 리뷰 후 develop 브랜치에 병합하는 방식을 의미한다.

### 1. 깃 컨벤션
  <kbd>
    <img width="400" height="300" src="https://github.com/user-attachments/assets/f12fd40e-4dac-47c1-9347-4631c03c7ead" alt="nolmung_gitflow" style="border:1px solid black;">
  </kbd>

### 2. 브랜치 명명 규칙
- 이슈 생성 후 타입/SV-jira 티켓 넘버 로 브랜치를 생성한다.  
  예) `feat/SV-1`

### 3. 커밋 메시지 규칙
- 브랜치를 로컬에 받아 개발한다.  
- 구현됨에 따라 자주 커밋한다. 한번에 모아서 커밋하지 않는다.  
- 커밋 메시지는 지정된 컨벤션에 따른다.

### 4. 코드 리뷰 및 PR 관리
- PR을 생성한다. 이때 PR없이 절대 develop 브랜치에 merge하지 않는다.  
- 지정된 template을 이용해 구현한다. 이때 PR 제목은 issue와 같은 형식으로 작성한다.  
- PR은 커밋 메시지와 마찬가지로 여러 업무를 모아서 보내지말고 자주 보내 conflict를 줄여야 한다.  
- 가능한 팀원은 코드 리뷰를 해주고, 1인 이상 approve하면 본인이 merge하여 메인 브랜치에 푸쉬한다.
  
### 🗓️ 추진 일정
  <kbd>
      <img width="600" height="200" src="https://github.com/user-attachments/assets/307aa836-668e-48bb-8a39-266ec626a6e1" alt="nolmung_schedule" style="border:1px solid black;">
  </kbd>

- 현재 진행 사항 (5주차)
  - 프론트 컴포넌트 (완료)
  - API 연동 (완료)
  - QA (진행 예정)
    
<!--### 🤔 고민한 이야기-->
