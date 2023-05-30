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

import java.io.Serializable;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.IValidationError;

public class TextFieldControlGroup<T> extends AbstractControlGroup<T> {
	private static final long serialVersionUID = -944776898493154174L;

	private TextField<T> textField;
	
	public TextFieldControlGroup(String id, IModel<T> model) {
		super(id, model);

		textField = new TextField<T>("input", getModel()){
			private static final long serialVersionUID = 4925601760084153117L;

			@Override
			public void error(IValidationError error) {
				MessageSource source = new MessageSource();
				Serializable message = error.getErrorMessage(source);
				
				super.error(message);
			}
		};
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		StringResourceModel stringResourceModel = new StringResourceModel(getPropertyName()+".placeHolder", getResourceBase(), getModel());
		stringResourceModel.setDefaultValue("");
		textField.add(AttributeModifier.replace("placeHolder", stringResourceModel));
		WebMarkupContainer inputBox = new WebMarkupContainer("inputBox");
		addInputBoxGridSize(inputBox);
		inputBox.add(textField);
		add(new BootstrapControlGroupFeedback("controlGroup").add(inputBox));
	}

	@Override
	public FormComponent<T> getFormComponent() {
		return textField;
	}
}
