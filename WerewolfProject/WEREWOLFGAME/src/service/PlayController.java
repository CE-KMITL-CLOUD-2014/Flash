package service;

import java.util.Random;

import model.MemberModel;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.json.*;

import JDBC.JDBCdataRoom;

@RestController
@RequestMapping("/play")
public class PlayController{
	@RequestMapping (value = "/start", method = RequestMethod.GET)
	public String postStart(@RequestParam int roomID){
		JSONObject json = new JSONObject();
		JDBCdataRoom jdbcDataRoom = new JDBCdataRoom();
		Random rd = new Random();
		int ranCareer = rd.nextInt(6)+1;
		jdbcDataRoom.startGameInit(roomID,ranCareer);
		try {
			json.put("start", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ""+json;
	}
	
	@RequestMapping(value ="/kill", method = RequestMethod.GET)
	public String postKill(@RequestParam String username,@RequestParam int roomID){
		JDBCdataRoom jdbcDateRoom = new JDBCdataRoom();
		jdbcDateRoom.updateKillPlayer(username, roomID);
		JSONObject json = new JSONObject();
		try {
			json.putOnce("kill", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json+"";
	}
	
	@RequestMapping(value ="/vote", method = RequestMethod.GET)
	public String postVote(
			@RequestParam String voter,
			@RequestParam int roomID,
			@RequestParam String voted){
		JSONObject json = new JSONObject();
		JDBCdataRoom jdbcDateRoom = new JDBCdataRoom();
		jdbcDateRoom.updateVotePlayer(voter, roomID, voted);
		try {
			json.put("iSvote", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ""+json;
	}
}