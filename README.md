# :family:  솔(SOL)레는 여행, 신나는 통장

### 희망 환율에 자동 환전되는 외화 모임 통장 및 해외여행 부가 기능 제공 서비스

![image (4)](https://github.com/user-attachments/assets/d201950a-8c4a-4cff-8a2a-994b9cdcfc72)

- **배포 링크 : [SolShin](https://soltravel.shop/login)**
- 모바일 기반의 어플리케이션이기 때문에 구글 개발툴을 통해 모바일 환경에서 사용을 권장합니다.

## :calendar: 기간

- 2024.08.16 ~ 2024.08.29 : 온라인 해커톤

- 2024.08.30 ~ 2024.09.01 : 오프라인 해커톤

## 목차

1. **:golf: 프로젝트 소개**

2. :star:  **주요 기능**

3. :closed_book:**시스템 아키텍처**

4. :file_folder: **기술 스택**

5. :pencil: **ERD**

6. :tv: **구현 화면(사용 플로우)**
   
   :one: 회원가입 및 로그인
   
   :two: 메인 페이지
   
   :three: 모임통장 개설 
   
   :four: 환율 확인 
   
   :five: 이체 기능  
   
   :six: 환전 기능
   
   :seven: 정산 기능 
   
   :eight: 이체 기록 확인
   
   :nine: 가계부 기능

7. :+1: **멤버**

## :golf: 프로젝트 소개

#### 희망 환율에 자동 환전 기능을 제공하는 외화 모임 통장과, 해외여행 시 다양한 부가 혜택을 누릴 수 있는 혁신적인 금융 서비스

#### 환율 변동에 따라 최적의 시점에 환전이 가능하며, 가계부 및 자동 정산 기능을 통해 여행 이후에 돈 관리를 손쉽게 관리가 가능하게 함

## :star: 주요 기능

#### 자동 환전 기능

- 모임 통장에 모인 돈이, 모임 통장 개설시에 설정을 해두었던 희망 환율을 기준으로 연결된 외화 모임 통장으로 자동으로 환전이 됨

#### 정산 기능

- 여행을 마친 후, 정산하기 버튼 하나로 외화모임통장에 남아 있는 돈과 일반모임통장에 남아 있는 돈이 모임원들의 개인통장으로 각각 정산을 받음

#### 가계부

- OCR 기능을 이용하여 카드 결제가 아니더라도, 영수증 인식을 통해 스마트한 입출금 내역 관리가 가능해지고, 달력 기능을 투입해 매일 발생한 지출과 수익에 대해서 한눈에 알아보기 쉽게 함

## :closed_book:시스템 아키텍처

![image (2)](https://github.com/user-attachments/assets/a0e62c4a-8d10-4bbf-acb8-19ac05dd89d6)

## :file_folder: 기술 스택

##### FrontEnd

- React

- Redux

- Javascript

- Node.js

- Tailwind.css

- MUI

- daisyUI

---

##### BackEnd

- Sping

- Spring Security

- JWT

- JPA

- QueryDsl

-----

##### DB

- MySql

- Redis

- S3

---



##### Infra

- Jenkins

- Docker

- DockerHub

- Nginx

- EC2

-----



## :pencil: ERD

![image (5)](https://github.com/user-attachments/assets/a8df2e29-d726-4f61-9ca4-75aa205546d8)

## :tv: 구현 화면(사용 플로우)

#### :one: 회원가입 및 로그인

- 회원가입

![image](https://github.com/user-attachments/assets/26ac06b4-2c19-4867-a7fc-b34023129fe2)

- 로그인

![제목 없음](https://github.com/user-attachments/assets/b87459d8-11ee-433e-bdf6-377204cdeba0)

#### :two: 메인 페이지 및 마이페이지

![mainpage_and_mypage(메인페이지, 마이페이지)](https://github.com/user-attachments/assets/5784ab9d-fecf-4231-9c93-4dd737f182eb)

#### :three: 모임통장 개설

- 모임통장 최초 개설

![통장개설앞부분(모임원초대이전)](https://github.com/user-attachments/assets/3a6e555c-9257-4239-93ec-7a269b808e84)

- 모임통장 모임원 초대

![통장개설모임원초대부분](https://github.com/user-attachments/assets/943882d4-e1cf-420e-b688-1ab999b629ea)

- 모임통장 여행일 설정

![통장개설통화입력_여행일설정부분'](https://github.com/user-attachments/assets/93e5ced0-9983-48c9-a399-e660f1036026)

-  모임통장 목록 상세

![모임통장목록_목록상세](https://github.com/user-attachments/assets/9fb75107-619d-4489-a496-b534a4d5e601)



#### :four: 환율 확인

![환율페이지(차트)](https://github.com/user-attachments/assets/7084a602-79cf-41cf-9c2a-09de56de2bb1)

#### :five: 이체 기능

![모임통장으로이체](https://github.com/user-attachments/assets/b4ccfdfb-9124-4955-adf0-8a85d659e3c7)

#### :six: 환전 기능

![모임통장직접환전](https://github.com/user-attachments/assets/dcee1685-3cf6-4ec5-963a-7ad058c07fdf)

#### :seven: 정산 기능

![정산하기](https://github.com/user-attachments/assets/bee1614b-6011-41e3-8bac-13a3b63f7625)

#### :eight: 이체 기록 확인

![일반모임통장이체내역](https://github.com/user-attachments/assets/e36540f8-7775-4bfe-a87a-5891fce1c3d6)

#### :nine: 가계부 기능

- 가계부 상세 내역 조회

![가계부상세내역조회](https://github.com/user-attachments/assets/5c07b592-bb10-4933-86cd-9aec800d8990)

- 영수증으로 가계부 추가

![](C:\Users\SSAFY\Downloads\��<�\��Ĭ�����.gif)

## :+1: 멤버

![image (3)](https://github.com/user-attachments/assets/286b8517-8718-4788-bc9c-19ac6d930b3e)
