//package bad;
//
//import java.util.Vector;
//
//class Room {
//
//	String roomName;
//	Vector<UserSocket> room_user_vc = new Vector<UserSocket>();
//
//	public Room(String roomName, UserSocket u) {
//		this.roomName = roomName;
//		this.room_user_vc.add(u);
//		// 와우 대박. ㅋㅋ
//		u.myCurrentRoomName = roomName;
//	}
//
//	private void roomBroadcast(String str) { // 현재방의 모든 사람들에게 알린다.
//		for (int i = 0; i < room_user_vc.size(); i++) {
//			UserSocket u = room_user_vc.elementAt(i);
//			u.sendmessage(str);
//		}
//	}
//
//	private void addUser(UserSocket u) {
//		room_user_vc.add(u);
//	}
//
//	@Override
//	public String toString() {
//		return roomName;
//	}
//
//	private void removeRoom(UserSocket u) {
//		room_user_vc.remove(u);
//		boolean empty = room_user_vc.isEmpty();
//		if (empty) {
//			for (int i = 0; i < vc_room.size(); i++) {
//				Room r = vc_room.elementAt(i);
//				if (r.roomName.equals(roomName)) {
//					vc_room.remove(this);
//					broadCast("EmptyRoom/" + roomName);
//					broadCast("UserData_Updata/ok");
//					break;
//				}
//			}
//		}
//	}
//}
