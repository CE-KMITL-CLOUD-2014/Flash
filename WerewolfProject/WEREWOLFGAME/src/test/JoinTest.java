package test;

import static org.junit.Assert.*;
import model.GameRoom;
import model.MemberModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import JDBC.JDBCdataRoom;
import service.JoinRoomController;

public class JoinTest {

//	@Test
//	public void test() throws JSONException {
//		MemberModel tester1 = new MemberModel(99, "username1", "password");
//		JoinRoomController jr = new JoinRoomController();
//		JSONObject room = jr.getCreate(tester1.getUserName());
//		MemberModel tester2 = new MemberModel(99, "username2", "password");
//		MemberModel tester3 = new MemberModel(99, "username3", "password");
//		MemberModel tester4 = new MemberModel(99, "username4", "password");
//		MemberModel tester5 = new MemberModel(99, "username5", "password");
//		MemberModel tester6 = new MemberModel(99, "username6", "password");
//		int idRoom = room.getInt("idRoom");
//		jr.getJoin(tester2.getUserName(), idRoom);
//		jr.getJoin(tester3.getUserName(), idRoom);
//		jr.getJoin(tester4.getUserName(), idRoom);
//		jr.getJoin(tester5.getUserName(), idRoom);
//		jr.getJoin(tester6.getUserName(), idRoom);
//		JDBCdataRoom jdbc = new JDBCdataRoom();
//		GameRoom roomtest = jdbc.selectDataInGame(idRoom);
//		//assertEquals(, actuals);
//	}
}
