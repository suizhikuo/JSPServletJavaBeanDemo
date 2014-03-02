package net.gicp.suizhikuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.gicp.suizhikuo.valuebean.ReviewBean;

 
public class ReviewDao {
	private DB connection = null;
	private ReviewBean reviewBean = null;
	
	public ReviewDao() {
		connection = new DB();
	}
	
	public DB getDB() {
		
		return connection;
	}
	
	public int queryReviewSum(int articleId){
		String sql = "select count(*) from tb_review where review_articleID='"+articleId +"'";
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
	
	
	
	
	public List queryReview(int articleId,int begin,int count ) {
		 List reviewList = new ArrayList(); 
		 String sql = "select * from tb_review where review_articleId = '" + articleId + "'";
		 
			if (begin == 0 && count == 0) {
				sql = "select * from tb_review where review_articleId = '" + articleId + "'order by id desc";
			} else
				sql = "select * from tb_review where review_articleId = '" + articleId + "' order by id desc limit "+(begin-1)*10+","+count+"";
		 
		 ResultSet rs = connection.executeQuery(sql);
		 if(rs != null) {
			 try {
				 while (rs.next()) {
					 reviewBean = new ReviewBean();
					 reviewBean.setId(rs.getInt(1));
					 reviewBean.setArticleId(rs.getInt(2));
					 reviewBean.setAuthor(rs.getString(3));
					 reviewBean.setContent(rs.getString(4));
					 reviewBean.setSdTime(rs.getString(5));
					 reviewList.add(reviewBean);
				 }
			 } catch (SQLException e) {
					e.printStackTrace();
				}
		 }
		 System.out.println("查询查询查询查询查询查询查询查询查询查询查询查询查询评论成功");
		 return reviewList;
	}
	
	
	
	public void followAdd(ReviewBean reviewBean) {
		int articleId = reviewBean.getArticleId();
		String content = reviewBean.getContent();
		String author = reviewBean.getAuthor();
		String sdTime = reviewBean.getSdTime();
		
		String sql = "insert into tb_review(review_articleID ,review_author ,review_content ,review_sdTime )" +
				" values("+articleId+",'"+author+"','"+content+"','"+sdTime+"')";
		
		boolean flag = connection.executeUpdate(sql);
		if(flag == true)
			System.out.println("增加评论成功");
		else 
			System.out.println("增加评论失败");
	}
}
