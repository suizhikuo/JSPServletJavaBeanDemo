package net.gicp.suizhikuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.gicp.suizhikuo.valuebean.WordBean;

public class WordDao {
	private DB connection = null;
	private WordBean wordBean = null;

	public WordDao() {
		connection = new DB();
	}

	public DB getDB() {

		return connection;
	}

	public int queryWordSum() {
		String sql = "select count(*) from tb_word";
		int sum = 0;
		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				rs.next();
				sum = rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sum;
	}

	public List queryWord(int begin, int count) {
		List wordList = new ArrayList();
		String sql = "";
		if (begin == 0 && count == 0) {
			sql = "select * from tb_word order by id desc";
		} else
			sql = "select * from tb_word order by id desc limit " + (begin - 1)
					* 10 + "," + count + "";

		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				while (rs.next()) {
					wordBean = new WordBean();
					wordBean.setId(rs.getInt(1));
					wordBean.setContent(rs.getString("word_content"));
					wordBean.setSdTime(rs.getString("word_sdtime"));
					wordBean.setAuthor(rs.getString("word_author"));
					wordList.add(wordBean);
					System.out
							.println("test-------------------WordDao.queryWord()查询留言成功");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return wordList;
	}

	public void addWord(WordBean wordBean) {
		// String title = wordBean.getTitle();
		String content = wordBean.getContent();
		String author = wordBean.getAuthor();
		String sdTime = wordBean.getSdTime();

		String sql = "insert into tb_word(word_author ,word_content ,word_sdTime )"
				+ " values('"
				+ author
				+ "','"
				+ content
				+ "','"
				+ sdTime
				+ "')";

		boolean flag = connection.executeUpdate(sql);
		if (flag == true)
			System.out.println("增加留言成功");
		else
			System.out.println("增加留言失败");
	}

}
