/* Copyright (c) 2023 JSONx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.jsonx;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

final class RuntimeUtil {
  public static Runnable onExit(final Runnable willCallExit) throws Throwable {
    Objects.requireNonNull(willCallExit);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        synchronized (willCallExit) {
          willCallExit.notify();
        }

        try {
          Thread.sleep(Long.MAX_VALUE);
        }
        catch (final Exception e) {
          throw new RuntimeException(e);
        }
      }
    });

    final AtomicReference<Throwable> exception = new AtomicReference<>();
    synchronized (willCallExit) {
      new Thread() {
        @Override
        public void run() {
          synchronized (willCallExit) {
          }

          try {
            willCallExit.run();
          }
          catch (final Throwable t) {
            exception.set(t);
          }
          finally {
            synchronized (willCallExit) {
              willCallExit.notify();
            }
          }
        }
      }.start();

      willCallExit.wait();
    }

    final Throwable t = exception.get();
    if (t != null)
      throw t;

    return () -> {
      new Thread() {
        @Override
        public void run() {
          try {
            Thread.sleep(100);
          }
          catch (final InterruptedException ignore) {
          }
          Runtime.getRuntime().halt(0);
        }
      }.start();
    };
  }

  private RuntimeUtil() {
  }
}