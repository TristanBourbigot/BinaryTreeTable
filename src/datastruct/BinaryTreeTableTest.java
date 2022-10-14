package datastruct;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryTreeTableTest {

	@Test
	public void testBinaryTreeTable() {
		BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		try{
			assertTrue(tree != null);
		}catch(Exception e){
			fail("Error - BinaryTreeTableTest testBinaryTreeTable - " + e.getMessage());
		}
			
	}

	@Test
	public void testSelect() {
		BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		try{
			tree.insert(25, "25");
			tree.insert(15, "15");
			tree.insert(35, "35");
			tree.insert(10, "10");
			tree.insert(20, "20");
			tree.insert(30, "30");
			tree.insert(40, "40");
			
			assertTrue(tree.select(25).equals("25"));
			assertTrue(tree.select(15).equals("15"));
			assertTrue(tree.select(35).equals("35"));
			assertTrue(tree.select(10).equals("10"));
			assertTrue(tree.select(20).equals("20"));
			assertTrue(tree.select(30).equals("30"));
			assertTrue(tree.select(40).equals("40"));

			assertTrue(tree.select(60) == null);
			assertTrue(tree.select(5) == null);
			}catch(Exception e){
			fail("Error - BinaryTreeTableTest testSelect - " + e.getMessage());
		}
	}

	@Test
	public void testInsert() {
		BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		try{
			assertTrue(tree.insert(25, "25"));
			assertTrue(tree.insert(15, "15"));
			assertTrue(tree.insert(35, "35"));
			assertTrue(tree.insert(10, "10"));
			assertTrue(tree.insert(20, "20"));
			assertTrue(tree.insert(30, "30"));
			assertTrue(tree.insert(40, "40"));

			assertFalse(tree.insert(10, "10"));
			assertFalse(tree.insert(25, "25"));

			assertEquals("25", tree.select(25));
			assertEquals("15", tree.select(15));
			assertEquals("35", tree.select(35));
			assertEquals("10", tree.select(10));
			assertEquals("20", tree.select(20));
			assertEquals("30", tree.select(30));

			assertFalse( tree.insert(null,"null"));
			assertFalse(tree.insert(1,null));

		}catch(Exception e){
			fail("Error - BinaryTreeTableTest testInsert - " + e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		BinaryTreeTable<String, Integer> tree2 = new BinaryTreeTable<String, Integer>();
		try{
			tree.insert(25, "25");
			tree.insert(15, "15");
			tree.insert(35, "35");
			tree.insert(10, "10");
			tree.insert(20, "20");
			tree.insert(30, "30");
			tree.insert(40, "40");
			tree2.insert(100, "first");
			tree2.insert(130, "second");
			tree2.insert(115, "third");
			tree2.insert(70, "fourth");
			tree2.insert(85, "fifth");
			tree2.insert(145, "six");
			tree2.insert(160, "seventh");
			tree2.insert(55, "eigth");
			tree2.insert(40, "ninth");
		
			assertTrue(tree.delete(10));
			assertTrue(tree.delete(40));
			assertTrue(tree.delete(15));
			assertTrue(tree.delete(25));
			assertTrue(tree.delete(35));
			assertTrue(tree.delete(20));

			
			assertTrue(tree2.delete(160));
			assertTrue(tree2.delete(55));
			assertTrue(tree2.delete(40));
			assertTrue(tree2.delete(130));
			assertTrue(tree2.delete(145));
			assertTrue(tree2.delete(70));
			assertFalse(tree.delete(10));

			}catch(Exception e){
				fail("Error - BinaryTreeTableTest testDelete - " + e.getMessage());
			}
	}

	

	@Test
	public void testToString() {
	    BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		BinaryTreeTable<String, Integer> tree2 = new BinaryTreeTable<String, Integer>();
	    try {
	        tree.insert(25, "25");
            tree.insert(15, "15");
            tree.insert(35, "35");
            tree.insert(10, "10");
            tree.insert(20, "20");
            tree.insert(30, "30");
            tree.insert(40, "40");
            System.out.println(tree.toString());

			tree2.insert(40,"40");
			tree2.insert(5,"5");
			tree2.insert(10,"10");
			tree2.insert(30,"30");
			tree2.insert(25,"25");
			tree2.insert(15, "15");
			tree2.insert(35,"35");
			tree2.insert(20, "20");
			


			System.out.println(tree2.toString());

			assertEquals("","\ncle=10\tdata=10\ncle=15\tdata=15\ncle=20\tdata=20\ncle=25\tdata=25\ncle=30\tdata=30\ncle=35\tdata=35\ncle=40\tdata=40",tree.toString());
		    assertEquals("","\ncle=5\tdata=5\ncle=10\tdata=10\ncle=15\tdata=15\ncle=20\tdata=20\ncle=25\tdata=25\ncle=30\tdata=30\ncle=35\tdata=35\ncle=40\tdata=40",tree2.toString());
		   
	    }catch(Exception e) {
	        fail("Error - BinaryTreeTableTest testDelete - "+ e.getMessage());
	    }
	}

	@Test
	public void testShowTree() {
		BinaryTreeTable<String, Integer> tree = new BinaryTreeTable<String, Integer>();
		BinaryTreeTable<String, Integer> tree2 = new BinaryTreeTable<String, Integer>();
	    try {
	        
			tree.insert(1, "1");
			tree.insert(2, "2");
			tree.insert(3, "3");
			tree.insert(4, "4");
			tree.insert(5, "5");
			tree.insert(6, "6");
			tree.insert(7, "7");
			tree.insert(8, "8");
			tree.insert(9, "9");
			tree.insert(10, "10");
			tree.showTree();

			tree2.insert(40,"40");
			tree2.insert(5,"5");
			tree2.insert(10,"10");
			tree2.insert(30,"30");
			tree2.insert(25,"25");
			tree2.insert(15, "15");
			tree2.insert(35,"35");
			tree2.insert(20, "20");
			tree2.showTree();
			Thread.sleep(10000);



			
	    }catch(Exception e) {
	        fail("Error - BinaryTreeTableTest testDelete - "+ e.getMessage());
	    }
	}

}
