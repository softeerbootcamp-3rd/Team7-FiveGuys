# Team7-FiveGuys
## 팀원 소개

| <img src="https://avatars.githubusercontent.com/u/116520932?v=4" width="100px" alt="이상민 깃허브 프로필"> | <img src="https://avatars.githubusercontent.com/u/102009925?v=4" width="100px" alt="강민우 깃허브 프로필"> | <img src="https://avatars.githubusercontent.com/u/104425318?v=4" width="100px" alt="전용호 깃허브 프로필"> | <img src="https://avatars.githubusercontent.com/u/62013201?v=4" width="100px" alt="박동민 깃허브 프로필"> | <img src="https://avatars.githubusercontent.com/u/104637774?v=4" alt="이승헌 깃허브 프로필"> |
|:---:|:---:|:---:|:---:|:---:|
| [AOS-이상민](https://github.com/StrangeMin) | [AOS-강민우](https://github.com/MgNuu) | [AOS,BE-전용호](https://github.com/yo0919) | [BE-박동민](https://github.com/eyeben) | [BE-이승헌](링크5) |

--- 

# 협업 전략
## GitHub FLow
### 선택 이유
1. 복잡한 프로젝트 관리보다는 간단하고 빠른 반복을 선호
2. 여러 버전을 관리할 필요가 없음

## 개발 방법론
### 애자일
> - 팀원들의 개발 경험이 적어 많은 대화와 잦은 수정을 고려하여 선택
> - 백로그, 스프린트 백로그를 깃헙 이슈, 프로젝트를  통해 관리
> - 스프린트는 1주
> - 데일리 스크럼을 통해 일정을 공유하고 오늘 할 일을 스프린트 백로그 -> ToDoList로 상태 변환
> - 퇴실 1시간 전 스프린트 회고를 통해 팀원들의 방향성 및 문제점 공유

## 브랜치 전략
### 1. dev에 pr 날려서 merge하기
- 모든 팀원의 리뷰 후 merge하기

### 2. 브랜치 생성
- 안드로이드는 aos/feat/{구현화면명}#{issue-number}
- 백엔드는 be/refactor/{구현api명}#{issue-number}


## 커밋 전략
<details>
<summary>상세 보기</summary>
<div markdown="1">

### 1. 커밋 유형(Type) 지정

- 커밋 유형은 영어 대문자로 작성하기
    
    | 커밋 유형 | 의미 |
    | --- | --- |
    | Feat | 새로운 기능 추가 |
    | Fix | 버그 수정 |
    | Docs | 문서 수정 |
    | Style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
    | Refactor | 코드 리팩토링 |
    | Test | 테스트 코드, 리팩토링 테스트 코드 추가 |
    | Chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore, import문 제거 |
    | Design | CSS 등 사용자 UI 디자인 변경 |
    | Comment | 필요한 주석 추가 및 변경 |
    | Rename | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
    | Remove | 파일을 삭제하는 작업만 수행한 경우 |
    | !BREAKING CHANGE | 커다란 API 변경의 경우 |
    | !HOTFIX | 급하게 치명적인 버그를 고쳐야 하는 경우 |

### 2. 제목과 본문을 빈행으로 분리

- 커밋 유형 이후 제목과 본문은 한글로 작성하여 내용이 잘 전달될 수 있도록 할 것
- 본문에는 변경한 내용과 이유 설명 (어떻게보다는 무엇 & 왜(근거)를 설명)

### 3. 제목 첫 글자는 대문자로, 끝에는 `.` 금지

### 4. 제목은 영문 기준 50자 이내로 할 것

### 5. 자신의 코드가 직관적으로 바로 파악할 수 있다고 생각하지 말자

### 6. 여러가지 항목이 있다면 글머리 기호를 통해 가독성 높이기

```
- 변경 내용 1
- 변경 내용 2
- 변경 내용 3
```

</div>
</details>

