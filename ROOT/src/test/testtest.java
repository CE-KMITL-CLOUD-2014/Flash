package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class testtest {

	@Test
	public void test() {
		User[] testUser = new User[6];
		for(int i = 0 ; i < 6 ;i++)
		testUser[i] = new User();
		villager a = new villager(1234);
		testUser[0].IDnum = 1234;
		testUser[1].IDnum = 2345;
		testUser[2].IDnum = 3456;
		testUser[3].IDnum = 4567;
		testUser[4].IDnum = 5678;
		testUser[5].IDnum = 6789;
		
		GameRoom testRoom = new GameRoom();
		
		testRoom.join(testUser[0]);
		assertEquals(testUser[0],testRoom.user[0]);
		
		testRoom.exit(testUser[0]);
		assertEquals(testRoom.user[0],null);
		
		testRoom.join(testUser[0]);
		testRoom.join(testUser[1]);
		testRoom.join(testUser[2]);
		testRoom.join(testUser[3]);
		testRoom.join(testUser[4]);
		testRoom.join(testUser[5]);
		assertEquals(true, testRoom.full());
		
		testRoom.exit(testUser[2]);
		assertEquals(false, testRoom.full());
		assertEquals(2, testRoom.indexEmpty());
		
	}

}
