package org.libx4j.jsonx.generator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RuntimeTest {
  public void test(final Class<?>[] classes) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    final List<Trial<?>> trials = TrialFactory.createTrials(classes);
    for (final Trial<?> trial : trials) {
      trial.invoke();
    }
  }
}