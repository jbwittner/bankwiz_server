package fr.bankwiz.server.infrastructure.transformer;

import java.util.ArrayList;
import java.util.List;

public interface Transformer<I, O> {

    O transform(I input);

    default List<O> transformAll(final List<I> inputList) {
        final List<O> outpuList = new ArrayList<>();
        inputList.forEach(input -> outpuList.add(this.transform(input)));
        return outpuList;
    }
}
