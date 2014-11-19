package service;

import java.util.ArrayList;

import model.GameRoom;
import model.Villager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JDBC.JDBCdataRoom;

@RestController
@RequestMapping(value = "/gameUI")
public class UIController {

	// public JSONObject getLobby(){
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
	// public String getGame(@RequestParam("idRoom") int idRoom){
	public String getGame() {
		int idRoom = 1;
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		JSONObject json = new JSONObject();
		GameRoom room = jdbcDataRoom.selectDataInGame(idRoom);
		int i = 0;
		String username = "";
		String career = "";
		String status = "";
		String vote = "";
		String votedPoint = "";
		for (Villager vi : room.getPlayer()) {
			i++;
			username = "username" + i;
			career = "career" + i;
			status = "status" + i;
			vote = "vote" + i;
			votedPoint = "votedPoint" + i;
			try {
				json.put(username, vi.getUsername());
				json.put(career, vi.getCareer());
				json.put(status, vi.getStatus());
				json.put(vote, vi.getVotedName());
				json.put(votedPoint, vi.getVotedPoint());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int j;
			String state = room.getState();
			if (state.equals("stand by"));
			else if (state.equals("vote"))
				for (j = 0; j < 6; j++) {
					if(room.getPlayer().get(i).getStatus().equals("alive")&&
					!(room.getPlayer().get(i).getVotedName().equals(""))){
						break;
					}
					if(j == 6){
						jdbcDataRoom.killByVote(idRoom);
						try {
							json.put("isEndVote", true);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			else if (state.equals("start"))
				for (j = 0; j < 6; j++) {
					try {
						json.put("isStart", true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			try {
				json.put("state", room.getState());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "" + json;
	}

	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String chat(@RequestParam("roomID") int roomID) {
		return null;
	}
}
