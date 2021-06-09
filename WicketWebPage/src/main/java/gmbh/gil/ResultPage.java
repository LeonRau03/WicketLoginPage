package gmbh.gil;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class ResultPage extends WebPage {
	private static final long serialVersionUID = -7178991666409130335L;

	private String CookieID;

	public ResultPage(final PageParameters Parameter) {
		
		LastUserIDValue IDValue = new LastUserIDValue();
		WicketConnection wickconnect = new WicketConnection();
		
    	Configuration con = new Configuration().configure().addAnnotatedClass(WicketConnection.class); 
    	ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	SessionFactory sf = con.buildSessionFactory(sr);
    	Session session = sf.openSession();
    	Transaction tx = session.beginTransaction();
    	
    	Query q = session.createQuery("SELECT UserSessionID FROM WicketConnection");
		List<Object> userData = (List<Object>)q.list();
		
		for(Object UserID : userData) {
			System.out.println(UserID);
		} 
				
		CookieID = IDValue.AskCookie();
		
		if(userData.contains(CookieID)) {	
			wickconnect = session.get(WicketConnection.class, IDValue.AskCookie());
		} else if (!userData.contains(IDValue.getSessionId())) {
			getRequestCycle().setResponsePage(HomePage.class);
		} else {
	    	wickconnect = session.get(WicketConnection.class, IDValue.getSessionId());
			
			System.out.println(IDValue.getSessionId());
			System.out.println(wickconnect.getUserSessionID());
    	}
		
		tx.commit();
		session.close();
    	
		Label outputName = new Label("enteredName", wickconnect.getLoginName());
		add(outputName);
		
		Label outputAge = new Label("enteredAge", wickconnect.getAge());
		add(outputAge);
	
		Label outputZipCode = new Label("enteredZipCode", wickconnect.getZipCode());
		add(outputZipCode);
		
		Label outputCity = new Label("enteredCity", wickconnect.getCity());
		add(outputCity);
		
		Label outputStreet = new Label("enteredStreet", wickconnect.getStreet());
		add(outputStreet);
		
		Label outputHouseNumber = new Label("enteredHouseNumber", wickconnect.getHouseNumber());
		add(outputHouseNumber);
		
		
		
		Button reset = new Button("ResetButton");
		reset.setOutputMarkupId(true);
		reset.add(new AjaxEventBehavior("click") {
			private static final long serialVersionUID = 1L;

			
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				Configuration conf = new Configuration().configure().addAnnotatedClass(WicketConnection.class); 
				ServiceRegistry servReg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
				SessionFactory sessF = conf.buildSessionFactory(servReg);
				Session session2 = sessF.openSession();
				Transaction trans = session2.beginTransaction();

				if(userData.contains(CookieID)) {
					Query del = session2.createQuery("DELETE FROM WicketConnection WHERE UserSessionID = '" + CookieID + "'");
					del.executeUpdate();
					IDValue.DeleteCookie();
					
				} else if (userData.contains(IDValue.getSessionId())) {
					Query del = session2.createQuery("DELETE FROM WicketConnection WHERE UserSessionID = '" + IDValue.getSessionId() + "'");
					del.executeUpdate();
				
				} else {
				
				}
				trans.commit();
				session2.close();
				setResponsePage(HomePage.class);
			}
		});
		add(reset);
		
	}
}