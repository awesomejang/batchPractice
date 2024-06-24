package study.batchpractice.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

public class CompositeItemReader<I, O> implements ItemReader<List<I>> {

    private final ItemReader<I> delegate;
    private final int chunkSize;

    public CompositeItemReader(ItemReader<I> delegate, int chunkSize) {
        this.delegate = delegate;
        this.chunkSize = chunkSize;
    }

    @Override
    public List<I> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<I> items = new ArrayList<>(chunkSize);
        for (int i = 0; i < chunkSize; i++) {
            I item = delegate.read();
            if (item != null) {
                items.add(item);
            } else {
                break;
            }
        }
        return items.isEmpty() ? null : items;
    }
}
