package cafeorder.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cafeorder.domain.Menu;
import cafeorder.repository.MenuRepository;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<Long, Menu> store = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(sequence++);
        }
        store.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Menu findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
