package cafeorder.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cafeorder.domain.Option;
import cafeorder.repository.OptionRepository;

public class InMemoryOptionRepository implements OptionRepository {
    private final Map<Long, Option> store = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public Option save(Option option) {
        if (option.getId() == null) {
            option.setId(sequence++);
        }
        store.put(option.getId(), option);
        return option;
    }

    @Override
    public Option findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Option> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
