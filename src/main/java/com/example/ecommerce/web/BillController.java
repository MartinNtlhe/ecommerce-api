package com.example.ecommerce.web;

import com.example.ecommerce.entity.Bill;
import com.example.ecommerce.entity.BillItem;
import com.example.ecommerce.entity.Type;
import com.example.ecommerce.service.IBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.ecommerce.util.DateUtil.returnCalendar;
import static java.util.Calendar.YEAR;

@Api(value = "E-COMMERCE")
@RestController
@RequestMapping("${app.v1}")
public class BillController {

    private final IBillService billService;

    public BillController(IBillService billService) {
        this.billService = billService;
    }

    @ApiOperation(value = "Generate user bill based on the provided bill items and user details", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully generated user bill.")})
    @RequestMapping(method = RequestMethod.POST, value = "${billing.generate}")
    @ResponseBody
    public ResponseEntity generateBill(@RequestBody Bill bill) throws JSONException {
        if(bill != null) {
            Bill generatedBill = billService.placeBill(bill);
            double total = 0D;
            double billSubTotal = 0D;
            for (BillItem detail: generatedBill.getBillDetailList()) {
                billSubTotal  = billSubTotal + (detail.getQuantity()*detail.getPrice());
            }

            double applicablePercentageDeductions = 0D;
            double rewardsDiscounted = 0D;
            double grossTotal = billSubTotal;
            double percentageDeductions = 0D;
            Type userType = generatedBill.getUserReference().getUserType().getType();
            switch (userType) {
                case EMPLOYEE:
                    applicablePercentageDeductions = 30;
                    break;
                case AFFILIATE:
                    applicablePercentageDeductions = 10;
                    break;
                case CUSTOMER:
                    Calendar joinedDate = returnCalendar(generatedBill.getUserReference().getJoinDate());
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

            if(!generatedBill.getBillType().equals("groceries")){
                rewardsDiscounted = (billSubTotal / 100) * 5;
            }

            if(applicablePercentageDeductions > 0){
                percentageDeductions = (billSubTotal * (applicablePercentageDeductions / 100));
            }

            if(rewardsDiscounted > 0){
                total = (billSubTotal - rewardsDiscounted) - percentageDeductions;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bill", generatedBill);
            jsonObject.put("subTotal", grossTotal);
            jsonObject.put("% Deduction", applicablePercentageDeductions);
            jsonObject.put("Monetary % Deduction", percentageDeductions);
            jsonObject.put("Rewards Discount", rewardsDiscounted);
            jsonObject.put("netTotal", total);

            return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "View a list of user bills by params", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Retrieved user bills.")})
    @RequestMapping(method = RequestMethod.GET, value = "${billing.all}")
    @ResponseBody
    public ResponseEntity<List<Bill>> retrieveBillByUserReference(@RequestParam  String reference){
        return new ResponseEntity<>(billService.findByUserReference(reference), HttpStatus.OK);
    }
}
