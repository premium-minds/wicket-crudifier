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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;
import com.premiumminds.webapp.wicket.testing.AbstractComponentTest;
import com.premiumminds.wicket.crudifier.IObjectRenderer;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings.GridSize;
import com.premiumminds.wicket.crudifier.form.EntityProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionControlGroupTest extends AbstractComponentTest {
	private class TestProvider extends EntityProvider<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public List<String> load() {
			return Arrays.asList("123", "456", "789");
		}
	}

	private class TestRenderer implements IObjectRenderer<String> {
		private static final long serialVersionUID = 1L;

		private boolean called = false;

		@Override
		public String render(String object) {
			called = true;
			return object;
		}
	}

	@Test
	public void testGetFormComponent() {
		CollectionControlGroup<String> ccg = new CollectionControlGroup<String>("test",  null);

		assertTrue(ccg.getFormComponent() instanceof ListMultipleChoice);
	}

	@Test
	public void testValidationError() {
		CollectionControlGroup<String> ccg = new CollectionControlGroup<String>("test",  null);
		ccg.getFormComponent().error(new IValidationError() {
			private static final long serialVersionUID = 1L;

			@Override
			public Serializable getErrorMessage(IErrorMessageSource messageSource) {
				return "Test error";
			}
		});

		assertEquals(1, ccg.getFormComponent().getFeedbackMessages().size());
		assertEquals("Test error", ccg.getFormComponent().getFeedbackMessages().first().getMessage());
	}

	@Test
	public void testInitialization() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();
		CollectionControlGroup<String> ccg = new CollectionControlGroup<String>("test",  null);
		ccg.init("prop", ccg, true, boolean.class, settings);
		ccg.setConfiguration(new TestProvider(), new HashMap<Class<?>, IObjectRenderer<?>>());
		settings.getGridFieldsSizes().put("prop", GridSize.valueOf("COL3"));
		startTest(ccg);

		replayAll();

		verifyAll();

		getTester().assertComponent(ccg.getPageRelativePath(), CollectionControlGroup.class);
		getTester().assertComponent(ccg.getFormComponent().getParent().getParent().getPageRelativePath(), BootstrapControlGroupFeedback.class);
		getTester().assertComponent(ccg.getFormComponent().getParent().getPageRelativePath(), WebMarkupContainer.class);
		getTester().assertComponent(ccg.getFormComponent().getPageRelativePath(), ListMultipleChoice.class);
		assertEquals("col-lg-3", getTester().getTagByWicketId(ccg.getFormComponent().getParent().getId()).getAttribute("class"));
	}

	@Test
	public void testCustomRenderer() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();
		Map<Class<?>, IObjectRenderer<?>> map = new HashMap<Class<?>, IObjectRenderer<?>>();
		TestRenderer renderer = new TestRenderer();
		map.put(String.class, renderer);
		CollectionControlGroup<String> ccg = new CollectionControlGroup<String>("test",  null);
		ccg.init("prop", ccg, true, boolean.class, settings);
		ccg.setConfiguration(new TestProvider(), map);
		startTest(ccg);

		replayAll();

		verifyAll();

		assertTrue(renderer.called);
	}
}
