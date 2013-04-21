package org.diningdevelopers.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

@ApplicationScoped
public class MappingService {

	private Mapper mapper = new DozerBeanMapper();

	public <S, D> void map(S source, D destination) {
		if (source != null) {
			mapper.map(source, destination);
		}
	}

	public <S, D> D map(S source, Class<D> destinationClass) {
		if (source == null) {
			return null;
		}
		return mapper.map(source, destinationClass);
	}

	public <S, D> List<D> mapCollection(Collection<S> sourceList, Class<D> destinationClass) {
		if (sourceList == null) {
			return null;
		}
		List<D> list = new ArrayList<D>();

		for (S source : sourceList) {
			list.add(mapper.map(source, destinationClass));
		}

		return list;
	}
}
