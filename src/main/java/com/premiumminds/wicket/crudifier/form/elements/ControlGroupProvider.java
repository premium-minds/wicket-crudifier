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

import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public interface ControlGroupProvider<T extends AbstractControlGroup<?>> extends Serializable {
	public T createControlGroup(String id, IModel<?> model, String name, Component component, boolean required, Class<?> type, CrudifierEntitySettings entitySettings);
}
