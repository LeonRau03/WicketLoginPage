package gmbh.gil;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public String loginName;
	public Integer age;
	public Integer zipCode;
	public String city;
	public String street;
	public Integer houseNumber;
	private Object CookieID;
	
			public HomePage(final PageParameters parameters) throws Exception {				
				
				LastUserIDValue Value = new LastUserIDValue();
				System.out.println(Value.AskCookie());
				
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
				
				tx.commit();
				
				if(userData.contains(Value.AskCookie())) {
					getRequestCycle().setResponsePage(ResultPage.class);
				} else {
					
					FeedbackPanel showInfos = new FeedbackPanel("feedback");
					add(showInfos);
					showInfos.setOutputMarkupId(true);
					
					
					final TextField<String> username = new TextField<String>("Username", new PropertyModel<String>(this, "loginName"));
					username.setOutputMarkupId(true);
					username.setRequired(true);
					username.add(StringValidator.minimumLength(3));
					username.add(new AjaxValidator(showInfos));
					
					
					final TextField<Integer> userAge = new TextField<Integer>("Age", new PropertyModel<Integer>(this, "age"));
					userAge.setRequired(true);
					userAge.add(RangeValidator.range(16, 120));
					userAge.add(new AjaxValidator(showInfos));
					
					
					final TextField<Integer> userZipCode = new TextField<Integer>("Zip Code", new PropertyModel<Integer>(this, "zipCode"));
					userZipCode.setRequired(true);
					userZipCode.add(RangeValidator.range(10000, 99999));
					userZipCode.add(new AjaxValidator(showInfos));
	
					
					final TextField<String> userCity = new TextField<String>("City", new PropertyModel<String>(this, "city"));
					userCity.setRequired(true);
					userCity.add(StringValidator.minimumLength(5));
					userCity.add(new NumbersAndSpecialCharactersValidator());
					userCity.add(new AjaxValidator(showInfos));
	
					
					final TextField<String> userStreet = new TextField<String>("Street", new PropertyModel<String>(this, "street"));
				 	userStreet.setRequired(true);
				 	userStreet.add(StringValidator.minimumLength(5));
				 	userStreet.add(new NumbersAndSpecialCharactersValidator());
					userStreet.add(new AjaxValidator(showInfos));
	
				 	
				 	final TextField<Integer> userHouseNumber = new TextField<Integer>("House Number", new PropertyModel<Integer>(this, "houseNumber"));
				 	userHouseNumber.setRequired(true);
				 	userHouseNumber.add(RangeValidator.range(1, 500));
					userHouseNumber.add(new AjaxValidator(showInfos));
					
					
					Form< ? > form = new Form<Void>("form") {
						private static final long serialVersionUID = 5204777274755390118L;
	
						@Override
						protected void onSubmit() {			
	
							PageParameters pageParameters = new PageParameters();
							pageParameters.add("Username", loginName);
							pageParameters.add("Age", age);
							pageParameters.add("Zip Code", zipCode);
							pageParameters.add("City", city);
							pageParameters.add("Street", street);
							pageParameters.add("House Number", houseNumber);
							
							getRequestCycle().setResponsePage(ShowInfos.class, pageParameters);
							super.onSubmit();
							
						}
					 };
					 add(form);
					 form.add(username);
					 form.add(userAge);
					 form.add(userZipCode);
					 form.add(userCity);
					 form.add(userStreet);
					 form.add(userHouseNumber);
					 form.add(showInfos);
				}
		}
}