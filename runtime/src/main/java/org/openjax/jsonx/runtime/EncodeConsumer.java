package org.openjax.jsonx.runtime;

import java.util.Objects;

/**
 * Represents an operation that accepts one object argument of type {@code <T>}
 * and two {@code int} arguments, and returns no result. Unlike most other
 * functional interfaces, {@code EncodeConsumer} is expected to operate via
 * side-effects.
 *
 * @param <T> The type of the first argument to the operation.
 */
@FunctionalInterface
public interface EncodeConsumer<T> {
  /**
   * Performs this operation on the given arguments.
   *
   * @param a The first input argument.
   * @param b The second input argument.
   * @param c The third input argument.
   */
  void accept(T a, int b, int c);

  /**
   * Returns a composed {@code EncodeConsumer} that performs, in sequence, this
   * operation followed by the {@code after} operation. If performing either
   * operation throws an exception, it is relayed to the caller of the composed
   * operation. If performing this operation throws an exception, the
   * {@code after} operation will not be performed.
   *
   * @param after The operation to perform after this operation.
   * @return A composed {@code EncodeConsumer} that performs in sequence this
   *         operation followed by the {@code after} operation
   * @throws NullPointerException if {@code after} is null
   */
  default EncodeConsumer<T> andThen(final EncodeConsumer<? super T> after) {
    Objects.requireNonNull(after);
    return (a, b, c) -> {
      accept(a, b, c);
      after.accept(a, b, c);
    };
  }
}