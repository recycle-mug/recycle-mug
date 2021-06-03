package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.repository.CustomerRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Long join(Customer customer) {
        validateDuplicate(customer); // 중복 회원 체크
        validatePassword(customer);
        customerRepository.save(customer);
        return customer.getId();

    }

    // 중복되는 이메일을 가지는 회원이 있는지를 판별
    public void validateDuplicate(Customer customer) {
        List<Customer> findUsers = customerRepository.findByEmail(customer.getEmail());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 패스워드 체크 8자리 이상 20자리 이하 대문자, 소문자, 숫자, 특수문자 중 3가지 사용
    public void validatePassword(Customer customer) {
        String pw = customer.getPassword();
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~$@$!%*#?&*])[A-Za-z[0-9]~$@$!%*#?&*]{8,20}$"; // 영문, 숫자, 특수문자
        Matcher match;

        match = Pattern.compile(pattern).matcher(pw);
        if(!match.find()) {
            throw new IllegalStateException("비밀번호를 양식에 맞게 작성해야 합니다");
        }
    }

    public Customer findById(Long id) {
        return customerRepository.findOne(id);
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
