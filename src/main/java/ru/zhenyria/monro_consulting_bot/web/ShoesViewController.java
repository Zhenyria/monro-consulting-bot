package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;
import ru.zhenyria.monro_consulting_bot.util.ShoesMapper;

@Controller
@RequestMapping("/shoes")
@RequiredArgsConstructor
public class ShoesViewController {
    private final ShoesMapper shoesMapper;
    private final ShoesService shoesService;

    @GetMapping
    public String get(Model model) {
        var shoes = shoesMapper.mapToDtos(shoesService.getAll());
        model.addAttribute("shoes", shoes);
        return "shoes";
    }
}
