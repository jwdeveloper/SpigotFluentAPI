package jw.fluent.plugin.implementation.modules.mapper;

import java.util.List;

public interface FluentMapper
{
      <Input,Output> Output map(Input input, Class<Output> outputClass);

      <Output> List<Output> mapAll(List<?> inputs, Class<Output> outputClass);
}
