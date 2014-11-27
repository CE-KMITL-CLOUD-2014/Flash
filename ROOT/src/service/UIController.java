package service;

import java.util.ArrayList;
import java.util.Collections;

import model.GameRoom;
import model.MemberModel;
import model.Villager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JDBC.JDBCchat;
import JDBC.JDBCdataRoom;
import JDBC.JDBCmember;

@RestController
@RequestMapping(value = "/gameUI")
public class UIController {

	@RequestMapping(value = "/lobby", method = RequestMethod.POST)
	public String getLobby() {
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		JSONObject json = null;
		JSONArray jsonArray = new JSONArray();
		ArrayList<GameRoom> roomlist = jdbcDataRoom.roomlist();
		int i = 0;
		jsonArray.put("room");
		for (GameRoom room : roomlist) {
			i++;
			json = new JSONObject();
			try {
				json.put("creater", room.getCreater());
				json.put("numPlayer", room.getNumPlayer());
				json.put("idRoom", room.getRoomID());
				jsonArray.put(i, json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonArray + "";
	}

	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public String getGame(@RequestParam("idRoom") int idRoom) {
		// public String getGame() {
		// int idRoom = 2;
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		JSONObject json = new JSONObject();
		GameRoom room = jdbcDataRoom.selectDataInGame(idRoom);
		int i = 0;
		String username = "";
		String career = "";
		String status = "";
		String vote = "";
		String votedPoint = "";
		String winner = null;
		for (Villager vi : room.getPlayer()) {
			i++;
			username = "username" + i;
			career = "career" + i;
			status = "status" + i;
			vote = "vote" + i;
			votedPoint = "votedPoint" + i;
			try {
				if (vi.getUsername() != null)
					json.put(username, vi.getUsername());
				else
					json.put(username, "");

				if (vi.getCareer() != null)
					json.put(career, vi.getCareer());
				else
					json.put(career, "");

				if (vi.getStatus() != null)
					json.put(status, vi.getStatus());
				else
					json.put(status, "");

				if (vi.getVotedName() != null)
					json.put(vote, vi.getVotedName());
				else
					json.put(vote, "");

				json.put(votedPoint, vi.getVotedPoint());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		int villagerNum = 5;
		if (!(room.getState().equals("standby")))
			for (Villager vi : room.getPlayer()) {
				if (vi.getCareer().equals("werewolf")
						&& vi.getStatus().equals("die")) {
					room.setState("end");
					jdbcDataRoom.updateChangeState(idRoom, "end");
					winner = "villager";
					break;
				} else if (vi.getCareer().equals("villager")
						&& vi.getStatus().equals("die"))
					villagerNum--;
			}
		if (villagerNum == 0) {
			room.setState("end");
			jdbcDataRoom.updateChangeState(idRoom, "end");
			winner = "werewolf";
		}

		String state = room.getState();
		try {
			if (state.equals("standby"))
				;
			else if (state.equals("kill"))
				;
			else if (state.equals("chat")) {
				ArrayList<Integer> submit = jdbcDataRoom.getSubmit(idRoom);
				for (int sub : submit) {
					if (sub == 0) {
						json.put("state", room.getState());
						return json.toString();
					}
				}
				jdbcDataRoom.resetSubmit(idRoom);
				jdbcDataRoom.updateChangeState(idRoom, "vote");

			} else if (state.equals("vote")) {
				ArrayList<Integer> submit = jdbcDataRoom.getSubmit(idRoom);
				for (int sub : submit) {
					if (sub == 0) {
						json.put("state", room.getState());
						return json.toString();
					}
				}
				jdbcDataRoom.resetSubmit(idRoom);
				jdbcDataRoom.killByVote(idRoom);
				jdbcDataRoom.updateChangeState(idRoom, "showvote");

			} else if (state.equals("showvote")) {
				ArrayList<Integer> submit = jdbcDataRoom.getSubmit(idRoom);
				for (int sub : submit) {
					if (sub == 0) {
						json.put("state", room.getState());
						return json.toString();
					}
				}
				jdbcDataRoom.resetSubmit(idRoom);
				jdbcDataRoom.resetVote(idRoom);
				jdbcDataRoom.updateChangeState(idRoom, "kill");

			} else if (state.equals("end")) {
				ArrayList<Integer> submit = jdbcDataRoom.getSubmit(idRoom);
				for (int sub : submit) {
					if (sub == 0) {
						json.put("isEnd", false);
						json.put("state", room.getState());
						return json.toString();
					}
				}
				json.put("isEnd", true);
				jdbcDataRoom.end(idRoom, winner);
				jdbcDataRoom.deleteRoom(idRoom);
				try {
					json.put("url", "lobby.html");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			json.put("state", room.getState());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String chat(@RequestParam("idRoom") int idRoom) {
		// public String chat(){
		// int idRoom = 99;
		JSONArray json = new JSONArray();
		JDBCchat jdbcChat = new JDBCchat();
		ArrayList<String> chat = jdbcChat.getChat(idRoom);
		int i = 1;
		json.put("chat");
		for (String talk : chat) {
			try {
				json.put(i, talk);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			i++;
		}
		return json.toString();
	}

	@RequestMapping(value = "/scoreboard", method = RequestMethod.POST)
	public String postScore(@RequestParam("username") String username) {
		// public String postScore(){
		// String username = "secret1";
		JDBCmember jdbcMember = new JDBCmember();
		JSONArray json = new JSONArray();
		ArrayList<MemberModel> memberlist = jdbcMember.listMember();
		for (int i = 1; i < memberlist.size(); i++)
			for (int j = 1; j < memberlist.size(); j++) {
				if (memberlist.get(j - 1).getScore() < memberlist.get(j)
						.getScore())
					Collections.swap(memberlist, j - 1, j);
			}
		json.put("user");
		try {
			for (int i = 1; i < 5; i++)
				json.put(i, memberlist.get(i - 1).getUserName()
						+ "\t\t : score = " + memberlist.get(i - 1).getScore());

			for (MemberModel member : memberlist) {
				if (member.getUserName().equals(username))
					json.put(5, "my score : " + member.getUserName() + "\t\t"
							+ member.getScore());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
