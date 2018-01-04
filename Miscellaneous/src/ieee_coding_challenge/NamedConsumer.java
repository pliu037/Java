package ieee_coding_challenge;

import java.util.function.Consumer;

class NamedConsumer<T> {

    final String methodName;
    Consumer<T> c;

    NamedConsumer(String methodName, Consumer<T> c) {
        this.methodName = methodName;
        this.c = c;
    }
}
