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
package com.premiumminds.wicket.crudifier.form;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

public abstract class EntityProvider<T> implements Serializable {
	private static final long serialVersionUID = -6207101592626765797L;

	public abstract List<T> load();
	
	public IChoiceRenderer<T> getRenderer(){
		return new ChoiceRenderer<T>();
	}
}
