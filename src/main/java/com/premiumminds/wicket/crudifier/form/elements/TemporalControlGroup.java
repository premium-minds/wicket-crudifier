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
import java.time.LocalDateTime;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidationError;

import com.premiumminds.webapp.wicket.TemporalTextField;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;
import com.premiumminds.webapp.wicket.bootstrap.BootstrapTemporalDatepicker;

public class TemporalControlGroup extends AbstractControlGroup<LocalDateTime> {
	private static final long serialVersionUID = 7519983535463694024L;

	private TemporalTextField<LocalDateTime> dateField;
	
	public TemporalControlGroup(String id, IModel<LocalDateTime> model) {
		super(id, model);
		
		BootstrapTemporalDatepicker<LocalDateTime> datepicker = new BootstrapTemporalDatepicker<LocalDateTime>("datepicker"){
			private static final long serialVersionUID = -1294334224980199521L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if(isEnabledInHierarchy()) tag.append("class", "input-append", " ");
			}
		};
		
		dateField = new TemporalTextField<LocalDateTime>("input", getModel(), LocalDateTime.class){
			private static final long serialVersionUID = 4925601760084153117L;

			@Override
			public void error(IValidationError error) {
				MessageSource source = new MessageSource();
				Serializable message = error.getErrorMessage(source);
				
				super.error(message);
			}
		};
		
		datepicker.add(dateField);
		datepicker.add(new WebMarkupContainer("icon"){
			private static final long serialVersionUID = -4412622222987841668L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				//don't display icon if it is disabled
				setVisible(dateField.isEnabledInHierarchy());
			}
		});
		add(new BootstrapControlGroupFeedback("controlGroup").add(datepicker));

		
	}

	@Override
	public FormComponent<LocalDateTime> getFormComponent() {
		return dateField;
	}

}
