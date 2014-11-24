package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import model.MemberModel;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.json.*;

import JDBC.JDBCchat;
import JDBC.JDBCdataRoom;

@RestController
@RequestMapping("/play")
public class PlayController {
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String postStart(@RequestParam int idRoom) {
		// public String postStart(){
		// int idRoom = 99;
		JSONObject json = new JSONObject();
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		Random rd = new Random();
		int ranCareer = rd.nextInt(6) + 1;
		String state = jdbcDataRoom.getState(idRoom);
		if (state.equals("standby"))
			jdbcDataRoom.startGameInit(idRoom, ranCareer);
		try {
			json.put("start", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/kill", method = RequestMethod.POST)
	public String postKill(@RequestParam String username,
			@RequestParam int idRoom) {
		JDBCdataRoom jdbcDateRoom = new JDBCdataRoom();
		String state = jdbcDateRoom.getState(idRoom);
		if (state.equals("kill"))
			;
		jdbcDateRoom.updateKillPlayer(username, idRoom);
		JSONObject json = new JSONObject();
		try {
			json.put("kill", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	public String postVote(@RequestParam String voter,
			@RequestParam int idRoom, @RequestParam String voted) {
		JSONObject json = new JSONObject();
		JDBCdataRoom jdbcDateRoom = new JDBCdataRoom();
		jdbcDateRoom.updateVotePlayer(voter, idRoom, voted);
		try {
			json.put("iSvote", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String postSummit(@RequestParam String username,
			@RequestParam int idRoom) {
		JSONObject json = new JSONObject();
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		jdbcDataRoom.updateSubmit(username, idRoom);
		try {
			json.put("submit", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String postChat(@RequestParam String chater,
			@RequestParam int idRoom, @RequestParam String talk) {
		// public String postChat(){
		// String chater = "deknarog2";
		// int idRoom = 99;
		// String str = "string for test";
		JSONObject json = new JSONObject();
		JDBCchat jdbcChat = new JDBCchat();
		JDBCdataRoom jdbcDateRoom = new JDBCdataRoom();
		String state = jdbcDateRoom.getState(idRoom);
		talk = chater + " : " + talk;
		if (state.equals("chat")||state.equals("standby")) {
			jdbcChat.insertChat(idRoom, talk);
			try {
				json.put("chat", true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			try {
				json.put("chat", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return json.toString();
	}
}