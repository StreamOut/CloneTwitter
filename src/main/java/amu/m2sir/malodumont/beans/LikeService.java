package amu.m2sir.malodumont.beans;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.hibernate.Session;
import org.hibernate.Transaction;

import amu.m2sir.malodumont.cloneTwitter.App;
import db.HibernateUtil;

public class LikeService {
private HibernateUtil hibernateUtil = App.hibernateUtil;
	
	public LikeService(){
		hibernateUtil.executeUpdate( "CREATE TABLE IF NOT EXISTS likes " +
                "(id int, messageId int, likeur varchar(255)); ");
	}
	
	public int isAlreadyLike(List<Like> likes, Long messageId, String user) {
		for (int i = 0; i < likes.size(); i++)
			if(likes.get(i).getMessageId().equals(messageId) &&likes.get(i).getLikeur().equals(user))
				return i;
		return -1;
	}
	
	public JsonArrayBuilder like(Long messageId, String user){
		System.out.println("Id : "+messageId+" user "+user);
		Like like = new Like(messageId,user);
		int already;
		JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
		hibernateUtil.getSession().beginTransaction();
		List<Like> list = new ArrayList<Like>(hibernateUtil.getSession().createQuery("from amu.m2sir.malodumont.beans.Like").list() );
		  already = isAlreadyLike(list, messageId, user);
		  if (already != -1) {
				hibernateUtil.getSession().delete(list.get(already));
				objectBuilder.add("like", "");
		}
		else {
			hibernateUtil.getSession().save(like);
			objectBuilder.add("like", "true");
		}
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
		objectBuilder.add("id", like.getMessageId());
		objectBuilder.add("user", like.getLikeur());

		arrayBuilder.add(objectBuilder);
		return arrayBuilder;
	}
	
	public JsonArrayBuilder getLikes(){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
		hibernateUtil.getSession().beginTransaction();
		List<Like> list = new ArrayList<Like>(hibernateUtil.getSession().createQuery("from amu.m2sir.malodumont.beans.Like").list() );
		  for (int i = 0; i < list.size(); i++) {
			System.out.println("MessageId : "+list.get(i).getMessageId() + " likeur "+list.get(i).getLikeur());
			objectBuilder.add("id", list.get(i).getMessageId());
			objectBuilder.add("likeur", list.get(i).getLikeur());
			arrayBuilder.add(objectBuilder);
		  }
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		return arrayBuilder;
	}
	
	public void deleteLikes(Long messageId){
		hibernateUtil.getSession().beginTransaction();
		List<Like> list = new ArrayList<Like>(hibernateUtil.getSession().createQuery("from amu.m2sir.malodumont.beans.Like").list() );
		  for (int i = 0; i < list.size(); i++) {
			System.out.println("deleteLikes : "+list.get(i).getMessageId() +" message id :"+messageId);
			if(list.get(i).getMessageId().equals(messageId)){
				hibernateUtil.getSession().delete(list.get(i));
				System.out.println("Delete "+messageId);
			}
		  }
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
	}


}
