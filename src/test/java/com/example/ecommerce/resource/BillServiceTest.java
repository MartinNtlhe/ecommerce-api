package com.example.ecommerce.resource;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.service.bean.BillServiceImpl;
import com.example.ecommerce.service.bean.ProductServiceImpl;
import com.example.ecommerce.service.bean.UserServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.ecommerce.util.DateUtil.*;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {

    @Mock
    private IBillRepository iBillRepository;
    @Mock
    private IProductRepository iProductRepository;
    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private IBillItemRepository iBillItemRepository;

    @InjectMocks
    private BillServiceImpl billServiceImpl;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void shouldGenerateNewEmployeeBill() throws JSONException {
        final User user = buildUser("12343", buildUserType(Type.EMPLOYEE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");

        final Bill inputBill = buildBill("furniture", user);
        final Bill expectedBill = Mockito.mock(Bill.class);

        when(iBillRepository.save(inputBill)).thenReturn(expectedBill);
        final Bill actualBill = billServiceImpl.placeBill(inputBill);

        verify(iBillRepository, times(1)).save(inputBill);
        assertEquals(expectedBill, actualBill);

        final List<BillItem> billItems = new ArrayList<BillItem>() {{
            add(buildBillItem(inputBill, buildProduct("76654", "blwablwa", "Blaw blaw", 100.00), 100.00, 1));
            add(buildBillItem(inputBill, buildProduct("24242", "gfhhgf", "fh hfg fh", 600.00), 600.00, 3));
            add(buildBillItem(inputBill, buildProduct("55453", " khj k", "kj kjkhjk", 5700.00), 5700.00, 7));
        }};

        double total = 0D;
        double billSubTotal = 0D;
        for (BillItem detail: billItems) {
            billSubTotal  = billSubTotal + (detail.getQuantity()*detail.getPrice());
        }

        double applicablePercentageDeductions = 0D;
        double rewardsDiscounted = 0D;
        double grossTotal = billSubTotal;
        double percentageDeductions = 0D;
        Type userType = inputBill.getUserReference().getUserType().getType();
        switch (userType) {
            case EMPLOYEE:
                applicablePercentageDeductions = 30;
                break;
            case AFFILIATE:
                applicablePercentageDeductions = 10;
                break;
            case CUSTOMER:
                Calendar joinedDate = returnCalendar(inputBill.getUserReference().getJoinDate());
                Calendar currentDate = returnCalendar(new Date());
                int yearsInterval = currentDate.get(YEAR) - joinedDate.get(YEAR);
                if(yearsInterval > 2){
                    applicablePercentageDeductions = 5;
                }
                break;
        }

        if(billSubTotal % 100 == 0){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(!inputBill.getBillType().equals("groceries")){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(applicablePercentageDeductions > 0){
            percentageDeductions = (billSubTotal * (applicablePercentageDeductions / 100));
        }

        if(rewardsDiscounted > 0){
            total = (billSubTotal - rewardsDiscounted) - percentageDeductions;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bill", inputBill);
        jsonObject.put("subTotal", grossTotal);
        jsonObject.put("% Deduction", applicablePercentageDeductions);
        jsonObject.put("Monetary % Deduction", percentageDeductions);
        jsonObject.put("Rewards Discount", rewardsDiscounted);
        jsonObject.put("netTotal", total);

        System.out.print(jsonObject);

    }

    @Test
    public void shouldGenerateNewAffiliateBill() throws JSONException {
        final User user = buildUser("12343", buildUserType(Type.AFFILIATE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");

        final Bill inputBill = buildBill("furniture", user);
        final Bill expectedBill = Mockito.mock(Bill.class);

        when(iBillRepository.save(inputBill)).thenReturn(expectedBill);
        final Bill actualBill = billServiceImpl.placeBill(inputBill);

        verify(iBillRepository, times(1)).save(inputBill);
        assertEquals(expectedBill, actualBill);

        final List<BillItem> billItems = new ArrayList<BillItem>() {{
            add(buildBillItem(inputBill, buildProduct("76654", "blwablwa", "Blaw blaw", 100.00), 100.00, 1));
            add(buildBillItem(inputBill, buildProduct("24242", "gfhhgf", "fh hfg fh", 600.00), 600.00, 3));
            add(buildBillItem(inputBill, buildProduct("55453", " khj k", "kj kjkhjk", 5700.00), 5700.00, 7));
        }};

        double total = 0D;
        double billSubTotal = 0D;
        for (BillItem detail: billItems) {
            billSubTotal  = billSubTotal + (detail.getQuantity()*detail.getPrice());
        }

        double applicablePercentageDeductions = 0D;
        double rewardsDiscounted = 0D;
        double grossTotal = billSubTotal;
        double percentageDeductions = 0D;
        Type userType = inputBill.getUserReference().getUserType().getType();
        switch (userType) {
            case EMPLOYEE:
                applicablePercentageDeductions = 30;
                break;
            case AFFILIATE:
                applicablePercentageDeductions = 10;
                break;
            case CUSTOMER:
                Calendar joinedDate = returnCalendar(inputBill.getUserReference().getJoinDate());
                Calendar currentDate = returnCalendar(new Date());
                int yearsInterval = currentDate.get(YEAR) - joinedDate.get(YEAR);
                if(yearsInterval > 2){
                    applicablePercentageDeductions = 5;
                }
                break;
        }

        if(billSubTotal % 100 == 0){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(!inputBill.getBillType().equals("groceries")){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(applicablePercentageDeductions > 0){
            percentageDeductions = (billSubTotal * (applicablePercentageDeductions / 100));
        }

        if(rewardsDiscounted > 0){
            total = (billSubTotal - rewardsDiscounted) - percentageDeductions;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bill", inputBill);
        jsonObject.put("subTotal", grossTotal);
        jsonObject.put("% Deduction", applicablePercentageDeductions);
        jsonObject.put("Monetary % Deduction", percentageDeductions);
        jsonObject.put("Rewards Discount", rewardsDiscounted);
        jsonObject.put("netTotal", total);

        System.out.print(jsonObject);

    }

    @Test
    public void shouldGenerateNewOldCustomerBill() throws JSONException {
        final User user = buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");

        final Bill inputBill = buildBill("furniture", user);
        final Bill expectedBill = Mockito.mock(Bill.class);

        when(iBillRepository.save(inputBill)).thenReturn(expectedBill);
        final Bill actualBill = billServiceImpl.placeBill(inputBill);

        verify(iBillRepository, times(1)).save(inputBill);
        assertEquals(expectedBill, actualBill);

        final List<BillItem> billItems = new ArrayList<BillItem>() {{
            add(buildBillItem(inputBill, buildProduct("76654", "blwablwa", "Blaw blaw", 100.00), 100.00, 1));
            add(buildBillItem(inputBill, buildProduct("24242", "gfhhgf", "fh hfg fh", 600.00), 600.00, 3));
            add(buildBillItem(inputBill, buildProduct("55453", " khj k", "kj kjkhjk", 5700.00), 5700.00, 7));
        }};

        double total = 0D;
        double billSubTotal = 0D;
        for (BillItem detail: billItems) {
            billSubTotal  = billSubTotal + (detail.getQuantity()*detail.getPrice());
        }

        double applicablePercentageDeductions = 0D;
        double rewardsDiscounted = 0D;
        double grossTotal = billSubTotal;
        double percentageDeductions = 0D;
        Type userType = inputBill.getUserReference().getUserType().getType();
        switch (userType) {
            case EMPLOYEE:
                applicablePercentageDeductions = 30;
                break;
            case AFFILIATE:
                applicablePercentageDeductions = 10;
                break;
            case CUSTOMER:
                Calendar joinedDate = returnCalendar(inputBill.getUserReference().getJoinDate());
                Calendar currentDate = returnCalendar(new Date());
                int yearsInterval = currentDate.get(YEAR) - joinedDate.get(YEAR);
                if(yearsInterval > 2){
                    applicablePercentageDeductions = 5;
                }
                break;
        }

        if(billSubTotal % 100 == 0){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(!inputBill.getBillType().equals("groceries")){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(applicablePercentageDeductions > 0){
            percentageDeductions = (billSubTotal * (applicablePercentageDeductions / 100));
        }

        if(rewardsDiscounted > 0){
            total = (billSubTotal - rewardsDiscounted) - percentageDeductions;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bill", inputBill);
        jsonObject.put("subTotal", grossTotal);
        jsonObject.put("% Deduction", applicablePercentageDeductions);
        jsonObject.put("Monetary % Deduction", percentageDeductions);
        jsonObject.put("Rewards Discount", rewardsDiscounted);
        jsonObject.put("netTotal", total);

        System.out.print(jsonObject);

    }

    @Test
    public void shouldGenerateNewCustomerBill() throws JSONException {
        final User user = buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-01-2021"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");

        final Bill inputBill = buildBill("furniture", user);
        final Bill expectedBill = Mockito.mock(Bill.class);

        when(iBillRepository.save(inputBill)).thenReturn(expectedBill);
        final Bill actualBill = billServiceImpl.placeBill(inputBill);

        verify(iBillRepository, times(1)).save(inputBill);
        assertEquals(expectedBill, actualBill);

        final List<BillItem> billItems = new ArrayList<BillItem>() {{
            add(buildBillItem(inputBill, buildProduct("76654", "blwablwa", "Blaw blaw", 100.00), 100.00, 1));
            add(buildBillItem(inputBill, buildProduct("24242", "gfhhgf", "fh hfg fh", 600.00), 600.00, 3));
            add(buildBillItem(inputBill, buildProduct("55453", " khj k", "kj kjkhjk", 5700.00), 5700.00, 7));
        }};

        double total = 0D;
        double billSubTotal = 0D;
        for (BillItem detail: billItems) {
            billSubTotal  = billSubTotal + (detail.getQuantity()*detail.getPrice());
        }

        double applicablePercentageDeductions = 0D;
        double rewardsDiscounted = 0D;
        double grossTotal = billSubTotal;
        double percentageDeductions = 0D;
        Type userType = inputBill.getUserReference().getUserType().getType();
        switch (userType) {
            case EMPLOYEE:
                applicablePercentageDeductions = 30;
                break;
            case AFFILIATE:
                applicablePercentageDeductions = 10;
                break;
            case CUSTOMER:
                Calendar joinedDate = returnCalendar(inputBill.getUserReference().getJoinDate());
                Calendar currentDate = returnCalendar(new Date());
                int yearsInterval = currentDate.get(YEAR) - joinedDate.get(YEAR);
                if(yearsInterval > 2){
                    applicablePercentageDeductions = 5;
                }
                break;
        }

        if(billSubTotal % 100 == 0){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(!inputBill.getBillType().equals("groceries")){
            rewardsDiscounted = (billSubTotal / 100) * 5;
        }

        if(applicablePercentageDeductions > 0){
            percentageDeductions = (billSubTotal * (applicablePercentageDeductions / 100));
        }

        if(rewardsDiscounted > 0){
            total = (billSubTotal - rewardsDiscounted) - percentageDeductions;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bill", inputBill);
        jsonObject.put("subTotal", grossTotal);
        jsonObject.put("% Deduction", applicablePercentageDeductions);
        jsonObject.put("Monetary % Deduction", percentageDeductions);
        jsonObject.put("Rewards Discount", rewardsDiscounted);
        jsonObject.put("netTotal", total);

        System.out.print(jsonObject);

    }

    private Bill buildBill(final String billType, final User user) {
        final Bill bill = new Bill();
        bill.setReference(generateReferenceNumber());
        bill.setBillType(billType);
        bill.setUserReference(user);
        bill.setCreatedAt(new Date());
        return bill;
    }

    private BillItem buildBillItem(final Bill bill, final Product product, final Double price, final int quantity) {
        final BillItem billItem = new BillItem();
        billItem.setBill(bill);
        billItem.setQuantity(quantity);
        billItem.setPrice(price);
        billItem.setProduct(product);

        return billItem;
    }

    private Product buildProduct(final String code, final String name, final String description, final Double price) {
        final Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    private User buildUser(final String idNumber, final UserType userType, final Date joinedDate, final String company, final String address, final String phone, final String email) {
        final User user = new User();
        final UserAddress inputContactDetails = buildAddressDetail(company, address, phone, email);

        user.setIdNumber(idNumber);
        user.setUserType(userType);
        user.setAddress(inputContactDetails);
        user.setJoinDate(joinedDate);
        return user;
    }

    private UserAddress buildAddressDetail(final String company, final String address, final String phone, final String email) {
        final UserAddress userAddress = new UserAddress();
        userAddress.setCompany(company);
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setEmail(email);
        return userAddress;
    }

    private UserType buildUserType (final Type name) {
        final UserType type = new UserType();
        type.setType(name);
        return type;
    }
}
