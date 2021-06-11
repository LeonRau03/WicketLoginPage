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

public class ShowInfos extends WebPage{
	private static final long serialVersionUID = 1872101592993974948L;

	
	public ShowInfos(final PageParameters Parameter) {
    	
		Label outputName = new Label("enteredName", Parameter.get("Username"));
		add(outputName);
		
		Label outputAge = new Label("enteredAge", Parameter.get("Age"));
		add(outputAge);
	
		Label outputZipCode = new Label("enteredZipCode", Parameter.get("Zip Code"));
		add(outputZipCode);
		
		Label outputCity = new Label("enteredCity", Parameter.get("City"));
		add(outputCity);
		
		Label outputStreet = new Label("enteredStreet", Parameter.get("Street"));
		add(outputStreet);
		
		Label outputHouseNumber = new Label("enteredHouseNumber", Parameter.get("House Number"));
		add(outputHouseNumber);
		
		
		Button backButton = new Button("BackButton");
		backButton.setOutputMarkupId(true);
		backButton.add(new AjaxEventBehavior("click") {
			private static final long serialVersionUID = -7347512649948235167L;
			
			@Override
            protected void onEvent(AjaxRequestTarget backtarget) {
				setResponsePage(HomePage.class);
			}
        });
		add(backButton);
		
		
		String loginName = String.valueOf(Parameter.get("Username"));
		String strAge = String.valueOf(Parameter.get("Age"));
		int age = Integer.valueOf(strAge);
		String strZipCode = String.valueOf(Parameter.get("Zip Code"));
		int zipCode = Integer.valueOf(strZipCode);
		String city = String.valueOf(Parameter.get("City"));
		String street = String.valueOf(Parameter.get("Street"));
		String strHouseNumber = String.valueOf(Parameter.get("House Number"));
		int houseNumber = Integer.valueOf(strHouseNumber);
		
		
		Button proceedButton = new Button("ProceedButton");
		proceedButton.setOutputMarkupId(true);
		proceedButton.add(new AjaxEventBehavior("click") {
			private static final long serialVersionUID = -7347512649948235167L;
			
			@Override
            protected void onEvent(AjaxRequestTarget proceedtarget) {
				LastUserIDValue IDValue = new LastUserIDValue();
				PageParameters pageParameters = new PageParameters();
				WicketConnection Wickcon = new WicketConnection();

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
				
				if(userData.contains(IDValue.AskCookie())) {
					setResponsePage(ResultPage.class);
				} else if (userData.contains(IDValue.getSessionId())) {
					setResponsePage(ResultPage.class);
				} else if (!userData.contains(IDValue.getSessionId()) && !userData.contains(IDValue.AskCookie())) {
					Wickcon.setUserSessionID(IDValue.getSessionId());
					Wickcon.setLoginName(loginName);
					Wickcon.setAge(age);
					Wickcon.setZipCode(zipCode);
					Wickcon.setCity(city);
					Wickcon.setStreet(street);
					Wickcon.setHouseNumber(houseNumber);
	
	
					session.save(Wickcon);
					tx.commit();
	
					System.out.println(IDValue.getSessionId());
					System.out.println(Wickcon.getUserSessionID());
					
			    	IDValue.CookieUtils();
					
					System.out.println(IDValue.CookieUtils());
					
					getRequestCycle().setResponsePage(ResultPage.class, pageParameters);
				}
			}
        });
		add(proceedButton);
	}
}
