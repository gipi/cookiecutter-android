package com.company.templateapplication.network;

import retrofit.RequestInterceptor;

public class OrganizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void intercept(retrofit.RequestInterceptor.RequestFacade request) {
        request.addHeader("Accept", "application/json");
    }
}