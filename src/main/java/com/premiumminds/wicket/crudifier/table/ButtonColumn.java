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
package com.premiumminds.wicket.crudifier.table;

import java.io.Serializable;
import java.util.Map;

import com.premiumminds.wicket.crudifier.IObjectRenderer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class ButtonColumn<T extends Serializable> implements
		IColumn<T>, Serializable {
	private static final long serialVersionUID = 2920108073341698814L;

	/**
	 * defines the button type. since this is a bootstrap implementation, see
	 * <a href="http://getbootstrap.com/css/#buttons">getbootstrap.com</a> for more information on each
	 * type
	 * 
	 * @author cfraga
	 * 
	 */
	public enum ButtonType {
		DEFAULT, PRIMARY, SUCCESS, INFO, WARNING, DANGER, LINK
	};

	private String propertyName;
	private ButtonType btnType;
	private ColumnAlign align;

	public ButtonColumn(String propertyName, ButtonType btnType,
			ColumnAlign align) {
		this.propertyName = propertyName;
		this.btnType = btnType;
		this.align = align;
	}

	public ButtonColumn(String propertyName) {
		this(propertyName, ButtonType.DEFAULT, ColumnAlign.CENTER);
	}

	public ButtonColumn(String propertyName, ColumnAlign align) {
		this(propertyName, ButtonType.DEFAULT, align);
	}

	public ButtonColumn(String propertyName, ButtonType btnType) {
		this(propertyName, btnType, ColumnAlign.CENTER);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Component createComponent(String id, final T object,
			Component resourceBase, Map<Class<?>, IObjectRenderer<?>> renderers) {
		ButtonPanel panel = new ButtonPanel(id);
		panel.add(new AjaxLink<Void>("button") {
			private static final long serialVersionUID = 4260049524761483954L;
			{
				add(AttributeModifier.append("class", getCssClass()));
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				ButtonColumn.this.onClick(Model.of(object), target);
			}
		}.add(new Label("label", resourceBase.getString(
				propertyName + ".button.label", new Model<String>(), "Button"))));
		return panel;
	}

	public ColumnAlign getAlign() {
		return align;
	}

	public abstract void onClick(IModel<T> model, AjaxRequestTarget target);

	public static class ButtonPanel extends Panel {
		private static final long serialVersionUID = -7728471050634320252L;

		public ButtonPanel(String id) {
			super(id);
		}
	}

	protected String getCssClass() {
		String rval = "btn ";
		switch (btnType) {
		case DANGER:
			rval += "btn-danger";
			break;
		case INFO:
			rval += "btn-info";
			break;
		case PRIMARY:
			rval += "btn-primary";
			break;
		case SUCCESS:
			rval += "btn-success";
			break;
		case WARNING:
			rval += "btn-warning";
			break;
		case LINK:
			rval += "btn-link";
			break;
		default:
			rval += "btn-default";
			break;
		}
		return rval;
	}

	public ButtonType getBtnType() {
		return btnType;
	}

	public void setBtnType(ButtonType btnType) {
		this.btnType = btnType;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
