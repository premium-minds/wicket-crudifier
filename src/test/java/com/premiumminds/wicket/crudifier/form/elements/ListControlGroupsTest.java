package com.premiumminds.wicket.crudifier.form.elements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import com.premiumminds.webapp.wicket.testing.AbstractComponentTest;
import com.premiumminds.wicket.crudifier.IObjectRenderer;
import com.premiumminds.wicket.crudifier.form.CrudifierEntitySettings;
import com.premiumminds.wicket.crudifier.form.EntityProvider;

public class ListControlGroupsTest extends AbstractComponentTest {
	private class TestPOJO implements Serializable {
		private static final long serialVersionUID = 1L;
	}

	private class TestList extends ListControlGroups<TestPOJO> {
		private static final long serialVersionUID = 1L;

		public TestList(String id, IModel<TestPOJO> model, CrudifierEntitySettings entitySettings, Map<Class<?>, IObjectRenderer<?>> renderers) {
			super(id, model, entitySettings, renderers);
		}

		@Override
		protected EntityProvider<?> getEntityProvider(String name) {
			return null;
		}
	}

	@Test
	public void testGetControlGroupsTypesMap() {
		TestList tl = new TestList("test", null, null, null);

		@SuppressWarnings("rawtypes")
		Map<Class<?>, Class<? extends AbstractControlGroup>> map = tl.getControlGroupsTypesMap();
		assertEquals(15, map.size());
		assertEquals(DateControlGroup.class, map.get(Date.class));
		assertEquals(TemporalControlGroup.class, map.get(LocalDateTime.class));
		assertEquals(TemporalControlGroup.class, map.get(Temporal.class));
		assertEquals(TextFieldControlGroup.class, map.get(String.class));
		assertEquals(TextFieldControlGroup.class, map.get(Integer.class));
		assertEquals(TextFieldControlGroup.class, map.get(int.class));
		assertEquals(TextFieldControlGroup.class, map.get(Long.class));
		assertEquals(TextFieldControlGroup.class, map.get(long.class));
		assertEquals(TextFieldControlGroup.class, map.get(Double.class));
		assertEquals(TextFieldControlGroup.class, map.get(double.class));
		assertEquals(TextFieldControlGroup.class, map.get(BigDecimal.class));
		assertEquals(TextFieldControlGroup.class, map.get(BigInteger.class));
		assertEquals(CheckboxControlGroup.class, map.get(Boolean.class));
		assertEquals(CheckboxControlGroup.class, map.get(boolean.class));
		assertEquals(CollectionControlGroup.class, map.get(Set.class));
	}

	@Test
	public void testGetModel() {
		IModel<TestPOJO> model = new Model<TestPOJO>(new TestPOJO());
		TestList tl = new TestList("test", model, null, null);

		assertEquals(model, tl.getModel());
	}

	@Test
	public void testInitializationEmptyObject() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();
		IModel<TestPOJO> model = new Model<TestPOJO>(new TestPOJO());
		TestList tl = new TestList("test", model, settings, null);
		startTest(tl);

		replayAll();

		verifyAll();

		getTester().assertComponent(tl.getPageRelativePath(), TestList.class);
		getTester().assertComponent(tl.stream().iterator().next().getPageRelativePath(), RepeatingView.class);
		RepeatingView v = (RepeatingView)tl.stream().iterator().next();
		Iterator<Component> i = v.iterator();
		assertFalse(i.hasNext());
	}

	@Test
	public void testInitializationNonEmptyObject() {
		CrudifierEntitySettings settings = new CrudifierEntitySettings();
		IModel<TestPOJO> model = new Model<TestPOJO>(new TestPOJO() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public int getField() { return 0; }
			@SuppressWarnings("unused")
			public void setField(int value) {}
		});

		TestList tl = new TestList("test", model, settings, null);
		startTest(tl);

		replayAll();

		verifyAll();

		getTester().assertComponent(tl.getPageRelativePath(), TestList.class);
		getTester().assertComponent(tl.stream().iterator().next().getPageRelativePath(), RepeatingView.class);
		RepeatingView v = (RepeatingView)tl.stream().iterator().next();
		Iterator<Component> i = v.iterator();
		assertTrue(i.hasNext());
		getTester().assertComponent(i.next().getPageRelativePath(), TextFieldControlGroup.class);
		assertFalse(i.hasNext());
	}

//	@Test
//	public void testGetResourceBase() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetFieldsControlGroup() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetControlGroupProviders() {
//		fail("Not yet implemented");
//	}
}
