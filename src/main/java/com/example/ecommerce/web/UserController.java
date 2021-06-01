package com.example.ecommerce.web;

import com.example.ecommerce.config.ResourceNotFound;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.resource.IUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "E-COMMERCE")
@RestController
@RequestMapping("${app.v1}/user")
public class UserController {

    @Autowired
    private IUserRepository IUserRepository;

    @ApiOperation(value = "Create a user", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created user.")})
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @ResponseBody
    public ResponseEntity<User> create(@RequestBody User user){
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(IUserRepository.save(user), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get list of users", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(IUserRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a single user", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> findOneUser(@PathVariable Long id) {
        User user = IUserRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("User with id " + id + " not found"));
        return ResponseEntity.ok(user);
    }
}
