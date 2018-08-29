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

import static org.junit.Assert.*;

import java.util.List;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.ValidatorAdapter;
import org.junit.Test;

import com.premiumminds.webapp.wicket.testing.AbstractComponentTest;
import com.premiumminds.webapp.wicket.validators.HibernateValidatorProperty;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings.GridSize;

public class AbstractControlGroupTest extends AbstractComponentTest {
	private class TestGroup extends AbstractControlGroup<String> {
		private static final long serialVersionUID = 1L;

		private TextField<String> field;
		protected WebMarkupContainer box;

		public TestGroup(String id, IModel<String> model) {
			super(id, model);

			field = new TextField<String>("field", model);
			box = new WebMarkupContainer("box");
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();

			add(field);
			add(box);
		};

		@Override
		public FormComponent<String> getFormComponent() {
			return field;
		}
	};

	@Test
	public void testInit() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();

		TestGroup tg = new TestGroup("test", null);
		tg.init("prop", tg, false, String.class, settings);

		assertEquals("prop", tg.getPropertyName());
		assertEquals(tg, tg.getResourceBase());
		assertEquals(String.class, tg.getType());
		assertEquals(settings, tg.getEntitySettings());
	}

	@Test
	public void testGetModel() {
		IModel<String> str = new Model<String>("model");

		TestGroup tg = new TestGroup("test", str);

		assertEquals(str, tg.getModel());
	}

	@Test
	public void testInitialization() {
		TestGroup tg = new TestGroup("test", null);
		tg.init("prop", tg, true, String.class, null);
		startTest(tg);

		replayAll();

		verifyAll();

		getTester().assertComponent(tg.getPageRelativePath(), TestGroup.class);
		getTester().assertComponent(tg.field.getPageRelativePath(), TextField.class);
		getTester().assertComponent(tg.box.getPageRelativePath(), WebMarkupContainer.class);
		getTester().assertRequired(tg.field.getPageRelativePath());
		assertEquals("prop", tg.field.getLabel().getObject());

		List<? extends Behavior> behaviors = tg.field.getBehaviors();
		assertEquals(1, behaviors.size());
		assertTrue(behaviors.get(0) instanceof ValidatorAdapter);
		assertTrue(((ValidatorAdapter<?>)behaviors.get(0)).getValidator() instanceof HibernateValidatorProperty);
	}

	@Test
	public void testInitRequiredFalse() {
		TestGroup tg = new TestGroup("test", null);
		tg.init("prop", tg, false, String.class, null);
		startTest(tg);

		replayAll();

		verifyAll();

		getTester().assertNotRequired(tg.field.getPageRelativePath());
	}

	@Test
	public void testAddInputBoxGridSizeNoSetting() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();

		TestGroup tg = new TestGroup("test", null) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				addInputBoxGridSize(box);
			}
		};

		tg.init("prop", tg, false, String.class, settings);
		startTest(tg);

		replayAll();

		verifyAll();

		assertEquals("col-lg-10", getTester().getTagByWicketId(tg.box.getId()).getAttribute("class"));
	}

	@Test
	public void testAddInputBoxGridSize() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();
		TestGroup tg[] = new TestGroup[10];
		
		for (int i = 0; i < tg.length; i++) {
			tg[i] = new TestGroup("test", null) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onInitialize() {
					super.onInitialize();
					addInputBoxGridSize(box);
				}
			};

			tg[i].init("prop", tg[i], false, String.class, settings);
			settings.getGridFieldsSizes().put("prop", GridSize.valueOf("COL" + (i + 1)));
			startTest(tg[i]);

			replayAll();

			verifyAll();

			assertEquals("col-lg-" + (i + 1), getTester().getTagByWicketId(tg[i].box.getId()).getAttribute("class"));

			resetAll();

			resetTest();
		}
	}
}
