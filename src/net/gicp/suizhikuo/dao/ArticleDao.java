package net.gicp.suizhikuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.gicp.suizhikuo.valuebean.ArticleBean;

public class ArticleDao {
	private DB connection = null;
	private ArticleBean articleBean = null;

	public ArticleDao() {
		connection = new DB();
	}

	public DB getDB() {

		return connection;
	}

	public List queryArticle(int typeId, String type) {
		List articleList = new ArrayList();
		String sql = "";
		if (typeId <= 0) {// 不按文章类别的查询，查询前3条记录
			sql = "select  * from tb_article order by article_sdTime DESC";

		} else // 按文件类别查询
		if (type == null || type.equals("") || !type.equals("all"))
			// 生成查询某类别下的前5篇文章的SQL语句
			sql = "select top 5 * from tb_article where article_typeID ="
					+ typeId + " order by article_sdTime DESC";

		else
			// 查询某类别下的所有文章的SQL语句
			sql = "select * from tb_article where article_typeID=" + typeId
					+ "order by article_sdTime DESC";
		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				while (rs.next()) {
					articleBean = new ArticleBean();
					articleBean.setId(rs.getInt(1));
					articleBean.setTypeId(rs.getInt(2));
					articleBean.setTitle(rs.getString(3));
					articleBean.setContent(rs.getString(4));
					articleBean.setSdTime(rs.getString(5));
					articleBean.setCreate(rs.getString(6));
					articleBean.setInfo(rs.getString(7));
					articleBean.setCount(rs.getInt(8));

					// 查询tb_article数据表统计当前文章的评论数
					sql = "select count(id) from tb_review where review_articleId ="
							+ articleBean.getId();
					ResultSet rsr = connection.executeQuery(sql);
					if (rsr != null) {
						rsr.next();
						articleBean.setReview(rsr.getInt(1));
						rsr.close();
					}
					articleList.add(articleBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return articleList;
	}

	public List queryArticleFromTo(int begin, int count) {
		List articleList = new ArrayList();
		String sql = "";
		if (begin == 0 && count == 0) {
			sql = "select * from tb_article order by id desc";
		} else
			sql = "select * from tb_article order by id desc limit "
					+ (begin - 1) * 10 + "," + count + "";

		ResultSet rs = connection.executeQuery(sql);

		if (rs != null) {
			try {
				while (rs.next()) {
					articleBean = new ArticleBean();
					articleBean.setId(rs.getInt(1));
					articleBean.setTypeId(rs.getInt(2));
					articleBean.setTitle(rs.getString(3));
					articleBean.setContent(rs.getString(4));
					articleBean.setSdTime(rs.getString(5));
					articleBean.setCreate(rs.getString(6));
					articleBean.setInfo(rs.getString(7));
					articleBean.setCount(rs.getInt(8));

					// 查询tb_article数据表统计当前文章的评论数
					sql = "select count(id) from tb_review where review_articleId ="
							+ articleBean.getId();
					ResultSet rsr = connection.executeQuery(sql);
					if (rsr != null) {
						rsr.next();
						articleBean.setReview(rsr.getInt(1));
						rsr.close();
					}
					articleList.add(articleBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return articleList;
	}

	public int queryArticleSum() {
		String sql = "select count(*) from tb_article";
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

	public HashMap<Integer, String> queryArticleStyle() {
		String sql = "select * from tb_articleType";
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		int id = 0;
		String type = "";
		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				rs.next();
				id = rs.getInt(1);
				type = rs.getString(2);
				hm.put(id, type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hm;
	}

	public ArticleBean queryArticleSingle(int id) {
		String sql = "select * from tb_article where id ='" + id + "'";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				articleBean = new ArticleBean();
				articleBean.setId(rs.getInt(1));
				articleBean.setTypeId(rs.getInt(2));
				articleBean.setTitle(rs.getString(3));
				articleBean.setContent(rs.getString(4));
				articleBean.setSdTime(rs.getString(5));
				articleBean.setCreate(rs.getString(6));
				articleBean.setInfo(rs.getString(7));
				articleBean.setCount(rs.getInt(8));

				/* 查询tb_review数据表统计当前文章的评论数 */
				sql = "select count(id) from tb_review where review_article_articleId="
						+ articleBean.getId();
				ResultSet rsr = connection.executeQuery(sql);
				if (rsr != null) {
					rsr.next();
					articleBean.setReview(rsr.getInt(1));
					rsr.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleBean;
	}

	public boolean operationArticle(String oper, ArticleBean single) {
		/* 生成SQL语句 */
		String sql = null;
		if (oper.equals("add")) // 发表新文章
			sql = "insert into tb_article(article_typeID,article_title ,article_content,article_sdTime,article_create,article_info    ) "
					+ "values('"
					+ single.getTypeId()
					+ "','"
					+ single.getTitle()
					+ "','"
					+ single.getContent()
					+ "','"
					+ single.getSdTime()
					+ "','"
					+ single.getCreate()
					+ "','"
					+ single.getInfo() + "')";

		if (oper.equals("modify")) // 修改文章
			sql = "update tb_article set article_typeID=" + single.getTypeId()
					+ ",article_title='" + single.getTitle()
					+ "',article_content='" + single.getContent()
					+ "',article_create='" + single.getCreate()
					+ "',article_info='" + single.getInfo() + "' where id ="
					+ single.getId();

		if (oper.equals("delete")) // 删除文章
			sql = "delete from tb_article where id =" + single.getId();

		if (oper.equals("readTimes"))// 累加阅读次数
			sql = "update tb_article set article_count=article_count+1 where id="
					+ single.getId();
		/* 执行SQL语句 */
		boolean flag = connection.executeUpdate(sql);
		return flag;
	}
}
