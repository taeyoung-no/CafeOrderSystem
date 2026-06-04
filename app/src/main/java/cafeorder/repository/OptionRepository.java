package cafeorder.repository;

import java.util.List;

import cafeorder.domain.Option;

public interface OptionRepository {
    Option save(Option option);
    Option findById(Long id);
    List<Option> findAll();
    void deleteById(Long id);
}
