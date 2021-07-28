package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.data.CustomerModifyDTO;
import recyclemug.ProjectMug.domain.user.*;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;
import recyclemug.ProjectMug.repository.CustomerRepository;
import recyclemug.ProjectMug.repository.UserRepositoryInterface;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final PasswordEncoder passwordEncoder;
    private final CustomerOrderRepository customerOrderRepository;

    /**
     * Customer 회원가입 메서드
     * @param customer
     * @return
     */
    @Transactional
    public Long join(Customer customer) throws RuntimeException{
        if (validatePassword(customer.getPassword()) && validateDuplicate(customer.getEmail())) {
            Authority authority = Authority.builder().authorityName("ROLE_CUSTOMER").build();

            customer.setPassword("{noop}" + customer.getPassword());
            customer.setActivated(true);
            customer.setAuthorities(Collections.singleton(authority));

            customerRepository.save(customer);
            return customer.getId();
        } else {
            throw new RuntimeException("You need to check ID or PW");
        }
    }

    @Transactional
    public void modifyProfilePicture(String pictureAddress, Customer customer) {
        customer.setProfilePictureAddress(pictureAddress);
    }


    /**
     * Customer 정보 수정 메서드 parameter 중 password, phoneNumber, nickname 변경
     * @param customer
     * @param customerDTO
     */
    @Transactional
    public void modifyCustomerInfo(Customer customer, CustomerModifyDTO customerDTO) throws RuntimeException{
        if (validatePassword(customerDTO.getPassword())) {
            customer.setPassword("{noop}" + customerDTO.getPassword());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setNickname(customerDTO.getNickname());
        } else {
            throw new RuntimeException("Wrong type Password");
        }
    }

    // 중복되는 이메일을 가지는 회원이 있는지를 판별
    public boolean validateDuplicate(String email) {
        List<Customer> findUsers = customerRepository.findByEmail(email);
        return findUsers.isEmpty();
    }

    // 패스워드 체크 8자리 이상 20자리 이하 대문자, 소문자, 숫자, 특수문자 중 3가지 사용
    public boolean validatePassword(String pw) throws RuntimeException{
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~$@$!%*#?&*])[A-Za-z[0-9]~$@$!%*#?&*]{8,20}$"; // 영문, 숫자, 특수문자
        Matcher match;

        match = Pattern.compile(pattern).matcher(pw);
        return match.find();
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
