package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.Customer;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    @Transactional
    public void save(Customer customer) {
        repository.save(customer);
    }

    public Customer get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public long getChatMembersTotalCount() {
        return repository.getTotalCount();
    }

    /**
     * Adds shoes to customer's wish list
     *
     * @param id    the id of the customer
     * @param shoes the shoes
     * @return {@code false} if the shoes is wished already else {@code true}
     */
    @Transactional
    public boolean addShoesToWishList(Long id, Shoes shoes) {
        var customer = this.get(id);
        var wishedShoes = customer.getWishedShoes();

        for (var alreadyWishedShoes : wishedShoes) {
            if (alreadyWishedShoes.getVendorCode().equals(shoes.getVendorCode())) {
                return false;
            }
        }

        wishedShoes.add(shoes);
        this.save(customer);
        return true;
    }

    /**
     * Removes shoes from customer's wish list
     *
     * @param id              the id of the customer
     * @param shoesVendorCode the id of the removing shoes
     */
    @Transactional
    public void removeShoesFromWishList(Long id, String shoesVendorCode) {
        var customer = this.get(id);
        var wishedShoes = customer.getWishedShoes();

        var iterator = wishedShoes.iterator();

        while (iterator.hasNext()) {
            var shoes = iterator.next();

            if (shoes.getVendorCode().equals(shoesVendorCode)) {
                iterator.remove();
                return;
            }
        }
    }

    @Transactional
    public boolean removeByChatMemberId(Long id) {
        return repository.removeByChatMemberId(id) > 0;
    }
}
