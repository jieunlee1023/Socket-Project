package project;

import java.util.Vector;

import lombok.Data;
import project.server.ServerGUI;
import project.server.UserSocket;

@Data
public class Room {

	private String roomName;
	private Vector<UserSocket> userSocketRoom = new Vector<>();

	private ServerGUI mContext;

	public Room(String roomName, UserSocket userSocket, ServerGUI mContext) {
		this.mContext = mContext;
		this.roomName = roomName;
		this.userSocketRoom.add(userSocket);
		userSocket.setMyCurrentRoomName(roomName);
	}

	public void roomBroadcast(String msg) {
		for (int i = 0; i < userSocketRoom.size(); i++) {
			UserSocket userSocket = userSocketRoom.elementAt(i);
			userSocket.sendMessage(msg);
		}
	}

	public void addUser(UserSocket userSocket) {
		userSocketRoom.add(userSocket);
	}

	@Override
	public String toString() {
		return roomName;
	}

	public void checkRoomUser(UserSocket userSocket) {
		for (int i = 0; i < userSocketRoom.size(); i++) {
			UserSocket socket = userSocketRoom.elementAt(i);
			userSocket.sendMessage("OldChatUser/" + socket.getNickName());
		}
	}

	public void removeRoom(UserSocket userSocket) {
		userSocketRoom.remove(userSocket);

		for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
			Room room = mContext.server.getRoomVector().elementAt(i);
			if (room.roomName.equals(roomName)) {
				mContext.server.getRoomVector().remove(this);
				mContext.server.broadCast("EmptyRoom/" + roomName);
				mContext.server.broadCast("UpdateDeleteUserData/ok");
				break;
			}
		}
	}

	public void exitRoom(UserSocket userSocket) {

		for (int i = 0; i < userSocketRoom.size(); i++) {
			System.out.println("userSocektRoomVectorCount:" + i);
			if (userSocketRoom.get(i).getNickName().equals(userSocket.getNickName())) {
				userSocketRoom.remove(i);
				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzz"+userSocket.getNickName());
				userSocket.sendMessage("ExitRoom/"+userSocket.getNickName());
			}
			mContext.server.broadCast("UpdateExitUserData/ok");
		}

	}

}
