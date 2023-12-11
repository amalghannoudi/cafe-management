package com.cafe.restlmpl;

import com.cafe.rest.DashboardRest;
import com.cafe.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestIml implements DashboardRest {

  @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
      return dashboardService.getDetails() ;
    }
}
