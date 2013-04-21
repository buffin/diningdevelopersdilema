package org.diningdevelopers.core.util;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import org.diningdevelopers.core.util.MappingService;

import java.math.BigDecimal;

import org.junit.Test;

public class MappingServiceTest {

	MappingService mappingService = new MappingService();

	@Test
	public void mappingToModelWorks() throws Exception {
		Entity entity = createEntity(1L);
		Model model = mappingService.map(entity, Model.class);

		assertEquals(entity.getId(), model.getId());
		assertEquals(entity.getString(), model.getString());
		assertEquals(entity.getAmount().doubleValue(), model.getAmount());
	}
	
	@Test
	public void onNullSourceMapReturnsNull() throws Exception {
		assertNull(mappingService.map(null, Model.class));
	}
	
	@Test
	public void onNullSourceMapCollectionReturnsNull() throws Exception {
		assertNull(mappingService.mapCollection(null, Model.class));
	}

	private Entity createEntity(long id) {
		Entity entity = new Entity();
		entity.setId(id);
		entity.setString("String " + id);
		entity.setAmount(BigDecimal.valueOf(id).add(BigDecimal.valueOf(15, 2)));
		return entity;
	}

	public static class Entity {
		private Long id;
		private String string;
		private BigDecimal amount;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

	}

	public static class Model {
		private Long id;
		private String string;
		private Double amount;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

	}
}
