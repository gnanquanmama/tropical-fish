package com.mcoding.base.common.util.bean;

import cn.hutool.core.collection.CollectionUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 实体熟悉映射工具
 */
public abstract class BeanMapperUtils {

	private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

	public static <S, D> D map(S source, Class<D> clazz) {
		if (Objects.isNull(source)) {
			return null;
		}

		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(source, clazz);
	}

	public static <S, D> void map(S source, D destination) {
		if (Objects.isNull(source) || Objects.isNull(destination)) {
			return;
		}

		MapperFacade mapper = mapperFactory.getMapperFacade();
		mapper.map(source, destination);
	}

	public static <S, D> D map(S s, Type<S> sType, Type<D> dType) {
		if (s == null) {
			return null;
		}

		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(s, sType, dType);
	}

	public static <S, D> List<D> mapAsList(List<S> source, Class<D> clazz) {
		if (CollectionUtil.isEmpty(source)) {
			return Collections.emptyList();
		}

		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.mapAsList(source, clazz);
	}


}
