# PPAP (Pnu Post Alarm Project)
부산대학교 학과 게시판의 공지사항을 알림으로 받아보고 한눈에 모아볼 수 있는 서비스입니다.

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

<p align="center"><img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/61db8d01-2c0f-4733-8172-539e349f0c78" width = "700px" alt = "introduction"></p>

### ☺️ 팀원
|   [김태호](https://github.com/Train0303)  |   [남원정](https://github.com/1jeongg)     |
|:----------------------------------------------:|:----------------------------------------------:|
|  <img src="https://github.com/Train0303.png" width = 150> |  <img src="https://github.com/1jeongg.png" width = 150> |  
| `Backend`, `PM` |  `Android`, `Design` |


## 주요 기능
### ✏️ 화면 구성

| 공지사항 | 스크랩 | 구독 | 설정 |
|:---:|:--:|:--:|:--:|
| <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/7ea0e234-493a-4cb3-8967-1bf31c7656ac" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/59a445d5-12c2-42ac-a136-60b5767ad7c2" width = "300px">  | <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/61a9f465-08d9-425b-8b01-ea0b13cb2198" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/a98aeebc-919a-4e1c-928f-11629f2e00de" width = "300px"> | 
| 스플래시 | 로그인 | 알림 설정 변경 | 구독 추가 |
| <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/38f4ff60-aade-4c13-80ab-c511dbd2a55e" width = "300px"> | <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/983ed44a-9938-4935-adda-b8436e5df1e2" width = "300px">   |  <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/20f3f624-4aec-483a-abd7-9e9337b5d3b6" width = "300px"> |  <img src="https://github.com/PnuPostAlarmProject/android/assets/84652886/058e1a3d-c0f2-49d6-9e35-197f975c7c12" width = "300px"> |

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

## License
Copyright 2024 ppap

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
