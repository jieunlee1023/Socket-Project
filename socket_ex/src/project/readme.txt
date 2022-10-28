[ 개인 프로젝트 ]
: 소켓통신을 이용한 채팅프로그램 만들기 

객체 설계 : 
1. 단일 책임에 원칙 (SRP : Single Responsibility principle)
2. 개방 폐쇄의 원칙 (OCP : Open-Closed principle)

[기능]
1. 서버 프로그램 & 클라이언트 프로그램 생성
2. 프로 토콜의 정의
3. 양방향, 다중 접속 구현하기
( 같은 방 유저끼리 채팅 기능 )

필수 기능
: 브로드 캐스트 (유저목록, 방 개념, 전체 메시지, 귓속말)

★★★ [프로토콜 별 기능]
 "/" -> 그 방으로 들어가는 약속, 인터페이스로 구현하는 것을 추천함 

whisper : 귓속말 기능
Chatting : 채팅 - "Chatting/방이름/유저이름/내용"
NewUser : 새로운 유저 정보 확인
NewRoom : 새로운 방 정보 확인 (방생성)
EnterRoom : 방 입장 "EnterRoom/방이름/유저이름"
ExitRoom : 방 나가기 "ExitRoom/방이름/유저이름"
OldUser/OldRoom : 기존에 있는 유저들 정보 업데이트

