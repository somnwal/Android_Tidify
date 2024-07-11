# Android_KakaoBank_Highlight
 2023 카카오뱅크 하이라이트 과제

```
1. 과제 설명
   이미지를 검색해서 보관함에 수집하는 안드로이드 앱을 작성해주세요.

* 검색은 키워드 하나에 이미지 검색과 동영상 검색을 동시에 사용, 두 검색 결과를 합친 리스트를 사용합니다.
  구체적인 사용 필드는 아래와 같습니다.
  이미지 검색 API ( https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-image )
  동영상 검색 API ( https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video )
* 두 검색 결과를 datetime 필드를 이용해 정렬하여 출력합니다. (최신부터 나타나도록)

* UI는 fragment 2개를 사용합니다. (버튼이나 탭 선택 시 전환)
* 첫 번째 fragment : 검색 결과
  검색어를 입력할 수 있습니다.
  검색된 이미지 리스트가 나타납니다. 각 아이템에는 이미지와 함께 날짜와 시간을 표시합니다.
  스크롤을 통해 다음 페이지를 불러옵니다.
  리스트에서 특정 이미지를 선택하여 '내 보관함'으로 저장할 수 있습니다.
  이미 보관된 이미지는 특별한 표시를 보여줍니다. (좋아요/별표/하트 등)
  보관된 이미지를 다시 선택하여 보관함에서 제거 가능합니다.
* 두 번째 fragment : 내 보관함
  검색 결과에서 보관했던 이미지들이 보관한 순서대로 보입니다.
  보관한 이미지 리스트는 앱 재시작 후 다시 보여야 합니다. (DB 관련 라이브러리 사용 금지. SharedPreferences 사용 권장)

적혀있지 않은 내용은 자유롭게 작성하시면 됩니다. (요건을 침해하지 않는 범위에서 기능 추가 등)

2. 개발 요건
* 검색 데이터는 https://developers.kakao.com/product/search 의 Open API를 사용합니다.
  오픈 소스 사용 가능합니다. 참고로 카카오뱅크에서는 retrofit, kotlinx-coroutines-android, rxjava 등을 사용하고 있습니다.
```

### 프로젝트 구조

|        모듈명        |                     설명                    |
|:-------------------|:-------------------------------------------|
| :app               | 앱                                         |
| :core              | 공통/핵심 모듈                                |
| :core:data         | API, Repository 등 데이터 관리 모듈            |
| :core:data-api     | Repository 추상 인터페이스 정의                 |
| :core:datastore    | 기기 내 저장하는 데이터 관리                     |
| :core:model        | 데이터 모델 정의                              |
| :core:designsystem | 앱 테마, 아이콘, 공통 컴포넌트 등 UI 요소 구현      |
| :core:domain       | UseCase 정의                               |
| :feature           | 실질적인 화면 구현                             |
| :feature:main      | 앱 공통 화면 구현                              |
| :feature:search    | 검색 화면 구현                                |
| :feature:favorite  | 보관함 화면 구현                               |
| :feature:web       | 웹 화면 구현                                  |

![멀티모듈 프로젝트 구조](https://github.com/somnwal/Android_KakaoBank_Highlight/blob/main/project.png?raw=true)

### 디펜던시 그래프 생성

graphviz 설치

```bash
brew install graphviz
```

<br/>

디펜던시 그래프 그리기
```
gradle projectDependencyGraph
```

<br/><br/>

### 사용 라이브러리 (요약본)

- Compose UI
```kotlin
compose = "1.9.0"
composeCompiler = "1.5.1"
composeBom = "2023.08.00"
composeMaterial = "1.2.1"
```

- 의존성 주입 (DI)
```kotlin
hilt = "2.48"
hiltNavigationCompose = "1.2.0"
```

- API 호출
```kotlin
retrofit = "2.9.0"
retrofit-converter-gson = "2.9.0"
retrofit-adapter-rxjava3 = "2.9.0"
loggingInterceptor = "4.10.0"
```

- Reactive Programming
```kotlin
rxKotlin = "3.0.1"
rxAndroid = "3.0.1"
```

- 이미지로딩
```kotlin
coil = "2.2.2"
```
