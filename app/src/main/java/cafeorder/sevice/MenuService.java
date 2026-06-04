package cafeorder.sevice;

import java.util.List;

import cafeorder.domain.Menu;
import cafeorder.repository.MenuRepository;

public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu addMenu(String name, int price, String category) {
        Menu menu = new Menu(null, name, price, category);
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, String name, int price, String category, boolean available) {
        Menu menu = menuRepository.findById(id);
        if (menu == null) {
            throw new IllegalArgumentException("존재하지 않는 메뉴입니다. id=" + id);
        }
        menu.setName(name);
        menu.setPrice(price);
        menu.setCategory(category);
        menu.setAvailable(available);
        return menuRepository.save(menu);
    }

    public void removeMenu(Long id) {
        if (menuRepository.findById(id) == null) {
            throw new IllegalArgumentException("존재하지 않는 메뉴입니다. id=" + id);
        }
        menuRepository.deleteById(id);
    }

    public List<Menu> getMenus() {
        return menuRepository.findAll();
    }
}
