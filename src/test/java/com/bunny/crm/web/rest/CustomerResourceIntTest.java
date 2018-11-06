package com.bunny.crm.web.rest;

import com.bunny.crm.CrmApp;

import com.bunny.crm.domain.Customer;
import com.bunny.crm.repository.CustomerRepository;
import com.bunny.crm.service.CustomerService;
import com.bunny.crm.service.dto.CustomerDTO;
import com.bunny.crm.service.mapper.CustomerMapper;
import com.bunny.crm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.bunny.crm.web.rest.TestUtil.sameInstant;
import static com.bunny.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bunny.crm.domain.enumeration.Level;
import com.bunny.crm.domain.enumeration.IntentionProduct;
import com.bunny.crm.domain.enumeration.Source;
/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrmApp.class)
public class CustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Level DEFAULT_LEVEL = Level.A;
    private static final Level UPDATED_LEVEL = Level.B;

    private static final String DEFAULT_PHONE = "AAAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBBB";

    private static final IntentionProduct DEFAULT_INTENTION_PRODUCT = IntentionProduct.HOUSING;
    private static final IntentionProduct UPDATED_INTENTION_PRODUCT = IntentionProduct.APARTMENT;

    private static final String DEFAULT_RESISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_RESISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_INTENTION_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_INTENTION_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_INTENTION_SPACE = "AAAAAAAAAA";
    private static final String UPDATED_INTENTION_SPACE = "BBBBBBBBBB";

    private static final Source DEFAULT_SOURCE = Source.CALL;
    private static final Source UPDATED_SOURCE = Source.INITIATIVE;

    private static final String DEFAULT_DEMAND_AREA = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_AREA = "BBBBBBBBBB";

    private static final Integer DEFAULT_VISIT_NUMBER = 1;
    private static final Integer UPDATED_VISIT_NUMBER = 2;

    private static final String DEFAULT_HOME_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_HOME_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL)
            .phone(DEFAULT_PHONE)
            .intentionProduct(DEFAULT_INTENTION_PRODUCT)
            .resistance(DEFAULT_RESISTANCE)
            .intentionPrice(DEFAULT_INTENTION_PRICE)
            .intentionSpace(DEFAULT_INTENTION_SPACE)
            .source(DEFAULT_SOURCE)
            .demandArea(DEFAULT_DEMAND_AREA)
            .visitNumber(DEFAULT_VISIT_NUMBER)
            .homeAddress(DEFAULT_HOME_ADDRESS)
            .profession(DEFAULT_PROFESSION)
            .remark(DEFAULT_REMARK)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .delFlag(DEFAULT_DEL_FLAG);
        return customer;
    }

    @Before
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomer.getIntentionProduct()).isEqualTo(DEFAULT_INTENTION_PRODUCT);
        assertThat(testCustomer.getResistance()).isEqualTo(DEFAULT_RESISTANCE);
        assertThat(testCustomer.getIntentionPrice()).isEqualTo(DEFAULT_INTENTION_PRICE);
        assertThat(testCustomer.getIntentionSpace()).isEqualTo(DEFAULT_INTENTION_SPACE);
        assertThat(testCustomer.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCustomer.getDemandArea()).isEqualTo(DEFAULT_DEMAND_AREA);
        assertThat(testCustomer.getVisitNumber()).isEqualTo(DEFAULT_VISIT_NUMBER);
        assertThat(testCustomer.getHomeAddress()).isEqualTo(DEFAULT_HOME_ADDRESS);
        assertThat(testCustomer.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testCustomer.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCustomer.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testCustomer.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCustomer.isDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setPhone(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].intentionProduct").value(hasItem(DEFAULT_INTENTION_PRODUCT.toString())))
            .andExpect(jsonPath("$.[*].resistance").value(hasItem(DEFAULT_RESISTANCE.toString())))
            .andExpect(jsonPath("$.[*].intentionPrice").value(hasItem(DEFAULT_INTENTION_PRICE.toString())))
            .andExpect(jsonPath("$.[*].intentionSpace").value(hasItem(DEFAULT_INTENTION_SPACE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].demandArea").value(hasItem(DEFAULT_DEMAND_AREA.toString())))
            .andExpect(jsonPath("$.[*].visitNumber").value(hasItem(DEFAULT_VISIT_NUMBER)))
            .andExpect(jsonPath("$.[*].homeAddress").value(hasItem(DEFAULT_HOME_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.intentionProduct").value(DEFAULT_INTENTION_PRODUCT.toString()))
            .andExpect(jsonPath("$.resistance").value(DEFAULT_RESISTANCE.toString()))
            .andExpect(jsonPath("$.intentionPrice").value(DEFAULT_INTENTION_PRICE.toString()))
            .andExpect(jsonPath("$.intentionSpace").value(DEFAULT_INTENTION_SPACE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.demandArea").value(DEFAULT_DEMAND_AREA.toString()))
            .andExpect(jsonPath("$.visitNumber").value(DEFAULT_VISIT_NUMBER))
            .andExpect(jsonPath("$.homeAddress").value(DEFAULT_HOME_ADDRESS.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .phone(UPDATED_PHONE)
            .intentionProduct(UPDATED_INTENTION_PRODUCT)
            .resistance(UPDATED_RESISTANCE)
            .intentionPrice(UPDATED_INTENTION_PRICE)
            .intentionSpace(UPDATED_INTENTION_SPACE)
            .source(UPDATED_SOURCE)
            .demandArea(UPDATED_DEMAND_AREA)
            .visitNumber(UPDATED_VISIT_NUMBER)
            .homeAddress(UPDATED_HOME_ADDRESS)
            .profession(UPDATED_PROFESSION)
            .remark(UPDATED_REMARK)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .delFlag(UPDATED_DEL_FLAG);
        CustomerDTO customerDTO = customerMapper.toDto(updatedCustomer);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomer.getIntentionProduct()).isEqualTo(UPDATED_INTENTION_PRODUCT);
        assertThat(testCustomer.getResistance()).isEqualTo(UPDATED_RESISTANCE);
        assertThat(testCustomer.getIntentionPrice()).isEqualTo(UPDATED_INTENTION_PRICE);
        assertThat(testCustomer.getIntentionSpace()).isEqualTo(UPDATED_INTENTION_SPACE);
        assertThat(testCustomer.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCustomer.getDemandArea()).isEqualTo(UPDATED_DEMAND_AREA);
        assertThat(testCustomer.getVisitNumber()).isEqualTo(UPDATED_VISIT_NUMBER);
        assertThat(testCustomer.getHomeAddress()).isEqualTo(UPDATED_HOME_ADDRESS);
        assertThat(testCustomer.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testCustomer.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCustomer.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testCustomer.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCustomer.isDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerDTO.class);
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        CustomerDTO customerDTO2 = new CustomerDTO();
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO2.setId(customerDTO1.getId());
        assertThat(customerDTO1).isEqualTo(customerDTO2);
        customerDTO2.setId(2L);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO1.setId(null);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerMapper.fromId(null)).isNull();
    }
}
