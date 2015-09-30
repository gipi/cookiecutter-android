package br.com.quintoandar.template.network;

import retrofit.RequestInterceptor;

public class QuintoandarRequestInterceptor implements RequestInterceptor {

    @Override
    public void intercept(retrofit.RequestInterceptor.RequestFacade request) {
        request.addHeader("Accept", "application/json");
    }
}
