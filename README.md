# AI 학습용 한국인 두피 상태 이미지 데이터 해커톤

## 1. 간단한 설명
본 어플리케이션은 AI 학습용 한국인 두피 상태 이미지 데이터 해커톤을 위해 개발된 간단한 프로토타입용 어플리케이션입니다.
주최측에서 제공해주는 한국인 두피 상태 이미지 데이터를 활용하여 인공지능 지식을 활용한 어플리케이션입니다.<br>
또한 본 해커톤에서는 비즈니스적 측면의 조사도 필요로 했기 때문에 사업 계획서도 작성하였습니다.

## 2. 서비스 설명
1) 두피/모발에 사용되는 화장품 구독 서비스 제공이 주 서비스입니다.
2) 스마트폰용 USB 현미경을 배달해주고 그것을 이용해 집에서 어플리케이션을 통해 두피를 촬영합니다.
3) 촬영된 두피는 기존 AI 학습용 한국인 두피 상태 이미지 데이터를 통해 학습된 AI 모델을 통해 두피 상태가 나오게 됩니다.
4) 두피 상태에 맞는 화장품을 추천받게 됩니다.
5) 화장품은 자체 제작하는 것이 아니라 인디 뷰티 브랜드와 제휴를 맺고 아웃소싱을 하여 제공하게 됩니다.
6) 추가적으로 공유 미용실을 활용하여 두피/모발 상태에 맞는 화장품 정기 배송 뿐 아니라 직접적 미용 서비스도 받을 수 있습니다.
7) 공유 미용실에 대한 정보, 위치 등을 제공해주고 사용자에게 맞는 디자이너(공유 미용실 사용 디자이너)를 선택합니다.
8) 위와 같은 과정을 통해 두피/모발에 대한 전체적인 서비스를 받습니다.

## 3. 서비스 차별성
1) 많은 구독 서비스가 존재하지만 화장품 구독 서비스는 존재하지 않습니다.
2) 두피/모발 상태를 AI 모델을 통해 알 수 있기 때문에 개인 맞춤형 화장품을 받을 수 있습니다.
3) 코로나19로 인하여 미용실 가는 것을 꺼려하는 사람들을 위해 최근 증가하고 있는 공유 미용실을 사용합니다.
4) 미용실 추천 서비스는 많지만 그 서비스들은 전부 공유 미용실은 취급하지 않는 점에서 또 하나의 차별성을 보입니다.

## 4. 개발 언어 및 환경
+ 안드로이드: 안드로이드 스튜디오/JAVA<br>
+ 이미지: Glide
+ 지도: Naver Mobile Dynamic Map
+ 내 위치: Geocoder of Google
+ 레이다 차트: MPAndroidChart Opensource

## 5. 사용 데이터
주최가 제공한 AI 학습용 한국인 두피 상태 이미지 데이터

## 6. 어플리케이션 버전
+ compileSdkVersion 30
+ minSdkVersion 16
+ targetSdkVersion 30

## 7. Dependencies
+ Glide 4.11.0
+ Naver map-sdk 3.11.0
+ Radar chart MPAndroidChart v3.1.0

## 8. Permissions
+ android.permission.ACCESS_FINE_LOCATION
+ android.permission.ACCESS_COARSE_LOCATION

## 9. 어플리케이션 이미지
![전체](https://user-images.githubusercontent.com/17876424/110230398-f7b85d00-7f53-11eb-8259-3c5d2822db36.PNG)
