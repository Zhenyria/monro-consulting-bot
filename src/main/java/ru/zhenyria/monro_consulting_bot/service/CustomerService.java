package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.model.Customer;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.repository.CustomerRepository;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final ConsultationRequestService consultationRequestService;
    private final CustomerRepository repository;

    @Transactional
    public void save(Customer customer) {
        repository.save(customer);
    }

    public Customer get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Customer getWithWishedShoes(Long id) {
        return repository.findWithWishedShoesByChatMemberId(id);
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
            if (Objects.equals(alreadyWishedShoes.getId(), shoes.getId())) {
                return false;
            }
        }

        wishedShoes.add(shoes);
        this.save(customer);
        return true;
    }

    /**
     * Removes shoes from customers' wishes lists
     *
     * @param shoesId the id of the removing shoes
     * @see CustomerService#removeShoesFromWishList(Long, Integer)
     */
    @Transactional
    public void removeShoesFromWishLists(Integer shoesId) {
        repository.findAllByWishedShoes(shoesId)
                  .stream()
                  .map(Customer::getChatMemberId)
                  .forEach(id -> removeShoesFromWishList(id, shoesId));
    }

    /**
     * Removes shoes from customer's wish list
     *
     * @param id      the id of the customer
     * @param shoesId the id of the removing shoes
     */
    @Transactional
    public void removeShoesFromWishList(Long id, Integer shoesId) {
        var customer = this.get(id);
        var wishedShoes = customer.getWishedShoes();

        var iterator = wishedShoes.iterator();

        while (iterator.hasNext()) {
            var shoes = iterator.next();

            if (Objects.equals(shoes.getId(), shoesId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeByChatMemberId(Long id) {
        consultationRequestService.removeAllByCustomerId(id);
        repository.removeByChatMemberId(id);
        repository.flush();
    }
}
