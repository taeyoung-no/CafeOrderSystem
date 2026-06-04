package cafeorder.repository;

import java.util.List;

import cafeorder.domain.Menu;

public interface MenuRepository {
    Menu save(Menu menu);
    Menu findById(Long id);
    List<Menu> findAll();
    void deleteById(Long id);
}
