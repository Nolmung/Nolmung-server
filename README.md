# Nolmung-server

### 💡 개요
> 강아지 동반 가능 시설 공유 플랫폼 ‘놀멍(Nolmung)’은 반려인들이 반려견과 함께 방문할 수 있는 장소를 쉽게 탐색하고, </br>
> 방문 후 ‘오늘멍’이라는 일기를 통해 소중한 추억을 기록하고 회상함으로써 반려견과의 유대감을 더욱 강화한다. </br>
> 또한, 다른 사용자들과 오늘멍을 공유하여 다양한 반려생활을 지원한다.

### 🙌🏻 목적
- 반려견 동반 가능한 장소 제공
- 반려견과의 활동 활성화
- 추억 기록
  
<br />

## 🔧 기능 소개 
**1. 회원 등록 및 관리 시스템** : 소셜 로그인 및 회원가입

**2. 반려견 정보 관리 시스템** : 프로필 등록, 조회, 수정 및 삭제

**3. 장소 검색 시스템** : 지도에서 장소 검색, 키워드로 장소 검색, 장소 검색 필터링

**4. 즐겨찾기 시스템** : 즐겨찾기 등록, 조회 및 삭제

**5. 다이어리 시스템(오늘멍)** : 오늘멍 등록, 조회, 수정 및 삭제

**6. 후기 등록 시스템** : 후기 등록, 조회 및 삭제

**7. 추천 시스템** : 위치정보 기반 추천, 즐겨찾기 순 추천, 견종별 입장 가능 시설 추천, 개인 맞춤형 추천

<br />

## 🛠️ 시스템 아키텍쳐

#### 개발
<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/8d29a75d-bc22-4c70-ab9b-312520342607" alt="nolmung_architecture" style="border:1px solid black;">
</kbd>

#### 운영
<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/23bbac49-e246-4a82-b88d-d5529e5979f9" alt="nolmung_architecture" style="border:1px solid black;">
</kbd>

<br />
<br />

## 📌 ERD

<kbd>
<img width="600" height="300" src="https://github.com/user-attachments/assets/1e9c0c56-0ee0-4a69-9566-d00def8160f5" alt="nolmung_erd" style="border:1px solid black;">
</kbd>
<p>
  🔗 <a href="https://www.erdcloud.com/d/i8uWGycunfcGahQYE" rel="nofollow">ERDCloud</a>  
</p>

<br />

## 📋 Conventions

#### 1. Git branch 전략
**Github-Flow** <br />
기본적으로 Github Flow를 따라 개발 프로세스를 진행한다. <br />
이는 기능별 브랜치를 생성하고, 코드 리뷰 후 develop 브랜치에 병합하는 방식을 의미한다.<br />

#### 2. Commit Message Conventions
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

#### 3. Branch Conventions
 - 지라 이슈 생성 후 '타입/SV-jira 티켓 넘버'로 브랜치를 생성한다.  
  예) `feat/SV-1`
 - 구현됨에 따라 자주 커밋하고, 한번에 몰아 커밋하지 않는다.
 - 커밋 메시지는 지정된 컨벤션에 따른다.

#### 4. Code Review & PR Conventions 
- PR은 지정된 template을 이용해 구현한다. 이때 PR 제목은 issue와 같은 형식으로 작성한다.
- PR은 커밋 메시지와 마찬가지로 여러 업무를 모아서 보내지 말고 자주 보내 conflict를 줄여야한다.
- 팀원 중 1인 이상 코드 리뷰를 해주고, approve하면 본인이 merge하여 develop 브랜치에 푸쉬한다.
- develop 브랜치에서 main 브랜치는 모든 팀원의 동의하에 merge 한다.

<br />

## 🗓️ 추진 일정

<kbd>
<img width="600" height="300" alt="스크린샷 2024-12-15 오후 3 34 05" src="https://github.com/user-attachments/assets/9e946772-27e8-49a1-bec2-6c8e813e0deb" style="border:1px solid black;" />
</kbd>

<br />
<br />

## 👥 팀원 및 역할 소개

<table>
  <tr align="center">
    <td>강희준</td>
    <td>고경남(팀장)</td>
    <td>배혜원</td>
    <td>정희진</td>
  </tr>
  <tr>
     <td align="center">
        <a href="https://github.com/orgs/Nolmung/people/dhfkdlsj">
          <img src="https://avatars.githubusercontent.com/u/105478203?v=4" width="150px" alt="강희준"/><br />
        </a>
     </td>
     <td align="center">
        <a href="https://github.com/rhrudska987">
          <img src="https://avatars.githubusercontent.com/u/59828706?v=4" width="150px" alt="고경남"/><br />
        </a>
     </td>
     <td align="center">
        <a href="https://github.com/hyewonbae">
          <img src="https://avatars.githubusercontent.com/u/43161096?v=4" width="150px" alt="배혜원"/><br />
        </a>
     </td>
     <td align="center">
        <a href="https://github.com/JHJ08">
          <img src="https://avatars.githubusercontent.com/u/161445093?v=4" width="150px" alt="정희진"/><br />
        </a>
     </td>
  </tr>
  <tr>
     <td align="center">
        <p> 테크리더 <br /> 추천시스템 </p>
     </td>
     <td align="center">
        <p> 장소 검색 시스템 <br />  즐겨찾기 시스템 <br /> 인프라 </p>
     </td>
     <td align="center">
        <p> 후기 시스템 <br /> 다이어리 시스템 <br /> 인프라 </p>
     </td>
     <td align="center">
        <p> 회원 시스템(소셜 로그인) <br /> 반려견 정보 관리 시스템 </p>
     </td>
  </tr>
</table>

