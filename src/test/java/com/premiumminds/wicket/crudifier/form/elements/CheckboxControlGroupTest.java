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

import org.apache.wicket.markup.html.form.CheckBox;
import org.junit.Test;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapControlGroupFeedback;
import com.premiumminds.webapp.wicket.testing.AbstractComponentTest;

public class CheckboxControlGroupTest extends AbstractComponentTest {
	@Test
	public void testGetFormComponent() {
		CheckboxControlGroup ccg = new CheckboxControlGroup("test", null);

		assertTrue(ccg.getFormComponent() instanceof CheckBox);
	}

	@Test
	public void testInitialization() {
		CheckboxControlGroup ccg = new CheckboxControlGroup("test", null);
		ccg.init("prop", ccg, true, boolean.class, null);
		startTest(ccg);

		replayAll();

		verifyAll();

		getTester().assertComponent(ccg.getPageRelativePath(), CheckboxControlGroup.class);
		getTester().assertComponent(ccg.getFormComponent().getParent().getPageRelativePath(), BootstrapControlGroupFeedback.class);
		getTester().assertComponent(ccg.getFormComponent().getPageRelativePath(), CheckBox.class);
	}
}
