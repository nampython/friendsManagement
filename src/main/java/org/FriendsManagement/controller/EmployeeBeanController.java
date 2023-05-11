package org.FriendsManagement.controller;

import org.FriendsManagement.model.Employee;
import org.FriendsManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EmployeeBeanController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeBeanController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    protected final String GET_EMPLOYEE_BY_ID = "/api/v1/employees/{id}";
    protected final String GET_EMPLOYEES = "/api/v1/employees";
    protected final String UPDATE_EMPLOYEE = "/api/v1/employee/update";

//    @Bean
//    public RouterFunction<ServerResponse> getEmployeesRouter() {
//        return RouterFunctions.route(
//                RequestPredicates.GET("/api/v1/employees"),
//                request -> ServerResponse.ok().body(employeeService.findAllEmployees(), Employee.class)
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> getEmployeeByIdRouter() {
//        return RouterFunctions.route(
//                RequestPredicates.GET("/api/v1/employees/{id}"),
//                request -> ServerResponse.ok().body(employeeService.findEmployeeById(request.pathVariable("id")), Employee.class)
//        );
//    }

    @Bean
    public RouterFunction<ServerResponse> composeRoutes() {
        return
                RouterFunctions.route(
                                RequestPredicates.GET(GET_EMPLOYEES),
                                request -> ServerResponse.ok().body(employeeService.findAllEmployees(), Employee.class))
                        .and(
                                RouterFunctions.route(
                                        RequestPredicates.GET(GET_EMPLOYEE_BY_ID),
                                        request -> ServerResponse.ok().body(employeeService.findEmployeeById(request.pathVariable("id")), Employee.class)
                                ))
                        .and(
                                RouterFunctions.route(
                                        RequestPredicates.POST(UPDATE_EMPLOYEE),
                                        request -> request.body(BodyExtractors.toMono(Employee.class))
                                                .doOnNext(employeeService::updateEmployee)
                                                .then(ServerResponse.ok().build())
                                )
                        )
                ;
    }
}
