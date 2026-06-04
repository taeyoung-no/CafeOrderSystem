package cafeorder.sevice;

import java.util.List;

import cafeorder.domain.Option;
import cafeorder.repository.OptionRepository;

public class OptionService {
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option addOption(String name, int price) {
        Option option = new Option(null, name, price);
        return optionRepository.save(option);
    }

    public Option updateOption(Long id, String name, int price) {
        Option option = optionRepository.findById(id);
        if (option == null) {
            throw new IllegalArgumentException("존재하지 않는 옵션입니다. id=" + id);
        }
        option.setName(name);
        option.setPrice(price);
        return optionRepository.save(option);
    }

    public void removeOption(Long id) {
        if (optionRepository.findById(id) == null) {
            throw new IllegalArgumentException("존재하지 않는 옵션입니다. id=" + id);
        }
        optionRepository.deleteById(id);
    }

    public List<Option> getOptions() {
        return optionRepository.findAll();
    }
}
