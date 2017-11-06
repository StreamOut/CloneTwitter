package amu.m2sir.malodumont.beans;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.security.auth.message.callback.PrivateKeyCallback.IssuerSerialNumRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import amu.m2sir.malodumont.cloneTwitter.App;
import db.HibernateUtil;

public class MessageService {
	private HibernateUtil hibernateUtil = App.hibernateUtil;
	private LikeService likeService = App.likeService;
	
	public MessageService (){
		hibernateUtil.executeUpdate( "CREATE TABLE IF NOT EXISTS message " +
                "(id int, contenu varchar(255), date varchar(255), auteur varchar(255)); ");
		
	}
	
	public JsonArrayBuilder getMessages(String user){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		hibernateUtil.getSession().beginTransaction();
		List<Message> list = new ArrayList<Message>(hibernateUtil.getSession().createQuery("from amu.m2sir.malodumont.beans.Message").list() );
		List<Like> Likelist = new ArrayList<Like>(hibernateUtil.getSession().createQuery("from amu.m2sir.malodumont.beans.Like").list() );
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		for (int i = 0; i < list.size(); i++) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("contenu", list.get(i).getContenu());
			objectBuilder.add("date", list.get(i).getDate());
			objectBuilder.add("auteur", list.get(i).getAuteur());
			objectBuilder.add("id", list.get(i).getId());
			if(likeService.isAlreadyLike(Likelist, list.get(i).getId(), user) != -1)
				objectBuilder.add("like", "true");
			else
				objectBuilder.add("like", "");
			objectBuilder.add("nbMessage", nbMessage());
			arrayBuilder.add(objectBuilder);
		}
		return arrayBuilder;
	}
	
	public JsonArrayBuilder addMessage(String contenu, String user){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
		Message message;
		Date aujourdhui = new Date();
		SimpleDateFormat formater = null;
		formater = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		message = new Message(contenu, formater.format(aujourdhui), user);
		hibernateUtil.getSession().beginTransaction();
		hibernateUtil.getSession().save(message);
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		objectBuilder.add("contenu", message.getContenu());
		objectBuilder.add("date", message.getDate());
		objectBuilder.add("auteur", message.getAuteur());
		objectBuilder.add("id", message.getId());
		objectBuilder.add("nbMessage", nbMessage());
		arrayBuilder.add(objectBuilder);
		return arrayBuilder;
	}
	
	public JsonObjectBuilder getMessage(Long id){
		JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
		hibernateUtil.getSession().beginTransaction();
		Message message = (Message) hibernateUtil.getSession().load(Message.class, new Long(id));
		if (message == null) {
			return objectBuilder;
		}
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		objectBuilder.add("contenu", message.getContenu());
		objectBuilder.add("date", message.getDate());
		objectBuilder.add("auteur", message.getAuteur());
		objectBuilder.add("id", message.getId());
		objectBuilder.add("nbMessage", nbMessage());
		return objectBuilder;
	}
	
	public JsonArrayBuilder deleteMessage(String id){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
		hibernateUtil.getSession().beginTransaction();
		Object persistentInstance = hibernateUtil.getSession().load(Message.class, new Long(id));
		if (persistentInstance != null) {
			hibernateUtil.getSession().delete(persistentInstance);
		}
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		likeService.deleteLikes(new Long(id));
		objectBuilder.add("id", id);
		objectBuilder.add("nbMessage", nbMessage());
		arrayBuilder.add(objectBuilder);
		return arrayBuilder;
	}
	
	public Long nbMessage (){
		hibernateUtil.getSession().beginTransaction();
		Criteria crit = hibernateUtil.getSession().createCriteria(Message.class);
		crit.setProjection(Projections.rowCount());
		Long count = (Long)crit.uniqueResult();
		hibernateUtil.getSession().getTransaction().commit();
		hibernateUtil.getSession().flush();
		return count;
	}

}
