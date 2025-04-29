package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zhenyria.monro_consulting_bot.service.ScaleService;
import ru.zhenyria.monro_consulting_bot.service.SeasonService;
import ru.zhenyria.monro_consulting_bot.service.ShoesModelService;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;
import ru.zhenyria.monro_consulting_bot.util.ScaleMapper;
import ru.zhenyria.monro_consulting_bot.util.SeasonMapper;
import ru.zhenyria.monro_consulting_bot.util.ShoesMapper;
import ru.zhenyria.monro_consulting_bot.util.ShoesModelMapper;

@Controller
@RequestMapping("/shoes")
@RequiredArgsConstructor
public class ShoesViewController {
    private final ScaleMapper scaleMapper;
    private final ScaleService scaleService;
    private final SeasonMapper seasonMapper;
    private final SeasonService seasonService;
    private final ShoesMapper shoesMapper;
    private final ShoesModelMapper shoesModelMapper;
    private final ShoesModelService shoesModelService;
    private final ShoesService shoesService;

    @GetMapping
    public String getAllPage(Model model) {
        var shoes = shoesMapper.mapToDtos(shoesService.getAll());
        model.addAttribute("shoes", shoes);
        return "shoes";
    }

    @GetMapping("/creating")
    public String getCreatingPage(Model model) {
        var scales = scaleMapper.mapToDto(scaleService.getAll());
        var seasons = seasonMapper.mapToDtos(seasonService.getAll());
        var shoesModels = shoesModelMapper.mapToDtos(shoesModelService.getAll());

        model.addAttribute("scales", scales);
        model.addAttribute("seasons", seasons);
        model.addAttribute("shoesModels", shoesModels);
        return "shoesCreating";
    }
}
