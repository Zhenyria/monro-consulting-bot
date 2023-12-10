package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.Customer;
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

    @Transactional
    public boolean removeByChatMemberId(Long id) {
        return repository.removeByChatMemberId(id) > 0;
    }
}
