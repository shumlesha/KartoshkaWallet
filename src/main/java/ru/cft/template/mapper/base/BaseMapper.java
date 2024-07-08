package ru.cft.template.mapper.base;


public interface BaseMapper<E, D> {
    D toDTO(E entity);
}
