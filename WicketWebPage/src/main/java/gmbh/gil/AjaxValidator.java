package gmbh.gil;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

public class AjaxValidator extends AjaxFormComponentUpdatingBehavior {
	private static final long serialVersionUID = 7266729164243993619L;
	
	private final Component feedback;
	public AjaxValidator(Component feedback) {
		super("blur");
		this.feedback = feedback;
	}
	protected void onUpdate(AjaxRequestTarget target) {
		target.add(feedback);
	}
	protected void onError(AjaxRequestTarget target, RuntimeException e) {
		target.add(feedback);
	}
}
