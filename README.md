<h1 align="center"> PPAP (Pnu Post Alarm Project) </h1>
<p align="center"> 부산대학교 학과 게시판을 한눈에 모아보고 <br>
  새로운 공지를 푸시 알림으로 빠르게 확인하는 서비스입니다.</p>

<h2 align="center"><a href = "https://play.google.com/store/apps/details?id=com.jeongg.ppap">⏩ 지금 당장 다운받으러 가기 (Click here!) ⏪</a></h2>

<p align="center"><img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/banner.png" width = "700px" alt = "introduction"></p>

<p align="center"><img src="https://github.com/PnuPostAlarmProject/android/blob/main/img/screen_shots.png" alt = "screen_shots"></p>

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
### 기능 

⭐ **부산대 내 원하는 게시판 구독**
> - 모든 학과의 게시판 (자유, 취업, 공지) 을 구독할 수 있어요
> - 등록되어있지 않는 게시판은 직접 사용자가 추가할 수도 있어요

⏰ **새 공지사항 푸시 알림**
> - 구독한 게시판 중 알림 설정을 `ON` 해두면 새로운 공지사항이 등록되었을 때 푸시 알림으로 받아볼 수 있어요

📢 **공지사항 조회하기**
> -  구독중인 게시판의 공지사항을 한눈에 확인할 수 있어요

❤️ **마음에 드는 공지사항 스크랩하기**
> -  “ 이건 꼭.. 소장해야 돼🥹 “ 하는 공지사항은 하트를 눌러 스크랩 화면에서 모아볼 수 있어요

### ✏️ 화면 구성

![스크린샷](https://github.com/PnuPostAlarmProject/android/blob/main/img/all_screen.png)

### 🎥 시연 영상

https://github.com/PnuPostAlarmProject/android/assets/84652886/e9e169a0-a3e1-4aa8-aaa2-796d1f9bcc59


### ♥️ 우리 PPAP는요…
- 게시판 추가 로직의 단순화 ➡️ 사용자의 불편 감소
- RSS 를 지원하지 않는 학과 (기계공학과 등) 지원 ➡️ 모두에게 서비스 이용의 기회 제공
- 다크모드 지원 ➡️ 접근성 UP
- Bottom Navigation Bar, Scrollable Tab Bar ➡️ 편리한 화면 이동
- 디바이스 내 알림 설정 바로가기 ➡️ 간편한 알림 설정 변경

## 🔗 링크 바로가기
- [구글 플레이스토어](https://play.google.com/store/apps/details?id=com.jeongg.ppap)
- [노션](https://taeho1234.notion.site/PPAP-c1fdfb4bf0a6495dad741554c8a29cb2?pvs=4)
- [백엔드 Github](https://github.com/PnuPostAlarmProject/api-server)
- [Figma](https://www.figma.com/file/XlxcZQFNuUaBr5ANEGbbBO/PPAP?type=design&node-id=519%3A2&mode=design&t=Zl3HBpTWioUAaSTl-1)
- [동작 영상 Youtube](https://youtu.be/MxdS-I7c6Qg?si=tXp0E59gy3j9Q6sf)


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
│   │   ├───📁notice
│   │   ├───📁scrap
│   │   ├───📁subscribe
│   │   ├───📁univ
│   │   └───📁user
│   ├───📁fcm
│   ├───📁paging
│   ├───📁repository
│   ├───📁smtp
│   ├───📁util
│   └───📁_const
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
│   │   ├───📁loading
│   │   └───📁util
│   ├───📁login
│   ├───📁mapper
│   ├───📁navigation
│   ├───📁notice
│   ├───📁scrap
│   ├───📁setting
│   ├───📁setting_complain
│   ├───📁splash
│   ├───📁state
│   ├───📁subscribe
│   ├───📁subscribe_custom_add
│   ├───📁subscribe_custom_update
│   ├───📁subscribe_default_add
│   │   ├───📁board_list
│   │   └───📁univ_list
│   └───📁util
├───📁theme
└───📁util
```

