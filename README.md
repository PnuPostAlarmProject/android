# PPAP (Pnu Post Alarm Project)
부산대학교 학과 게시판의 공지사항을 알림으로 받아보고 한눈에 모아볼 수 있는 서비스입니다.

<p align="center"><img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/banner.png" width = "700px" alt = "introduction"></p>

## Introduction of PPAP
### 🤔 Why?
> 부산대학교 학생들은 졸업, 장학, 수업 등 중요한 정보를 얻기 위해 학과 게시판을 주로 이용합니다.
>
> 하지만 언제 중요한 공지사항이 등록될지 모르다 보니 종종 놓치는 경우도 있습니다.
> 
> 또한 졸업과제, 채용, 경진 게시판 등 하나의 학과라도 여러 개의 게시판이 따로 흩어져서 접속하는데 번거로움이 있었습니다.

**PPAP는 다음과 같은 두 가지 욕구를 만족하기 위해 탄생했습니다. 🥳**
- 중요 공지사항을 빠르게 알림으로 받아보고 싶다.
- 여러 게시판의 공지사항을 한 눈에 모아보고 싶다.

### ☺️ 팀원
|   [김태호](https://github.com/Train0303)  |   [남원정](https://github.com/1jeongg)     |
|:----------------------------------------------:|:----------------------------------------------:|
|  <img src="https://github.com/Train0303.png" width = 150> |  <img src="https://github.com/1jeongg.png" width = 150> |  
| `Backend`, `PM` |  `Android`, `Design` |


## 주요 기능
### ✏️ 화면 구성

| 공지사항 | 스크랩 | 구독 | 설정 |
|:---:|:--:|:--:|:--:|
| <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/notice.jpg" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/scrap.jpg" width = "300px">  | <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/subscribe.jpg" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/setting.jpg" width = "300px"> | 
| 스플래시 | 로그인 | 알림 설정 변경 | 구독 추가 |
| <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/splash.jpg" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/login.jpg" width = "300px">   |  <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/alarm_change.jpg" width = "300px"> |  <img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/add_subscribe.jpg" width = "300px"> |

### 🎥 시연 영상

https://github.com/PnuPostAlarmProject/android/assets/84652886/d1ad6b62-e2a0-48da-af43-76bc98d8e2b2


### ♥️ 사용자 친화적 UI
- 다크모드 지원 ➡️ 접근성 UP
- Bottom Navigation Bar, Scrollable Tab Bar ➡️ 편리한 화면 이동
- 디바이스 내 알림 설정 바로가기
- 바텀 Sheet와 Dialog의 적절한 사용
- 앱 종료 전 Toast Bar를 사용한 확인

## 개발
### 📖 Libraries
- androidx
  - core
  - lifecycle
  - activity
  - compose
  - navigation
  - datastore
  - paging
  - accompanist
- ktor
  - kotlinx.serialization
- dagger hilt
- firebase
- data store
- kakao
- junit4
- mock

### 📁 폴더 구조

```
├───📁data
│   ├───📁api
│   ├───📁dto
│   ├───📁fcm
│   ├───📁paging
│   ├───📁repository
│   └───📁util
├───📁di
├───📁domain
│   ├───📁repository
│   └───📁usecase
│       ├───📁notice
│       ├───📁scrap
│       ├───📁subscribe
│       └───📁user
├───📁presentation
│   ├───📁component
│   ├───📁login
│   ├───📁mapper
│   ├───📁navigation
│   ├───📁notice
│   ├───📁noticeItem
│   ├───📁scrap
│   ├───📁setting
│   ├───📁splash
│   ├───📁subscribe
│   ├───📁subscribe_add
│   └───📁util
├───📁theme
└───📁util
```

