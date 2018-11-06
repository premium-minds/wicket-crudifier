/**
 * Copyright (C) 2014 Premium Minds.
 *
 * This file is part of wicket-crudifier.
 *
 * wicket-crudifier is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * wicket-crudifier is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with wicket-crudifier. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.wicket.crudifier.form.elements;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;

public class CheckboxControlGroup extends AbstractControlGroup<Boolean> {
	private static final long serialVersionUID = -2510616774931793758L;
	
	private CheckBox checkbox;
	
	public CheckboxControlGroup(String id, IModel<Boolean> model) {
		super(id, model);
		
		checkbox = new CheckBox("input", getModel());
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		StringResourceModel stringResourceModel = new StringResourceModel(getPropertyName()+".label", getResourceBase(), getModel());
		stringResourceModel.setDefaultValue(getPropertyName());
		add(new BootstrapControlGroupFeedback("controlGroup")
			.add(checkbox)
			.add(new Label("label", stringResourceModel))
		);
	}
	@Override
	public FormComponent<Boolean> getFormComponent() {
		return checkbox;
	}

}
