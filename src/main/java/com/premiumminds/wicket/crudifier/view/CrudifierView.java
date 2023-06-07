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
package com.premiumminds.wicket.crudifier.view;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.premiumminds.wicket.crudifier.IObjectRenderer;
import com.premiumminds.wicket.crudifier.LabelProperty;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class CrudifierView<T> extends Panel implements IGenericComponent<T, CrudifierView<T>> {
	private static final long serialVersionUID = -151637566983702881L;

	private CrudifierEntitySettings entitySettings = new CrudifierEntitySettings();
	private Map<Class<?>, IObjectRenderer<?>> renderers = new HashMap<Class<?>, IObjectRenderer<?>>();

	public CrudifierView(String id, IModel<T> model) {
		super(id, model);

		StringResourceModel stringResourceModel = new StringResourceModel("viewLegend", this, getModel());
		stringResourceModel.setDefaultValue("Unknown");
		add(new Label("viewLegend", stringResourceModel){
			private static final long serialVersionUID = -7854751811138463187L;

			@Override
			protected void onConfigure() {
				super.onConfigure();

				setVisible(!getDefaultModelObjectAsString().isEmpty());
			}
		});



	}
	@Override
	protected void onConfigure() {
		super.onConfigure();

		RepeatingView view = new RepeatingView("control");
		if(null != getModelObject()) {
			for(final String property : getPropertiesByOrder(getModelObject().getClass())){
				WebMarkupContainer control = new WebMarkupContainer(view.newChildId());
				view.addOrReplace(control);

				StringResourceModel stringResourceModel = new StringResourceModel("controls."+property+".label", this, getModel());
				stringResourceModel.setDefaultValue("Unknown");
				control.addOrReplace(new Label("label", stringResourceModel));
				control.addOrReplace(new LabelProperty("input", new PropertyModel<Object>(getModel(), property), renderers) {
					private static final long serialVersionUID = 8561120757563362569L;

					@Override
					protected String getResourceString(String key, String defaultValue) {
						return getLocalizer().getStringIgnoreSettings("controls."+property+"."+key, CrudifierView.this, null, defaultValue);
					}
				});
			}
		}
		addOrReplace(view);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}

	private Set<String> getPropertiesByOrder(Class<?> modelClass) {
		Set<String> properties = new LinkedHashSet<String>();

		for(String property : entitySettings.getOrderOfFields()){
			if(!entitySettings.getHiddenFields().contains(property))
				properties.add(property);
		}
		for(PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(modelClass)){
			if(!entitySettings.getHiddenFields().contains(descriptor.getName()) &&
					!properties.contains(descriptor.getName()) &&
					!descriptor.getName().equals("class"))
				properties.add(descriptor.getName());
		}

		return properties;
	}

	public CrudifierEntitySettings getEntitySettings() {
		return entitySettings;
	}

	public void setEntitySettings(CrudifierEntitySettings entitySettings) {
		this.entitySettings = entitySettings;
	}

	public Map<Class<?>, IObjectRenderer<?>> getRenderers() {
		return renderers;
	}

	public void setRenderers(Map<Class<?>, IObjectRenderer<?>> renderers) {
		this.renderers = renderers;
	}
}
